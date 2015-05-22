package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.ForumExt;

public interface IForumService {
	
	public ForumExt create(ForumExt forum);
	
	public void update(ForumExt forum);
	
	public ForumExt load(Long  id);
	
	public void delete(Long  id);

	public List<ForumExt> criteriaQuery(List<ICondition> conditions);
	
	public List<ForumExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<ForumExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
