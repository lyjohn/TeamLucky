package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.NoticeExt;

public interface INoticeService {
	
	public NoticeExt create(NoticeExt notice);
	
	public void update(NoticeExt notice);
	
	public NoticeExt load(Long  id);
	
	public void delete(Long  id);

	public List<NoticeExt> criteriaQuery(List<ICondition> conditions);
	
	public List<NoticeExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<NoticeExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
