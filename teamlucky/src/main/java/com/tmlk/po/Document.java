package com.tmlk.po;

import java.util.Date;

public class Document{

	/**
	 * 文档
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String docFullName;
	
	public String getDocFullName(){
		return docFullName;
	}
	
	public void setDocFullName(String docFullName){
		this.docFullName = docFullName;
	}
	private String docName;
	
	public String getDocName(){
		return docName;
	}
	
	public void setDocName(String docName){
		this.docName = docName;
	}
	private String docExtName;
	
	public String getDocExtName(){
		return docExtName;
	}
	
	public void setDocExtName(String docExtName){
		this.docExtName = docExtName;
	}
	private Long docSize;
	
	public Long getDocSize(){
		return docSize;
	}
	
	public void setDocSize(Long docSize){
		this.docSize = docSize;
	}
	private String docPath;
	
	public String getDocPath(){
		return docPath;
	}
	
	public void setDocPath(String docPath){
		this.docPath = docPath;
	}
	private Boolean isPublic;
	
	public Boolean getIsPublic(){
		return isPublic;
	}
	
	public void setIsPublic(Boolean isPublic){
		this.isPublic = isPublic;
	}
	private Integer downloadCount;
	
	public Integer getDownloadCount(){
		return downloadCount;
	}
	
	public void setDownloadCount(Integer downloadCount){
		this.downloadCount = downloadCount;
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
        if (!(object instanceof Document))
        {
            return false;
        }
        final Document that = (Document)object;
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
	
	public Document cloneDocument (){
        
		Document newObj = null;
        try
        {
            newObj = (Document)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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