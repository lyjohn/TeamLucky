package com.tmlk.po;

import java.util.Date;

public class Group{

	/**
	 * 小组
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private Long partyId;
	
	public Long getPartyId(){
		return partyId;
	}
	
	public void setPartyId(Long partyId){
		this.partyId = partyId;
	}
	private String groupName;
	
	public String getGroupName(){
		return groupName;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	private String groupCover;
	
	public String getGroupCover(){
		return groupCover;
	}
	
	public void setGroupCover(String groupCover){
		this.groupCover = groupCover;
	}
	private String groupRemark;
	
	public String getGroupRemark(){
		return groupRemark;
	}
	
	public void setGroupRemark(String groupRemark){
		this.groupRemark = groupRemark;
	}
	private Integer groupStatus;
	
	public Integer getGroupStatus(){
		return groupStatus;
	}
	
	public void setGroupStatus(Integer groupStatus){
		this.groupStatus = groupStatus;
	}
	private Boolean isCustomJoin;
	
	public Boolean getIsCustomJoin(){
		return isCustomJoin;
	}
	
	public void setIsCustomJoin(Boolean isCustomJoin){
		this.isCustomJoin = isCustomJoin;
	}
	private Boolean isSourcePublic;
	
	public Boolean getIsSourcePublic(){
		return isSourcePublic;
	}
	
	public void setIsSourcePublic(Boolean isSourcePublic){
		this.isSourcePublic = isSourcePublic;
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
        if (!(object instanceof Group))
        {
            return false;
        }
        final Group that = (Group)object;
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
	
	public Group cloneGroup (){
        
		Group newObj = null;
        try
        {
            newObj = (Group)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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