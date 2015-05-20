package com.tmlk.framework.util;

public class JsonResult {

	public JsonResult() {
		this.message = "";
		this.data = null;
		this.status=1;
	}

	private String message;

	/**
	 *  0:成功  1:失败
	 */
	private int status;
	
	private Object data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
