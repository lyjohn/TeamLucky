package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.ISysPartyUserLinkDao;
import com.tmlk.po.SysPartyUserLinkExt;
import com.tmlk.service.ISysPartyUserLinkService;

public class SysPartyUserLinkService implements ISysPartyUserLinkService{
	
	private ISysPartyUserLinkDao sysPartyUserLinkDao;
	
	public ISysPartyUserLinkDao getSysPartyUserLinkDao() {
		return sysPartyUserLinkDao;
	}

	public void setSysPartyUserLinkDao(ISysPartyUserLinkDao sysPartyUserLinkDao) {
		this.sysPartyUserLinkDao = sysPartyUserLinkDao;
	}

	@Override
	public SysPartyUserLinkExt create(SysPartyUserLinkExt sysPartyUserLink) {
		int res = sysPartyUserLinkDao.create(sysPartyUserLink);
		if(res == 1)
			return sysPartyUserLink;
		
		return null;
	}

	@Override
	public void update(SysPartyUserLinkExt sysPartyUserLink) {
		sysPartyUserLinkDao.update(sysPartyUserLink);
	}
	
	@Override
	public SysPartyUserLinkExt load(Long id) {
		return sysPartyUserLinkDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		sysPartyUserLinkDao.delete(id);
	}
	
	
	@Override
	public List<SysPartyUserLinkExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<SysPartyUserLinkExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<SysPartyUserLinkExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return sysPartyUserLinkDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return sysPartyUserLinkDao.criteriaCount(mqlList,params);
	}
	
}
