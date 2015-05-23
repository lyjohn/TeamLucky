package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.ISysLogDao;
import com.tmlk.po.SysLogExt;
import com.tmlk.service.ISysLogService;

public class SysLogService implements ISysLogService{
	
	private ISysLogDao sysLogDao;
	
	public ISysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(ISysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}

	@Override
	public SysLogExt create(SysLogExt sysLog) {
		int res = sysLogDao.create(sysLog);
		if(res == 1)
			return sysLog;
		
		return null;
	}

	@Override
	public void update(SysLogExt sysLog) {
		sysLogDao.update(sysLog);
	}
	
	@Override
	public SysLogExt load(Long id) {
		return sysLogDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		sysLogDao.delete(id);
	}
	
	
	@Override
	public List<SysLogExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<SysLogExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<SysLogExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> mqlList = new ArrayList<String>();
		if(conditions != null){
			for(ICondition condition : conditions){
				mqlList.add(condition.generateExpression(params));
			}
		}
		
		List<String> mortList = new ArrayList<String>();
		if(orders != null){
			for(Order order : orders){
				mortList.add(order.toSqlString());
			}
		}
		
		return sysLogDao.criteriaQuery(mqlList, mortList, params, pp);
	}

	@Override
	public int count(List<ICondition> conditions) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> mqlList = new ArrayList<String>();
		if(conditions != null){
			for(ICondition condition : conditions){
				mqlList.add(condition.generateExpression(params));
			}
		}
		
		return sysLogDao.criteriaCount(mqlList,params);
	}
	
}
