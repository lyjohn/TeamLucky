package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IDocumentDao;
import com.tmlk.po.DocumentExt;
import com.tmlk.service.IDocumentService;

public class DocumentService implements IDocumentService{
	
	private IDocumentDao documentDao;
	
	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	@Override
	public DocumentExt create(DocumentExt document) {
		int res = documentDao.create(document);
		if(res == 1)
			return document;
		
		return null;
	}

	@Override
	public void update(DocumentExt document) {
		documentDao.update(document);
	}
	
	@Override
	public DocumentExt load(Long id) {
		return documentDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		documentDao.delete(id);
	}
	
	
	@Override
	public List<DocumentExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<DocumentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<DocumentExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return documentDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return documentDao.criteriaCount(mqlList,params);
	}
	
}
