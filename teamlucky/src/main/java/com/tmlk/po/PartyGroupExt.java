package com.tmlk.po;

import java.io.Serializable;

public class PartyGroupExt extends PartyGroup  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//小组创建人
	private PartyUserExt groupAuthor;

	public PartyUserExt getGroupAuthor() {
		return groupAuthor;
	}

	public void setGroupAuthor(PartyUserExt groupAuthor) {
		this.groupAuthor = groupAuthor;
	}

	private String createTimeStr;

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}