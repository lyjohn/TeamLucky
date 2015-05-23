package com.tmlk.service;

import com.tmlk.framework.util.JsonResult;
import com.tmlk.po.SysUserExt;

import javax.servlet.http.HttpServletRequest;

public interface ISysUserServiceExt extends ISysUserService{

    public JsonResult login(String loginName, String loginPwd,HttpServletRequest request);

    public SysUserExt register(SysUserExt sysUserExt,HttpServletRequest request);
}
