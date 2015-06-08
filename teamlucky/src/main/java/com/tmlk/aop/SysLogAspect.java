package com.tmlk.aop;

import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.JSONUtil;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.po.*;
import com.tmlk.service.ISysLogServiceExt;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * Created by laiguoqiang on 15/5/23.
 */
@Component
@Aspect
public class SysLogAspect {

    //注入Service用于把日志保存数据库
    private ISysLogServiceExt sysLogService;

    public ISysLogServiceExt getSysLogService() {
        return sysLogService;
    }

    public void setSysLogService(ISysLogServiceExt sysLogService) {
        this.sysLogService = sysLogService;
    }

    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    //Service层切点
    @Pointcut("@annotation(com.tmlk.aop.SysServiceLog)")
    public void serviceAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(com.tmlk.aop.SysControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作，系统只拦截用户登录
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        //请求的IP
        String ip = request.getRemoteAddr();
        try {
            Integer code = getControllerMethodCode(joinPoint);
            String desc = getControllerMethodDescription(joinPoint);

            SysLogExt sysLogExt = new SysLogExt();
            sysLogExt.setLogTime(new Date());
            sysLogExt.setUserIp(ip);
            if (sessionUser == null) {
                sysLogExt.setUserName("匿名用户");
            } else {
                if (sessionUser.getUserType() == 1) {
                    sysLogExt.setUserName(sessionUser.getSysUserName());
                } else {
                    sysLogExt.setUserName(sessionUser.getPartyUserName());
                }
            }

            sysLogExt.setLogDesc(desc);
            sysLogExt.setLogAction(code);

            if (code == 102) {
                sysLogExt.setLogObjId(0L);

                String url = "http://" + request.getServerName() //服务器地址
                        + ":"
                        + request.getServerPort()           //端口号
                        + request.getContextPath()      //项目名称
                        + request.getServletPath()      //请求页面或其他地址
                        + "?" + (request.getQueryString()); //参数
                sysLogExt.setLogContent("Url路径: " + url + ", Controller方法:" + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            } else {
                sysLogExt.setLogObjId(-1L);
                sysLogExt.setLogContent("");
            }
            //保存数据库
            sysLogService.create(sysLogExt);
        } catch (Exception e) {
            logger.error("AOP Controller Before :{}", e.getMessage());
        }
    }

    /*
    * Service发生异常了就记录下来，以便后续解决，例如每天定时提醒开发人员有哪些异常了
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {

                //以Http开头的参数 不存储 例如 HttpServletRequest HttpServletResponse HttpSession 数据太长了
                if (joinPoint.getArgs()[i].getClass() == request.getClass() || joinPoint.getArgs()[i].getClass() == session.getClass())
                    continue;

                if (joinPoint.getArgs()[i].getClass() == String.class || joinPoint.getArgs()[i].getClass() == Long.class || joinPoint.getArgs()[i].getClass() == Integer.class) {
                    params += joinPoint.getArgs()[i] + ";";
                } else {
                    params += JSONUtil.object2JsonString(joinPoint.getArgs()[i]) + ";";
                }
            }
        }

         /*==========记录本地异常日志==========*/
        logger.error("AOP Service AfterThrown: 异常方法:{}\n异常代码:{}\n异常信息:{}\n参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);
    }

    /**
     * 配置后置返回通知,使用在方法aspect()上注册的切入点
     *
     * @param joinPoint
     */
    @AfterReturning(value = "serviceAspect()", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, Object returnValue) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        //获取请求ip
        String ip = request.getRemoteAddr();

        try {
            String desc = getServiceMethodDescription(joinPoint);
            Integer code = getServiceMethodCode(joinPoint);

            SysLogExt sysLogExt = new SysLogExt();
            sysLogExt.setLogTime(new Date());
            sysLogExt.setUserIp(ip);
            if (sessionUser != null)
                if (sessionUser.getUserType() == 1) {
                    sysLogExt.setUserName(sessionUser.getSysUserName());
                } else {
                    sysLogExt.setUserName(sessionUser.getPartyUserName());
                }
            else {
                sysLogExt.setUserName("匿名用户");
            }

            sysLogExt.setLogDesc(desc);
            sysLogExt.setLogAction(code);

            if (code == 101) {//登录系统,第一个参数是用户名
                sysLogExt.setUserName(joinPoint.getArgs()[0].toString());

                JsonResult loginResult = (JsonResult) returnValue;
                int loginStatus = loginResult.getStatus();
                if (loginStatus == 1) {
                    sysLogExt.setLogContent("系统用户登录成功");
                    sysLogExt.setLogObjId(0L);
                } else if (loginStatus == 2) {
                    sysLogExt.setLogContent("活动用户登录成功");
                    sysLogExt.setLogObjId(0L);
                } else {
                    sysLogExt.setLogContent("登录的未知情况");
                    sysLogExt.setLogObjId(-1L);
                }
            } else if (code == 103) {//注册系统用户
                SysUserExt sysUserExt = (SysUserExt) joinPoint.getArgs()[0];

                sysLogExt.setUserName(sysUserExt.getLoginName());
                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent(JSONUtil.object2JsonString(sysLogExt));
            }else if(code == 104){ //系统用户绑定活动用户
                sysLogExt.setUserName(sessionUser.getSysUserName());
                sysLogExt.setLogObjId(0L);

                JsonResult bindResult = (JsonResult)returnValue;
                if(bindResult.getStatus()==0)
                    sysLogExt.setLogContent("关联活动用户:"+joinPoint.getArgs()[0]+" 成功");
                else
                    sysLogExt.setLogContent("关联活动用户:"+joinPoint.getArgs()[0]+" 失败:"+bindResult.getMessage());
            }else if(code==105){//活动用户关联系统用户
                sysLogExt.setUserName(sessionUser.getPartyUserName());

                JsonResult bindResult = (JsonResult)returnValue;
                if(bindResult.getStatus()==0)
                    sysLogExt.setLogContent("关联系统用户:"+joinPoint.getArgs()[0]+" 成功");
                else
                    sysLogExt.setLogContent("关联系统用户:"+joinPoint.getArgs()[0]+" 失败:"+bindResult.getMessage());
            }else if(code==106){//编辑系统用户基本信息
                SysUserExt sysUserExt = (SysUserExt) joinPoint.getArgs()[0];

                sysLogExt.setUserName(sessionUser.getSysUserName());
                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent(JSONUtil.object2JsonString(sysUserExt));
            }else if(code==107){//编辑活动用户基本信息
                PartyUserExt partyUserExt = (PartyUserExt) joinPoint.getArgs()[0];

                sysLogExt.setUserName(sessionUser.getPartyUserName());
                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent(JSONUtil.object2JsonString(partyUserExt));
            }else if(code==108){//上传系统用户头像
                sysLogExt.setUserName(sessionUser.getSysUserName());
                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent(joinPoint.getArgs()[0].toString());
            }else if(code==109){//上传活动用户头像
                sysLogExt.setUserName(sessionUser.getPartyUserName());

                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent(joinPoint.getArgs()[0].toString());
            }else if (code == 201 || code==203) {//创建活动 || 编辑活动
                PartyExt partyExt = (PartyExt)returnValue;

                sysLogExt.setUserName(sessionUser.getSysUserName());
                sysLogExt.setLogObjId(partyExt.getId());
                sysLogExt.setLogContent(JSONUtil.object2JsonString(partyExt));
            } else if(code == 202){//创建活动用户
                PartyUserExt partyUserExt = (PartyUserExt)returnValue;

                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent(JSONUtil.object2JsonString(partyUserExt));
            }else if(code == 204){//导入活动成员
                List<PartyUserExt> partyUserExtList = (List<PartyUserExt>)returnValue;

                sysLogExt.setUserName(sessionUser.getPartyUserName());
                sysLogExt.setLogObjId(0L);
                sysLogExt.setLogContent("成功导入了"+partyUserExtList.size()+"个新成员");
            }else if(code == 205){//用户进入访问活动
                PartyUserExt partyUserExt = (PartyUserExt)joinPoint.getArgs()[0];
                PartyExt partyExt =(PartyExt)joinPoint.getArgs()[1];

                sysLogExt.setUserName(partyUserExt.getUserName());

                sysLogExt.setLogObjId(partyExt.getId());
                sysLogExt.setLogContent("活动用户:"+partyUserExt.getUserName()+" 访问了活动: "+partyExt.getPartyName());
            }else if(code == 301 || code == 303){//创建小组
                PartyGroupExt partyGroupExt = (PartyGroupExt)returnValue;

                sysLogExt.setLogObjId(partyGroupExt.getId());
                sysLogExt.setLogContent(JSONUtil.object2JsonString(partyGroupExt));
            }else if(code == 302){//加入小组
                PartyUserExt partyUserExt = (PartyUserExt)returnValue;

                sysLogExt.setLogObjId(partyUserExt.getGroupId());
                sysLogExt.setLogContent(JSONUtil.object2JsonString(partyUserExt));
            }else if(code == 401){
                NewsExt newsExt = (NewsExt)returnValue;

                sysLogExt.setLogObjId(newsExt.getId());
                sysLogExt.setLogContent(JSONUtil.object2JsonString(newsExt));
            }else {
                sysLogExt.setLogObjId(-1L);
                sysLogExt.setLogContent("未处理的日志信息"+code+"，请开发人员知悉");
            }
            //保存数据库
            sysLogService.create(sysLogExt);
        } catch (Exception ex)
        {
            //记录本地异常日志
            logger.error("AOP Service AfterReturn 异常信息:{}", ex.getMessage());
            ex.getStackTrace();
        }

    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMethodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SysServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的Code信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Integer getServiceMethodCode(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Integer code = 0;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    code = method.getAnnotation(SysServiceLog.class).code();
                    break;
                }
            }
        }
        return code;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SysControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的Code信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Integer getControllerMethodCode(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Integer code = 0;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    code = method.getAnnotation(SysControllerLog.class).code();
                    break;
                }
            }
        }
        return code;
    }
}
