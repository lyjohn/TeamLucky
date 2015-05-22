package com.tmlk.po;

import java.util.Date;

public class Party{

	/**
	 * 活动
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String partyName;
	
	public String getPartyName(){
		return partyName;
	}
	
	public void setPartyName(String partyName){
		this.partyName = partyName;
	}
	private String partyCode;
	
	public String getPartyCode(){
		return partyCode;
	}
	
	public void setPartyCode(String partyCode){
		this.partyCode = partyCode;
	}
	private String partyCover;
	
	public String getPartyCover(){
		return partyCover;
	}
	
	public void setPartyCover(String partyCover){
		this.partyCover = partyCover;
	}
	private String partyRemark;
	
	public String getPartyRemark(){
		return partyRemark;
	}
	
	public void setPartyRemark(String partyRemark){
		this.partyRemark = partyRemark;
	}
	private Integer partyStatus;
	
	public Integer getPartyStatus(){
		return partyStatus;
	}
	
	public void setPartyStatus(Integer partyStatus){
		this.partyStatus = partyStatus;
	}
	private Boolean isPublic;
	
	public Boolean getIsPublic(){
		return isPublic;
	}
	
	public void setIsPublic(Boolean isPublic){
		this.isPublic = isPublic;
	}
	private Boolean isGroup;
	
	public Boolean getIsGroup(){
		return isGroup;
	}
	
	public void setIsGroup(Boolean isGroup){
		this.isGroup = isGroup;
	}
	private Boolean isCustomBuild;
	
	public Boolean getIsCustomBuild(){
		return isCustomBuild;
	}
	
	public void setIsCustomBuild(Boolean isCustomBuild){
		this.isCustomBuild = isCustomBuild;
	}
	private Integer memberNumMin;
	
	public Integer getMemberNumMin(){
		return memberNumMin;
	}
	
	public void setMemberNumMin(Integer memberNumMin){
		this.memberNumMin = memberNumMin;
	}
	private Integer memberNumMax;
	
	public Integer getMemberNumMax(){
		return memberNumMax;
	}
	
	public void setMemberNumMax(Integer memberNumMax){
		this.memberNumMax = memberNumMax;
	}
	private java.util.Date buildEndTime;
	
	public java.util.Date getBuildEndTime(){
		return buildEndTime;
	}
	
	public void setBuildEndTime(java.util.Date buildEndTime){
		this.buildEndTime = buildEndTime;
	}
	private String createBy;
	
	public String getCreateBy(){
		return createBy;
	}
	
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	private java.util.Date createTime;
	
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	private Integer memberCount;
	
	public Integer getMemberCount(){
		return memberCount;
	}
	
	public void setMemberCount(Integer memberCount){
		this.memberCount = memberCount;
	}
	private Integer hotCount;
	
	public Integer getHotCount(){
		return hotCount;
	}
	
	public void setHotCount(Integer hotCount){
		this.hotCount = hotCount;
	}

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof Party))
        {
            return false;
        }
        final Party that = (Party)object;
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
	
	public Party cloneParty (){
        
		Party newObj = null;
        try
        {
            newObj = (Party)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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