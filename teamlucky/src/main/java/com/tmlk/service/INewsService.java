package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.NewsExt;

public interface INewsService {
	
	public NewsExt create(NewsExt news);
	
	public void update(NewsExt news);
	
	public NewsExt load(Long  id);
	
	public void delete(Long  id);

	public List<NewsExt> criteriaQuery(List<ICondition> conditions);
	
	public List<NewsExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<NewsExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
