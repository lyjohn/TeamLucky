package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.ISysExceptionDao;
import com.tmlk.po.SysExceptionExt;
import com.tmlk.service.ISysExceptionService;

public class SysExceptionService implements ISysExceptionService{
	
	private ISysExceptionDao sysExceptionDao;
	
	public ISysExceptionDao getSysExceptionDao() {
		return sysExceptionDao;
	}

	public void setSysExceptionDao(ISysExceptionDao sysExceptionDao) {
		this.sysExceptionDao = sysExceptionDao;
	}

	@Override
	public SysExceptionExt create(SysExceptionExt sysException) {
		int res = sysExceptionDao.create(sysException);
		if(res == 1)
			return sysException;
		
		return null;
	}

	@Override
	public void update(SysExceptionExt sysException) {
		sysExceptionDao.update(sysException);
	}
	
	@Override
	public SysExceptionExt load(Long id) {
		return sysExceptionDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		sysExceptionDao.delete(id);
	}
	
	
	@Override
	public List<SysExceptionExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<SysExceptionExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<SysExceptionExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return sysExceptionDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return sysExceptionDao.criteriaCount(mqlList,params);
	}
	
}
