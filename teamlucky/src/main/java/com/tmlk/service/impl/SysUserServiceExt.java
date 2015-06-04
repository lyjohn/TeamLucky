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
import com.tmlk.service.IPartyUserServiceExt;
import com.tmlk.service.ISysPartyUserLinkServiceExt;
import org.apache.log4j.Logger;

import com.tmlk.service.ISysUserServiceExt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SysUserServiceExt extends SysUserService implements ISysUserServiceExt {

    private static final Logger logger = Logger.getLogger(SysUserServiceExt.class);

    private ISysPartyUserLinkServiceExt sysPartyUserLinkService;

    public ISysPartyUserLinkServiceExt getSysPartyUserLinkService() {
        return sysPartyUserLinkService;
    }

    public void setSysPartyUserLinkService(ISysPartyUserLinkServiceExt sysPartyUserLinkService) {
        this.sysPartyUserLinkService = sysPartyUserLinkService;
    }

    private IPartyUserServiceExt partyUserService;

    public IPartyUserServiceExt getPartyUserService() {
        return partyUserService;
    }

    public void setPartyUserService(IPartyUserServiceExt partyUserService) {
        this.partyUserService = partyUserService;
    }

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
    @SysServiceLog(description = "关联系统帐号", code = 105)
    public JsonResult bind(String loginName, String loginPwd,HttpSession session) {
        JsonResult result = new JsonResult();

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("loginName", loginName));

        List<SysUserExt> userList = this.criteriaQuery(conditions);
        if (userList != null && userList.size() == 1) {
            SysUserExt sysUser = userList.get(0);

            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

            //判断是否已有同活动下的用户绑定到该系统帐号
            conditions.clear();
            conditions.add(new EqCondition("sysUserId",sysUser.getId()));
            conditions.add(new EqCondition("partyId",sessionUser.getPartyId()));

            List<SysPartyUserLinkExt> sysPartyUserLinkExtList = sysPartyUserLinkService.criteriaQuery(conditions);
            if(sysPartyUserLinkExtList != null && sysPartyUserLinkExtList.size() > 0)
            {
                PartyUserExt partyUserExt = partyUserService.load(sysPartyUserLinkExtList.get(0).getPartyUserId());
                result.setStatus(1);
                result.setMessage("同活动的["+partyUserExt.getUserName()+"]已绑定到该系统帐号");
            }
            else{
                if (MD5Util.MD5(loginPwd).equals(sysUser.getLoginPwd())) {
                    result.setStatus(0);

                    SysPartyUserLinkExt sysPartyUserLinkExt = new SysPartyUserLinkExt();
                    sysPartyUserLinkExt.setPartyUserId(sessionUser.getPartyUserId());
                    sysPartyUserLinkExt.setJoinTime(new Date());
                    sysPartyUserLinkExt.setPartyId(sessionUser.getPartyId());
                    sysPartyUserLinkExt.setSysUserId(sysUser.getId());
                    sysPartyUserLinkService.create(sysPartyUserLinkExt);

                    result.setData(sysUser);
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

    @Override
    @SysServiceLog(description = "上传系统用户头像头像",code = 104)
    public SysUserExt uploadAvatar(String filePath, String sysUserId) {
        SysUserExt sysUserExt = this.load(sysUserId);

        if (sysUserExt == null)
            return null;

        sysUserExt.setUserAvatar(filePath);
        this.update(sysUserExt);

        return sysUserExt;
    }

    /**
     * 修改用户基本in洗
     * @param sysUserExt 用户实体，承载需要变更的内容，某些项是空的
     * @param updateType 更新类型 1:基本信息(username sex birthday remark) 2:通讯录(email tel qq weixin)  3:密码)
     * @return
     */
    @Override
    @SysServiceLog(description = "编辑了系统用户资料",code = 106)
    public SysUserExt updateProfile(SysUserExt sysUserExt,int updateType) {
        SysUserExt sysUserExtPer = this.load(sysUserExt.getId());
        if (sysUserExt == null)
            return null;

        if(updateType == 1){
            sysUserExtPer.setUserName(sysUserExt.getUserName());
            if(sysUserExt.getBirthDay()!=null)
                sysUserExtPer.setBirthDay(sysUserExt.getBirthDay());
            sysUserExtPer.setSex(sysUserExt.getSex());
            sysUserExtPer.setUserRemark(sysUserExt.getUserRemark());
        }else if(updateType == 2){
            sysUserExtPer.setEmail(sysUserExt.getEmail());
            sysUserExtPer.setTel(sysUserExt.getTel());
            sysUserExtPer.setQq(sysUserExt.getQq());
            sysUserExtPer.setWeiXin(sysUserExt.getWeiXin());
        }else if(updateType == 3){
            sysUserExtPer.setLoginPwd(sysUserExt.getLoginPwd());
        }else
            return sysUserExtPer;

        this.update(sysUserExtPer);

        return sysUserExtPer;
    }
}