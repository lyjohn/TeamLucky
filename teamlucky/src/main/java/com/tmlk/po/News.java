package com.tmlk.po;

import java.util.Date;

public class News{

	/**
	 * 新闻通知
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String newsTitle;
	
	public String getNewsTitle(){
		return newsTitle;
	}
	
	public void setNewsTitle(String newsTitle){
		this.newsTitle = newsTitle;
	}
	private String newsContent;
	
	public String getNewsContent(){
		return newsContent;
	}
	
	public void setNewsContent(String newsContent){
		this.newsContent = newsContent;
	}
	private Boolean isPublic;
	
	public Boolean getIsPublic(){
		return isPublic;
	}
	
	public void setIsPublic(Boolean isPublic){
		this.isPublic = isPublic;
	}
	private Integer readCount;
	
	public Integer getReadCount(){
		return readCount;
	}
	
	public void setReadCount(Integer readCount){
		this.readCount = readCount;
	}
	private Long groupId;
	
	public Long getGroupId(){
		return groupId;
	}
	
	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}
	private Long partyId;
	
	public Long getPartyId(){
		return partyId;
	}
	
	public void setPartyId(Long partyId){
		this.partyId = partyId;
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

	public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (!(object instanceof News))
        {
            return false;
        }
        final News that = (News)object;
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
	
	public News cloneNews (){
        
		News newObj = null;
        try
        {
            newObj = (News)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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