package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IGroupDao;
import com.tmlk.po.GroupExt;
import com.tmlk.service.IGroupService;

public class GroupService implements IGroupService{
	
	private IGroupDao groupDao;
	
	public IGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public GroupExt create(GroupExt group) {
		int res = groupDao.create(group);
		if(res == 1)
			return group;
		
		return null;
	}

	@Override
	public void update(GroupExt group) {
		groupDao.update(group);
	}
	
	@Override
	public GroupExt load(Long id) {
		return groupDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		groupDao.delete(id);
	}
	
	
	@Override
	public List<GroupExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<GroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<GroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> mqlList = new ArrayList<String>();
		if(conditions != null){
			for(ICondition condition : conditions){
				mqlList.add(condition.generateExpression(params));
			}
		}
		
		List<String> mortList = new ArrayList<String>();
		if(orders != null){
			for(Order order : orders){
				mortList.add(order.toSqlString());
			}
		}
		
		return groupDao.criteriaQuery(mqlList, mortList, params, pp);
	}

	@Override
	public int count(List<ICondition> conditions) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> mqlList = new ArrayList<String>();
		if(conditions != null){
			for(ICondition condition : conditions){
				mqlList.add(condition.generateExpression(params));
			}
		}
		
		return groupDao.criteriaCount(mqlList,params);
	}
	
}
