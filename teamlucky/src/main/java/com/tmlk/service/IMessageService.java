package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.MessageExt;

public interface IMessageService {
	
	public MessageExt create(MessageExt message);
	
	public void update(MessageExt message);
	
	public MessageExt load(Long  id);
	
	public void delete(Long  id);

	public List<MessageExt> criteriaQuery(List<ICondition> conditions);
	
	public List<MessageExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<MessageExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
