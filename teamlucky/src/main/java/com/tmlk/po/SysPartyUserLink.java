package com.tmlk.po;

import java.util.Date;

public class SysPartyUserLink{

	/**
	 * 系统活动用户关联
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String sysUserId;
	
	public String getSysUserId(){
		return sysUserId;
	}
	
	public void setSysUserId(String sysUserId){
		this.sysUserId = sysUserId;
	}
	private String partyUserId;
	
	public String getPartyUserId(){
		return partyUserId;
	}
	
	public void setPartyUserId(String partyUserId){
		this.partyUserId = partyUserId;
	}
	private Long partyId;
	
	public Long getPartyId(){
		return partyId;
	}
	
	public void setPartyId(Long partyId){
		this.partyId = partyId;
	}

	private Date joinTime;

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof SysPartyUserLink))
        {
            return false;
        }
        final SysPartyUserLink that = (SysPartyUserLink)object;
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
	
	public SysPartyUserLink cloneSysPartyUserLink (){
        
		SysPartyUserLink newObj = null;
        try
        {
            newObj = (SysPartyUserLink)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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