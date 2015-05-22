package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.DocumentExt;

public interface IDocumentService {
	
	public DocumentExt create(DocumentExt document);
	
	public void update(DocumentExt document);
	
	public DocumentExt load(Long  id);
	
	public void delete(Long  id);

	public List<DocumentExt> criteriaQuery(List<ICondition> conditions);
	
	public List<DocumentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<DocumentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
