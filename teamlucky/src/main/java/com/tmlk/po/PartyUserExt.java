package com.tmlk.po;

import java.util.Date;
import java.io.Serializable;

import com.tmlk.po.PartyUser;

public class PartyUserExt extends PartyUser  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 活动用户的小组
	 */
	private PartyGroupExt group;

	public PartyGroupExt getGroup() {
		return group;
	}

	public void setGroup(PartyGroupExt group) {
		this.group = group;
	}

	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private String lastLoginTimeStr;

	public String getLastLoginTimeStr() {
		return lastLoginTimeStr;
	}

	public void setLastLoginTimeStr(String lastLoginTimeStr) {
		this.lastLoginTimeStr = lastLoginTimeStr;
	}
}