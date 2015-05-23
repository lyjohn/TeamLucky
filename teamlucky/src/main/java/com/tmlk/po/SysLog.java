package com.tmlk.po;

import java.util.Date;

public class SysLog{

	/**
	 * 系统日志
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String userName;
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	private String userIp;
	
	public String getUserIp(){
		return userIp;
	}
	
	public void setUserIp(String userIp){
		this.userIp = userIp;
	}
	private Integer logAction;
	
	public Integer getLogAction(){
		return logAction;
	}
	
	public void setLogAction(Integer logAction){
		this.logAction = logAction;
	}
	private String logDesc;
	
	public String getLogDesc(){
		return logDesc;
	}
	
	public void setLogDesc(String logDesc){
		this.logDesc = logDesc;
	}
	private java.util.Date logTime;
	
	public java.util.Date getLogTime(){
		return logTime;
	}
	
	public void setLogTime(java.util.Date logTime){
		this.logTime = logTime;
	}
	private Long logObjId;
	
	public Long getLogObjId(){
		return logObjId;
	}
	
	public void setLogObjId(Long logObjId){
		this.logObjId = logObjId;
	}
	private String logContent;
	
	public String getLogContent(){
		return logContent;
	}
	
	public void setLogContent(String logContent){
		this.logContent = logContent;
	}

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof SysLog))
        {
            return false;
        }
        final SysLog that = (SysLog)object;
        if (this.id == null || that.getId() == null || !this.id.equals(that.getId()))
        {
            return false;
        }
        return true;
    }
	
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());
		return hashCode;
	}
	
	public String toString() {
		try {
			return org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(this);
		} catch (Exception e) {
			return "";
		}

	}
	
	public SysLog cloneSysLog (){
        
		SysLog newObj = null;
        try
        {
            newObj = (SysLog)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
        } catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.lang.reflect.InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return newObj;
	}
}