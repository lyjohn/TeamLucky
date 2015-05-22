package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IForumCommentDao;
import com.tmlk.po.ForumCommentExt;
import com.tmlk.service.IForumCommentService;

public class ForumCommentService implements IForumCommentService{
	
	private IForumCommentDao forumCommentDao;
	
	public IForumCommentDao getForumCommentDao() {
		return forumCommentDao;
	}

	public void setForumCommentDao(IForumCommentDao forumCommentDao) {
		this.forumCommentDao = forumCommentDao;
	}

	@Override
	public ForumCommentExt create(ForumCommentExt forumComment) {
		int res = forumCommentDao.create(forumComment);
		if(res == 1)
			return forumComment;
		
		return null;
	}

	@Override
	public void update(ForumCommentExt forumComment) {
		forumCommentDao.update(forumComment);
	}
	
	@Override
	public ForumCommentExt load(Long id) {
		return forumCommentDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		forumCommentDao.delete(id);
	}
	
	
	@Override
	public List<ForumCommentExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<ForumCommentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<ForumCommentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return forumCommentDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return forumCommentDao.criteriaCount(mqlList,params);
	}
	
}
