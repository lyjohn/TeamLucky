package com.tmlk.framework.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tmlk.framework.util.Constants;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysUser;
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

	public synchronized void checkAndLogin(HttpSession session, Object user, HttpServletRequest request) {

		SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

		String userId = "";
		if(user.getClass() == SysUserExt.class){ //系统用户
			SysUserExt sysUser = (SysUserExt)user;
			userId = sysUser.getId();
		}
		else{
			PartyUserExt partyUser = (PartyUserExt)user;
			userId = partyUser.getId();
		}
	
		if (sessionUser == null) {
			sessionUser = new SessionUser();
			if(user.getClass() == SysUserExt.class){ //系统用户
				SysUserExt sysUser = (SysUserExt)user;
				sessionUser.setUserType(1);
				sessionUser.setUserId(userId);
				sessionUser.setUser(sysUser);
			}
			else{
				PartyUserExt partyUser = (PartyUserExt)user;
				sessionUser.setUserType(2);
				sessionUser.setUserId(userId);
				sessionUser.setUser(partyUser);
			}

			session.setAttribute(Constants.SESSION_USER, sessionUser);

			HttpSession s = sessionMap.get(userId);
			if(s == null){
				sessionMap.put(userId, session);
				sessionCount++;
			}else{
				try{
					s.invalidate();
				}catch(Exception e){
					sessionCount--;
				}
				sessionMap.put(userId, session);
				sessionCount++;
			}
			
		} else { //如果当前Session登录的用户 重新加载一下
			sessionMap.remove(sessionUser.getUserId());

			if(user.getClass() == SysUserExt.class){ //系统用户
				SysUserExt sysUser = (SysUserExt)user;
				sessionUser.setUserType(1);
				sessionUser.setUserId(userId);
				sessionUser.setUser(sysUser);
			}
			else{
				PartyUserExt partyUser = (PartyUserExt)user;
				sessionUser.setUserType(2);
				sessionUser.setUserId(userId);
				sessionUser.setUser(partyUser);
			}

			session.setAttribute(Constants.SESSION_USER, sessionUser);
			sessionMap.put(userId, session);
		}
	}

	public synchronized void logout(HttpSession session) {
		SysUser user = (SysUser) session.getAttribute(Constants.SESSION_USER);
		if (user != null) {
			session.removeAttribute(Constants.SESSION_USER);
			HttpSession s = sessionMap.remove(user.getId());
			if(s != null){
				sessionCount--;
			}
		}
	}
}
