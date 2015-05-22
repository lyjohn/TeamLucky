package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IMessageDao;
import com.tmlk.po.MessageExt;
import com.tmlk.service.IMessageService;

public class MessageService implements IMessageService{
	
	private IMessageDao messageDao;
	
	public IMessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public MessageExt create(MessageExt message) {
		int res = messageDao.create(message);
		if(res == 1)
			return message;
		
		return null;
	}

	@Override
	public void update(MessageExt message) {
		messageDao.update(message);
	}
	
	@Override
	public MessageExt load(Long id) {
		return messageDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		messageDao.delete(id);
	}
	
	
	@Override
	public List<MessageExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<MessageExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<MessageExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return messageDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return messageDao.criteriaCount(mqlList,params);
	}
	
}
