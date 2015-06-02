package com.tmlk.po;

import java.util.Date;
import java.io.Serializable;

import com.tmlk.po.Party;

public class PartyExt extends Party  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//记录活动创建人
	private SysUserExt PartyAuthor;

	public SysUserExt getPartyAuthor() {
		return PartyAuthor;
	}

	public void setPartyAuthor(SysUserExt partyAuthor) {
		PartyAuthor = partyAuthor;
	}

	//记录创建的相对日期
	private String createTimeString;

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	/**
	 * 记录用户在这个活动中所在的组
	 */
	private PartyGroupExt group;

	public PartyGroupExt getGroup() {
		return group;
	}

	public void setGroup(PartyGroupExt group) {
		this.group = group;
	}
}