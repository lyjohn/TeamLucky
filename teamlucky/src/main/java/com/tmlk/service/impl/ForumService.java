package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IForumDao;
import com.tmlk.po.ForumExt;
import com.tmlk.service.IForumService;

public class ForumService implements IForumService{
	
	private IForumDao forumDao;
	
	public IForumDao getForumDao() {
		return forumDao;
	}

	public void setForumDao(IForumDao forumDao) {
		this.forumDao = forumDao;
	}

	@Override
	public ForumExt create(ForumExt forum) {
		int res = forumDao.create(forum);
		if(res == 1)
			return forum;
		
		return null;
	}

	@Override
	public void update(ForumExt forum) {
		forumDao.update(forum);
	}
	
	@Override
	public ForumExt load(Long id) {
		return forumDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		forumDao.delete(id);
	}
	
	
	@Override
	public List<ForumExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<ForumExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<ForumExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return forumDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return forumDao.criteriaCount(mqlList,params);
	}
	
}
