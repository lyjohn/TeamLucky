package com.tmlk.po;

import java.util.Date;
import java.io.Serializable;

import com.tmlk.po.Message;

public class MessageExt extends Message  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long libId;
	
	public Long getLibId() {
		return libId;
	}

	public void setLibId(Long libId) {
		this.libId = libId;
	}
}