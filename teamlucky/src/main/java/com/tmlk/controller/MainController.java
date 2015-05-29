package com.tmlk.controller;

import com.tmlk.aop.SysControllerLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.util.*;
import com.tmlk.model.PartyModel;
import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysUserExt;
import com.tmlk.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by laiguoqiang on 15/5/13.
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

    private static SessionStatus sessionStatus = SessionStatus.getInstance();

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    private ISysUserServiceExt sysUserService;

    @Autowired
    private IPartyUserServiceExt partyUserService;

    @Autowired
    private IPartyServiceExt partyService;

    @RequestMapping(value = "/")
    public String index(@ModelAttribute PartyModel partyModel,ModelMap model) {

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("isPublic",true));

        List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("createTime"));

        Pagination pp = new Pagination();
        pp.setCurrentPage(1);
        pp.setPageSize(4);//每次4个 最多3个

        partyModel.setItems(partyService.criteriaQuery(conditions,orders,pp));

        model.addAttribute("model", partyModel);

        return "/index";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public JsonResult doLogin(@RequestParam(value="loginName",required=true) String loginName, @RequestParam(value="loginPwd",required=true) String loginPwd, HttpServletRequest request, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            JsonResult loginResult = null;
            if(loginName.indexOf('_')>0) { //有下划线，是活动用户
                loginResult = partyUserService.login(loginName, loginPwd, request);
            }
            else
                loginResult = sysUserService.login(loginName,loginPwd,request);

            if(loginResult.getStatus() == 1 || loginResult.getStatus() == 2) {
                sessionStatus.checkAndLogin(session, loginResult.getData(), request);
                result.setData(loginResult.getStatus());
                result.setStatus(0);
            }else {
                result.setMessage(Constants.LOGIN_RESULT_MAP.get(loginResult.getStatus()));
            }
        }catch (Exception ex){
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/logout")
    @SysControllerLog(description = "退出系统", code = 102)
    public String doLogout(PartyModel partyModel,HttpSession session,ModelMap model){
        try{
            sessionStatus.logout(session);

        }catch (Exception ex){
            logger.error(ex.getStackTrace());
        }
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("isPublic",true));

        List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("createTime"));

        Pagination pp = new Pagination();
        pp.setCurrentPage(1);
        pp.setPageSize(4);//每次4个 最多3个

        partyModel.setItems(partyService.criteriaQuery(conditions,orders,pp));

        model.addAttribute("model", partyModel);

        return "redirect:/";
    }

    @RequestMapping(value = "/register")
    @ResponseBody
    public JsonResult doRegister(@ModelAttribute SysUserExt sysUserExt, HttpServletRequest request, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            if(sysUserExt.getLoginName().indexOf('_')>-1){
                throw new Exception("帐号不能包含下划线_");
            }

            sysUserService.register(sysUserExt,request);

            //注册完直接登录
            sessionStatus.checkAndLogin(session, sysUserExt, request);

            result.setStatus(0);
        }catch (Exception ex){
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/checkLoginName")
    @ResponseBody
    public JsonResult checkUser(@RequestParam(value="loginName",required=true) String loginName){
        JsonResult result = new JsonResult();
        try{
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("loginName", loginName));
            List<SysUserExt> userList = sysUserService.criteriaQuery(conditions);
            if(userList.size() > 0)
                result.setMessage("用户已存在");
            else
                result.setStatus(0);
        }catch (Exception ex){
            result.setMessage(ex.getMessage());
        }
        return result;
    }

}
