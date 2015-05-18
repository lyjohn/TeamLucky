package com.tmlk.framework.mybatis;

import java.sql.Connection;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.ReflectUtils;
import com.tmlk.framework.util.FormatUtils;
import org.apache.log4j.Logger;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagePlugin implements Interceptor {

    private static String dialect = "";    //数据库方言
    private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)

    private static final Logger logger = Logger.getLogger(Pagination.class);

    public Object intercept(Invocation ivk) throws Throwable {
        // TODO Auto-generated method stub
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtils.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectUtils.getValueByFieldName(delegate, "mappedStatement");

            if (mappedStatement.getId().matches(pageSqlId)) { //拦截需要分页的SQL
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject尚未实例化！");
                } else {
                    String sql = boundSql.getSql();

//					Connection connection = (Connection) ivk.getArgs()[0];
//					String countSql = "select count(0) as tmp_count from (" + sql+ ")"; //记录统计
//					PreparedStatement countStmt = connection.prepareStatement(countSql);
//					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
//					setParameters(countStmt,mappedStatement,countBS,parameterObject);
//					ResultSet rs = countStmt.executeQuery();
//					int count = 0;
//					if (rs.next()) {
//						count = rs.getInt(1);
//					}
//					rs.close();
//					countStmt.close();

                    Pagination pager = null;

                    //传入参数是个map 遍历所有map 查看是否各对象中有pagination属性
                    ParamMap<?> paramMap = (ParamMap<?>) parameterObject;
                    Set<String> keys = paramMap.keySet();
                    boolean noPagerField = true;
                    for (String key : keys) { //遍历 看看是否有pagination实体 没有就不分页
                        Object p = paramMap.get(key);
                        if (p == null)
                            continue;

                        if (p instanceof Pagination) {    //该对象就是Pagination实体
                            pager = (Pagination) p;
                            noPagerField = false;
                            break;
                        }
                    }
                    if (noPagerField) {
                        logger.debug("do not need pagination:\n" + sql);
                    } else {
                        String pageSql = generatePageSql(sql, pager);
                        ReflectUtils.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.

                        logger.debug("pagination：\n" + pageSql);
                    }
                }
            }
        }
        return ivk.proceed();
    }


    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
//	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
//		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
//		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//		if (parameterMappings != null) {
//			Configuration configuration = mappedStatement.getConfiguration();
//			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
//			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
//			for (int i = 0; i < parameterMappings.size(); i++) {
//				ParameterMapping parameterMapping = parameterMappings.get(i);
//				if (parameterMapping.getMode() != ParameterMode.OUT) {
//					Object value;
//					String propertyName = parameterMapping.getProperty();
//					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
//					if (parameterObject == null) {
//						value = null;
//					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
//						value = parameterObject;
//					} else if (boundSql.hasAdditionalParameter(propertyName)) {
//						value = boundSql.getAdditionalParameter(propertyName);
//					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
//						value = boundSql.getAdditionalParameter(prop.getName());
//						if (value != null) {
//							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
//						}
//					} else {
//						value = metaObject == null ? null : metaObject.getValue(propertyName);
//					}
//					TypeHandler typeHandler = parameterMapping.getTypeHandler();
//					if (typeHandler == null) {
//						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
//					}
//					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
//				}
//			}
//		}
//	}

    /**
     * 根据数据库方言，生成特定的分页sql
     *
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql, Pagination page) {
        if (page != null && FormatUtils.notEmpty(dialect)) {
            StringBuffer pageSql = new StringBuffer();
            if ("mysql".equals(dialect)) {
                pageSql.append(sql);
                pageSql.append(" limit " + page.getCurrentResult() + "," + page.getPageSize());
            } else if ("oracle".equals(dialect)) {
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
                pageSql.append(sql);
                pageSql.append(") tmp_tb where ROWNUM<=");
                pageSql.append(page.getCurrentResult() + page.getPageSize());
                pageSql.append(") where row_id>");
                pageSql.append(page.getCurrentResult());
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public Object plugin(Object target) {
        // TODO Auto-generated method stub
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (FormatUtils.isEmpty(dialect)) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (FormatUtils.isEmpty(pageSqlId)) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
