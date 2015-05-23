package com.tmlk.service;

import com.tmlk.framework.util.JsonResult;
import com.tmlk.po.PartyUserExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IPartyUserServiceExt extends IPartyUserService{

    public JsonResult login(String loginName, String loginPwd, HttpServletRequest request);

    public PartyUserExt register(PartyUserExt partyUserExt);
}
