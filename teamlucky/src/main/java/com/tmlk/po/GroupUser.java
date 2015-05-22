package com.tmlk.po;

import java.util.Date;

public class GroupUser{

	/**
	 * 小组成员
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String partyUserId;
	
	public String getPartyUserId(){
		return partyUserId;
	}
	
	public void setPartyUserId(String partyUserId){
		this.partyUserId = partyUserId;
	}
	private Integer memberStatus;
	
	public Integer getMemberStatus(){
		return memberStatus;
	}
	
	public void setMemberStatus(Integer memberStatus){
		this.memberStatus = memberStatus;
	}
	private Long groupId;
	
	public Long getGroupId(){
		return groupId;
	}
	
	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof GroupUser))
        {
            return false;
        }
        final GroupUser that = (GroupUser)object;
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
	
	public GroupUser cloneGroupUser (){
        
		GroupUser newObj = null;
        try
        {
            newObj = (GroupUser)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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