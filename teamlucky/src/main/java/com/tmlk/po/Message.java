package com.tmlk.po;

import java.util.Date;

public class Message{

	/**
	 * 消息
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String messageTitle;
	
	public String getMessageTitle(){
		return messageTitle;
	}
	
	public void setMessageTitle(String messageTitle){
		this.messageTitle = messageTitle;
	}
	private String messageContent;
	
	public String getMessageContent(){
		return messageContent;
	}
	
	public void setMessageContent(String messageContent){
		this.messageContent = messageContent;
	}
	private String messageUrl;
	
	public String getMessageUrl(){
		return messageUrl;
	}
	
	public void setMessageUrl(String messageUrl){
		this.messageUrl = messageUrl;
	}
	private Boolean isRead;
	
	public Boolean getIsRead(){
		return isRead;
	}
	
	public void setIsRead(Boolean isRead){
		this.isRead = isRead;
	}
	private String messageFrom;
	
	public String getMessageFrom(){
		return messageFrom;
	}
	
	public void setMessageFrom(String messageFrom){
		this.messageFrom = messageFrom;
	}
	private String messageTo;
	
	public String getMessageTo(){
		return messageTo;
	}
	
	public void setMessageTo(String messageTo){
		this.messageTo = messageTo;
	}
	private java.util.Date messageTime;
	
	public java.util.Date getMessageTime(){
		return messageTime;
	}
	
	public void setMessageTime(java.util.Date messageTime){
		this.messageTime = messageTime;
	}

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Message))
        {
            return false;
        }
        final Message that = (Message)object;
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
	
	public Message cloneMessage (){
        
		Message newObj = null;
        try
        {
            newObj = (Message)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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