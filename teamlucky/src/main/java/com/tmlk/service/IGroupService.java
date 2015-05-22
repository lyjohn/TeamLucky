package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.GroupExt;

public interface IGroupService {
	
	public GroupExt create(GroupExt group);
	
	public void update(GroupExt group);
	
	public GroupExt load(Long  id);
	
	public void delete(Long  id);

	public List<GroupExt> criteriaQuery(List<ICondition> conditions);
	
	public List<GroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<GroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
