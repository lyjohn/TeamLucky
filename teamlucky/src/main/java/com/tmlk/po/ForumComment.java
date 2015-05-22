package com.tmlk.po;

import java.util.Date;

public class ForumComment{

	/**
	 * 论坛回复
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private Long forumId;
	
	public Long getForumId(){
		return forumId;
	}
	
	public void setForumId(Long forumId){
		this.forumId = forumId;
	}
	private String commentContent;
	
	public String getCommentContent(){
		return commentContent;
	}
	
	public void setCommentContent(String commentContent){
		this.commentContent = commentContent;
	}
	private Long parentId;
	
	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
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
        if (!(object instanceof ForumComment))
        {
            return false;
        }
        final ForumComment that = (ForumComment)object;
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
	
	public ForumComment cloneForumComment (){
        
		ForumComment newObj = null;
        try
        {
            newObj = (ForumComment)org.apache.commons.beanutils.BeanUtils.cloneBean(this);            
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