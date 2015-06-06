package com.tmlk.po;

import java.util.Date;
import java.io.Serializable;

import com.tmlk.po.Forum;

public class ForumExt extends Forum  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String createTimeStr;

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}