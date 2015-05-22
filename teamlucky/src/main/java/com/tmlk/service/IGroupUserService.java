package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.GroupUserExt;

public interface IGroupUserService {
	
	public GroupUserExt create(GroupUserExt groupUser);
	
	public void update(GroupUserExt groupUser);
	
	public GroupUserExt load(Long  id);
	
	public void delete(Long  id);

	public List<GroupUserExt> criteriaQuery(List<ICondition> conditions);
	
	public List<GroupUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<GroupUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
