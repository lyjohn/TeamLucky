package com.tmlk.service;

import com.tmlk.framework.util.JsonResult;
import com.tmlk.po.SysUserExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ISysUserServiceExt extends ISysUserService {

    public JsonResult login(String loginName, String loginPwd, HttpServletRequest request);

    public JsonResult bind(String loginName, String loginPwd, HttpSession session);

    public SysUserExt register(SysUserExt sysUserExt, HttpServletRequest request);

    public SysUserExt uploadAvatar(String filePath, String sysUserId);

    public SysUserExt updateProfile(SysUserExt sysUserExt, int updateType);
}
