package com.tmlk.service;

import com.tmlk.framework.util.JsonResult;
import com.tmlk.po.PartyUserExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IPartyUserServiceExt extends IPartyUserService{

    public PartyUserExt findUserByName(String loginName);

    public JsonResult login(String loginName, String loginPwd, HttpServletRequest request);

    public JsonResult bind(String loginName, String loginPwd, HttpSession session);

    public PartyUserExt register(PartyUserExt partyUserExt);

    public PartyUserExt uploadAvatar(String filePath,String partyUserId);

    public PartyUserExt updateProfile(PartyUserExt partyUserExt,int updateType);

    public PartyUserExt joinGroup(PartyUserExt partyUserExt);
}
