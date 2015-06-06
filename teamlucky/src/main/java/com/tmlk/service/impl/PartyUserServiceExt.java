package com.tmlk.service.impl;

import com.tmlk.aop.SysServiceLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.framework.util.MD5Util;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysPartyUserLink;
import com.tmlk.po.SysPartyUserLinkExt;
import com.tmlk.po.SysUserExt;
import com.tmlk.service.ISysPartyUserLinkServiceExt;
import org.apache.log4j.Logger;

import com.tmlk.service.IPartyUserServiceExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PartyUserServiceExt extends PartyUserService implements IPartyUserServiceExt {

    private static final Logger logger = Logger.getLogger(PartyUserServiceExt.class);

    private ISysPartyUserLinkServiceExt sysPartyUserLinkService;

    public ISysPartyUserLinkServiceExt getSysPartyUserLinkService() {
        return sysPartyUserLinkService;
    }

    public void setSysPartyUserLinkService(ISysPartyUserLinkServiceExt sysPartyUserLinkService) {
        this.sysPartyUserLinkService = sysPartyUserLinkService;
    }

    @Override
    public PartyUserExt findUserByName(String loginName) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("loginName", loginName));

        List<PartyUserExt> userList = this.criteriaQuery(conditions);

        if(userList == null || userList.size() == 0)
            return null;
        else
            return userList.get(0);
    }

    @Override
    @SysServiceLog(description = "登录系统", code = 101)
    public JsonResult login(String loginName, String loginPwd, HttpServletRequest request) {
        JsonResult result = new JsonResult();

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("loginName", loginName));

        List<PartyUserExt> userList = this.criteriaQuery(conditions);
        if (userList != null && userList.size() == 1) {
            PartyUserExt partyUser = (PartyUserExt) userList.get(0);
            if (MD5Util.MD5(loginPwd).equals(partyUser.getLoginPwd())) {

                //1：不允许登录活动，2：未分组状态 4：已申请小组，等待审核中 8：已进入小组 16:管理员
                if (partyUser.getUserStatus()%2 == 1) {
                    result.setStatus(6);
                } else {
                    Date now = new Date();
                    String ipAddress = FormatUtils.getIpAddress(request);
//进入活动的时候设置
//                  partyUser.setLastLoginTime(now);
                    partyUser.setLastLoginIP(ipAddress);
                    this.update(partyUser);

                    result.setData(partyUser);
                    result.setStatus(2);
                }
            } else {
                result.setStatus(5);
            }
        } else {
            result.setStatus(4);
        }

        return result;
    }

    @Override
    @SysServiceLog(description = "绑定活动帐号", code = 104)
    public JsonResult bind(String loginName, String loginPwd,HttpSession session) {
        JsonResult result = new JsonResult();

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("loginName", loginName));

        List<PartyUserExt> userList = this.criteriaQuery(conditions);
        if (userList != null && userList.size() == 1) {
            PartyUserExt partyUser = (PartyUserExt) userList.get(0);

            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

            //判断是否已绑定同活动下的用户
            conditions.clear();
            conditions.add(new EqCondition("sysUserId",sessionUser.getSysUserId()));
            conditions.add(new EqCondition("partyId",partyUser.getPartyId()));

            List<SysPartyUserLinkExt> sysPartyUserLinkExtList = sysPartyUserLinkService.criteriaQuery(conditions);
            if(sysPartyUserLinkExtList != null && sysPartyUserLinkExtList.size() > 0)
            {
                PartyUserExt partyUserExt = this.load(sysPartyUserLinkExtList.get(0).getPartyUserId());
                result.setStatus(1);
                result.setMessage("您已绑定了同活动下的帐号:"+partyUserExt.getUserName());
            }
            else{
                if (MD5Util.MD5(loginPwd).equals(partyUser.getLoginPwd())) {
                    result.setStatus(0);

                    SysPartyUserLinkExt sysPartyUserLinkExt = new SysPartyUserLinkExt();
                    sysPartyUserLinkExt.setPartyUserId(partyUser.getId());
                    sysPartyUserLinkExt.setJoinTime(new Date());
                    sysPartyUserLinkExt.setPartyId(partyUser.getPartyId());
                    sysPartyUserLinkExt.setSysUserId(sessionUser.getSysUserId());
                    sysPartyUserLinkService.create(sysPartyUserLinkExt);

                    result.setData(sysPartyUserLinkExt);
                } else {
                    result.setStatus(1);
                    result.setMessage("密码不正确");
                }
            }
        } else {
            result.setStatus(1);
            result.setMessage("活动账户不存在");
        }

        return result;
    }

    @Override
    @SysServiceLog(description = "创建活动用户", code = 202)
    public PartyUserExt register(PartyUserExt partyUserExt) {
        //用户ID生成
        partyUserExt.setId(UUID.randomUUID().toString().replaceAll("-", ""));

        return this.create(partyUserExt);
    }

    @Override
    @SysServiceLog(description = "上传活动用户头像头像",code = 109)
    public PartyUserExt uploadAvatar(String filePath, String partyUserId) {
        PartyUserExt partyUserExt = this.load(partyUserId);

        if (partyUserExt == null)
            return null;

        partyUserExt.setUserAvatar(filePath);
        this.update(partyUserExt);

        return partyUserExt;
    }

    /**
     * 修改用户基本in洗
     * @param partyUserExt 用户实体，承载需要变更的内容，某些项是空的
     * @param updateType 更新类型 1:基本信息(username sex birthday remark) 2:通讯录(email tel qq weixin)  3:密码)
     * @return
     */
    @Override
    @SysServiceLog(description = "编辑了活动用户资料",code = 107)
    public PartyUserExt updateProfile(PartyUserExt partyUserExt,int updateType) {
        PartyUserExt partyUserExtPer = this.load(partyUserExt.getId());
        if (partyUserExtPer == null)
            return null;

        if(updateType == 1){
            partyUserExtPer.setUserName(partyUserExt.getUserName());
            if(partyUserExt.getBirthDay()!=null)
                partyUserExtPer.setBirthDay(partyUserExt.getBirthDay());
            partyUserExtPer.setSex(partyUserExt.getSex());
            partyUserExtPer.setUserRemark(partyUserExt.getUserRemark());
        }else if(updateType == 2){
            partyUserExtPer.setEmail(partyUserExt.getEmail());
            partyUserExtPer.setTel(partyUserExt.getTel());
            partyUserExtPer.setQq(partyUserExt.getQq());
            partyUserExtPer.setWeiXin(partyUserExt.getWeiXin());
        }else if(updateType == 3){
            partyUserExtPer.setLoginPwd(partyUserExt.getLoginPwd());
        }
        else
            return partyUserExtPer;

        this.update(partyUserExtPer);

        return partyUserExtPer;
    }

    /**
     * 修改用户基本in洗
     * @param partyUserExt 用户实体，承载需要变更的内容，某些项是空的
     * @return
     */
    @Override
    @SysServiceLog(description = "加入小组",code = 302)
    public PartyUserExt joinGroup(PartyUserExt partyUserExt) {
        this.update(partyUserExt);

        return partyUserExt;
    }
}