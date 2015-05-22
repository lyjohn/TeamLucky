package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.ForumCommentExt;

public interface IForumCommentService {
	
	public ForumCommentExt create(ForumCommentExt forumComment);
	
	public void update(ForumCommentExt forumComment);
	
	public ForumCommentExt load(Long  id);
	
	public void delete(Long  id);

	public List<ForumCommentExt> criteriaQuery(List<ICondition> conditions);
	
	public List<ForumCommentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<ForumCommentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
