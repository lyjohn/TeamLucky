package com.tmlk.po;

import java.util.Date;

public class PartyUser{

	/**
	 * 活动用户
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private Long partyId;
	
	public Long getPartyId(){
		return partyId;
	}
	
	public void setPartyId(Long partyId){
		this.partyId = partyId;
	}
	private String loginName;
	
	public String getLoginName(){
		return loginName;
	}
	
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}
	private String loginPwd;
	
	public String getLoginPwd(){
		return loginPwd;
	}
	
	public void setLoginPwd(String loginPwd){
		this.loginPwd = loginPwd;
	}
	private String userName;
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	private String sex;
	
	public String getSex(){
		return sex;
	}
	
	public void setSex(String sex){
		this.sex = sex;
	}
	private java.util.Date birthDay;
	
	public java.util.Date getBirthDay(){
		return birthDay;
	}
	
	public void setBirthDay(java.util.Date birthDay){
		this.birthDay = birthDay;
	}
	private String userAvatar;
	
	public String getUserAvatar(){
		return userAvatar;
	}
	
	public void setUserAvatar(String userAvatar){
		this.userAvatar = userAvatar;
	}
	private String userRemark;
	
	public String getUserRemark(){
		return userRemark;
	}
	
	public void setUserRemark(String userRemark){
		this.userRemark = userRemark;
	}
	private Integer userStatus;
	
	public Integer getUserStatus(){
		return userStatus;
	}
	
	public void setUserStatus(Integer userStatus){
		this.userStatus = userStatus;
	}
	private String tel;
	
	public String getTel(){
		return tel;
	}
	
	public void setTel(String tel){
		this.tel = tel;
	}
	private String email;
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	private String qq;
	
	public String getQq(){
		return qq;
	}
	
	public void setQq(String qq){
		this.qq = qq;
	}
	private String weiXin;
	
	public String getWeiXin(){
		return weiXin;
	}
	
	public void setWeiXin(String weiXin){
		this.weiXin = weiXin;
	}
	private java.util.Date registerTime;
	
	public java.util.Date getRegisterTime(){
		return registerTime;
	}
	
	public void setRegisterTime(java.util.Date registerTime){
		this.registerTime = registerTime;
	}
	private String registerIP;
	
	public String getRegisterIP(){
		return registerIP;
	}
	
	public void setRegisterIP(String registerIP){
		this.registerIP = registerIP;
	}
	private java.util.Date lastLoginTime;
	
	public java.util.Date getLastLoginTime(){
		return lastLoginTime;
	}
	
	public void setLastLoginTime(java.util.Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}
	private String lastLoginIP;
	
	public String getLastLoginIP(){
		return lastLoginIP;
	}
	
	public void setLastLoginIP(String lastLoginIP){
		this.lastLoginIP = lastLoginIP;
	}

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof PartyUser))
        {
            return false;
        }
        final PartyUser that = (PartyUser)object;
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
	
	public PartyUser clonePartyUser (){
        
		PartyUser newObj = null;
        try
        {
            newObj = (PartyUser)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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