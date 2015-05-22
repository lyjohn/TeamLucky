/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.tmlk.controller;

import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.framework.util.MD5Util;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysUserExt;
import com.tmlk.service.IPartyUserService;
import com.tmlk.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IPartyUserService partyUserService;

    @RequestMapping(value = "/")
    public String index(ModelMap model) {

        return "/index";
    }

    @RequestMapping(value = "/error")
    public String error(){
        return "/error";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public JsonResult doLogin(@RequestParam(value="loginName",required=true) String loginName, @RequestParam(value="loginPwd",required=true) String loginPwd, HttpServletRequest request, HttpSession session, ModelMap map){
        JsonResult result = new JsonResult();
        try{
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("loginName", loginName));
            if(loginName.indexOf('_')>0){ //有下划线，是活动用户
                List<PartyUserExt> userList = partyUserService.criteriaQuery(conditions);
                if(userList != null && userList.size() == 1) {
                    PartyUserExt partyUser = (PartyUserExt)userList.get(0);
                    if(MD5Util.MD5(loginPwd).equals(partyUser.getLoginPwd())) {

                        if(partyUser.getUserStatus() != 1){
                            result.setMessage("活动已禁用该用户");
                        }
                        else {
                            sessionStatus.checkAndLogin(session, partyUser, request);

                            Date now = new Date();
                            String ipAddress = getIpAddress(request);
                            partyUser.setLastLoginTime(now);
                            partyUser.setLastLoginIP(ipAddress);
                            partyUserService.update(partyUser);

                            result.setStatus(0);
                            result.setData("2");
                        }
                    }else{
                        result.setMessage("密码不正确");
                    }
                }else{
                    result.setMessage("用户名不正确");
                }
            }else{
                List<SysUserExt> userList = sysUserService.criteriaQuery(conditions);
                if(userList != null && userList.size() == 1) {
                    SysUserExt sysUser = (SysUserExt)userList.get(0);
                    if(MD5Util.MD5(loginPwd).equals(sysUser.getLoginPwd())) {

                        sessionStatus.checkAndLogin(session, sysUser, request);

                        Date now = new Date();
                        String ipAddress = getIpAddress(request);
                        sysUser.setLastLoginTime(now);
                        sysUser.setLastLoginIP(ipAddress);
                        sysUserService.update(sysUser);

                        result.setData("1");
                        result.setStatus(0);
                    }else{
                        result.setMessage("密码不正确");
                    }
                }else{
                    result.setMessage("没有该用户");
                }
            }
        }catch (Exception ex){
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/register")
    @ResponseBody
    public JsonResult doRegister(@RequestParam(value="loginName",required=true) String loginName, @RequestParam(value="loginPwd",required=true) String loginPwd, HttpServletRequest request, HttpSession session, ModelMap map){
        JsonResult result = new JsonResult();
        try{
            if(loginName.indexOf('_')>-1){
                throw new Exception("帐号不能包含下划线_");
            }

            Date now = new Date();
            String ipAddress = getIpAddress(request);
            SysUserExt sysUserExt = new SysUserExt();
            sysUserExt.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            sysUserExt.setLoginName(loginName);
            sysUserExt.setLoginPwd(MD5Util.MD5(loginPwd));
            sysUserExt.setLastLoginIP(ipAddress);
            sysUserExt.setLastLoginTime(now);
            sysUserExt.setRegisterIP(ipAddress);
            sysUserExt.setRegisterTime(now);
            sysUserExt.setUserName(loginName);

            sysUserService.create(sysUserExt);

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

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
