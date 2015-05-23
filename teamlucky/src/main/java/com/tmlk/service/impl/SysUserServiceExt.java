package com.tmlk.service.impl;

import com.tmlk.aop.SysServiceLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.framework.util.MD5Util;
import com.tmlk.po.SysUserExt;
import org.apache.log4j.Logger;

import com.tmlk.service.ISysUserServiceExt;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SysUserServiceExt extends SysUserService implements ISysUserServiceExt {

    private static final Logger logger = Logger.getLogger(SysUserServiceExt.class);


    @Override
    @SysServiceLog(description = "登录系统", code = 101)
    public JsonResult login(String loginName, String loginPwd, HttpServletRequest request) {
        JsonResult result = new JsonResult();

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("loginName", loginName));

        List<SysUserExt> userList = this.criteriaQuery(conditions);
        if (userList != null && userList.size() == 1) {
            SysUserExt sysUser = (SysUserExt) userList.get(0);
            if (MD5Util.MD5(loginPwd).equals(sysUser.getLoginPwd())) {

                Date now = new Date();
                String ipAddress = FormatUtils.getIpAddress(request);
                sysUser.setLastLoginTime(now);
                sysUser.setLastLoginIP(ipAddress);
                this.update(sysUser);

                result.setData(sysUser);
                result.setStatus(1);
            } else {
                result.setStatus(5);
            }
        } else {
            result.setStatus(3);
        }

        return result;
    }

    @Override
    @SysServiceLog(description = "注册用户", code = 103)
    public SysUserExt register(SysUserExt sysUserExt, HttpServletRequest request) {
        //用户ID生成
        sysUserExt.setId(UUID.randomUUID().toString().replaceAll("-", ""));

        //密码加密
        sysUserExt.setLoginPwd(MD5Util.MD5(sysUserExt.getLoginPwd()));

        //如果用户注册的时候没有填写昵称  则默认为登录名
        if (FormatUtils.isEmpty(sysUserExt.getUserName())) {
            sysUserExt.setUserName(sysUserExt.getLoginName());
        }

        Date now = new Date();
        String ipAddress = FormatUtils.getIpAddress(request);

        sysUserExt.setLastLoginIP(ipAddress);
        sysUserExt.setLastLoginTime(now);
        sysUserExt.setRegisterIP(ipAddress);
        sysUserExt.setRegisterTime(now);

        return this.create(sysUserExt);
    }
}