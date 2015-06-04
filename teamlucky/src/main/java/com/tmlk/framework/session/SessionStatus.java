package com.tmlk.framework.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tmlk.framework.util.Constants;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysUserExt;

public class SessionStatus {

    private static SessionStatus instance = new SessionStatus();

    public static SessionStatus getInstance() {
        return instance;
    }

    private Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>(10000);

    private long sessionCount = 0;

    private SessionStatus() {
    }

    public synchronized long getSessionCount() {
        return sessionCount;
    }

    /**
     * 登录的时候调用，将用户的 id name avatar记录到Session中
     *
     * @param session HttpSession
     * @param user    用户对象
     * @return 返回值
     */
    public synchronized void checkAndLogin(HttpSession session, Object user) {

        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        String sessionKey = "";
        if (user.getClass() == SysUserExt.class) { //系统用户
            SysUserExt sysUser = (SysUserExt) user;
            sessionKey = sysUser.getId();
        } else {
            PartyUserExt partyUser = (PartyUserExt) user;
            sessionKey = partyUser.getId();
        }

        if (sessionUser == null) {
            sessionUser = new SessionUser();
            if (user.getClass() == SysUserExt.class) { //系统用户
                SysUserExt sysUser = (SysUserExt) user;
                sessionUser.setUserType(1);
                sessionUser.setSessionKey(sessionKey);
                sessionUser.setSysUserAvatar(sysUser.getUserAvatar());
                sessionUser.setSysUserId(sysUser.getId());
                sessionUser.setSysUserName(sysUser.getUserName());
            } else {
                PartyUserExt partyUser = (PartyUserExt) user;
                sessionUser.setUserType(2);
                sessionUser.setSessionKey(sessionKey);
                sessionUser.setPartyUserAvatar(partyUser.getUserAvatar());
                sessionUser.setPartyUserId(partyUser.getId());
                sessionUser.setPartyUserName(partyUser.getUserName());
                sessionUser.setPartyId(partyUser.getPartyId());
                if(partyUser.getUserStatus() < 8){ //2是未入组 4是待审核 8是在组 16是管理员
                    sessionUser.setGroupId(0L);
                }
                else {
                    sessionUser.setGroupId(partyUser.getGroupId());

                    if(partyUser.getUserStatus() == 16)
                        sessionUser.setPartyAdmin(true);
                }
            }

            session.setAttribute(Constants.SESSION_AUTOCREATE,false);

            session.setAttribute(Constants.SESSION_USER, sessionUser);

            HttpSession s = sessionMap.get(sessionKey);
            if (s == null) {
                sessionMap.put(sessionKey, session);
                sessionCount++;
            } else {
                try {
                    s.invalidate();
                } catch (Exception e) {
                    sessionCount--;
                }
                sessionMap.put(sessionKey, session);
                sessionCount++;
            }

        } else { //如果当前Session登录的用户 重新加载一下
            sessionMap.remove(sessionUser.getSessionKey());

            if (user.getClass() == SysUserExt.class) { //系统用户
                SysUserExt sysUser = (SysUserExt) user;
                sessionUser.setUserType(1);
                sessionUser.setSessionKey(sessionKey);
                sessionUser.setSysUserAvatar(sysUser.getUserAvatar());
                sessionUser.setSysUserId(sysUser.getId());
                sessionUser.setSysUserName(sysUser.getUserName());
            } else {
                PartyUserExt partyUser = (PartyUserExt) user;
                sessionUser.setUserType(2);
                sessionUser.setSessionKey(sessionKey);
                sessionUser.setPartyUserAvatar(partyUser.getUserAvatar());
                sessionUser.setPartyUserId(partyUser.getId());
                sessionUser.setPartyUserName(partyUser.getUserName());
                sessionUser.setPartyId(partyUser.getPartyId());
                sessionUser.setGroupId(partyUser.getGroupId());
                if(partyUser.getUserStatus() < 8){ //2是未入组 4是待审核 8是在组 16是管理员
                    sessionUser.setGroupId(0L);
                }
                else {
                    sessionUser.setGroupId(partyUser.getGroupId());

                    if(partyUser.getUserStatus() == 16)
                        sessionUser.setPartyAdmin(true);
                }
            }

            session.setAttribute(Constants.SESSION_USER, sessionUser);

            sessionMap.put(sessionKey, session);
        }
    }

    /**
     * 用户进入活动的时候，将用户在活动中对应的活动用户记录到Session中
     *
     * @param session   HttpSession
     * @param partyUser 活动用户
     * @return 返回值
     */
    public synchronized void checkAndInParty(HttpSession session, PartyUserExt partyUser) {

        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //既然能切换活动，肯定是系统用户了,sessionKey 不用变
        sessionMap.remove(sessionUser.getSessionKey());

        //需要重新配置了PartyUser的相关信息
        sessionUser.setPartyUserAvatar(partyUser.getUserAvatar());
        sessionUser.setPartyUserId(partyUser.getId());
        sessionUser.setPartyUserName(partyUser.getUserName());
        sessionUser.setPartyId(partyUser.getPartyId());
        if(partyUser.getUserStatus() < 8){ //2是未入组 4是待审核 8是在组 16是管理员
            sessionUser.setGroupId(0L);
        }
        else {
            sessionUser.setGroupId(partyUser.getGroupId());

            if(partyUser.getUserStatus() == 16)
                sessionUser.setPartyAdmin(true);
        }
        session.setAttribute(Constants.SESSION_USER, sessionUser);

        sessionMap.put(sessionUser.getSessionKey(), session);
    }

    /**
     * 活动用户绑定系统用户之后，需存入系统用户的信息
     *
     * @param session   HttpSession
     * @param sysUser 系统用户
     * @return 返回值
     */
    public synchronized void checkAndBindSysUser(HttpSession session, SysUserExt sysUser) {

        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        sessionMap.remove(sessionUser.getSessionKey());

        //需要添加配置了SysUser的相关信息
        sessionUser.setUserType(1);
        sessionUser.setSysUserAvatar(sysUser.getUserAvatar());
        sessionUser.setSysUserId(sysUser.getId());
        sessionUser.setSysUserName(sysUser.getUserName());

        //活动用户的sessionKey去掉，换为系统用户的ID作为SessionKey
        sessionUser.setSessionKey(sysUser.getId());

        session.setAttribute(Constants.SESSION_USER, sessionUser);

        sessionMap.put(sessionUser.getSessionKey(), session);
    }

    /**
     * 活动用户进入某个小组，例如：直接进入某个小组时，或者被通过进入某个小组时（可以在接收消息的时候调用此函数，必须是本人调用才能生效哟）
     *
     * @param session   HttpSession
     * @param groupId   小组ID
     * @return 返回值
     */
    public synchronized void checkAndJoinGroup(HttpSession session,Long groupId) {

        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        sessionMap.remove(sessionUser.getSessionKey());

        //设置该SessionUser的GroupId
        sessionUser.setGroupId(groupId);

        session.setAttribute(Constants.SESSION_USER, sessionUser);

        sessionMap.put(sessionUser.getSessionKey(), session);
    }

    public synchronized void logout(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        if (sessionUser != null) {
            session.removeAttribute(Constants.SESSION_USER);
            HttpSession s = sessionMap.remove(sessionUser.getSessionKey());
            if (s != null) {
                sessionCount--;
            }
        }
    }
}
