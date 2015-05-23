package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.dao.IPartyGroupDao;
import com.tmlk.po.PartyGroupExt;
import com.tmlk.service.IPartyGroupService;

public class PartyGroupService implements IPartyGroupService{
	
	private IPartyGroupDao partyGroupDao;
	
	public IPartyGroupDao getPartyGroupDao() {
		return partyGroupDao;
	}

	public void setPartyGroupDao(IPartyGroupDao partyGroupDao) {
		this.partyGroupDao = partyGroupDao;
	}

	@Override
	public PartyGroupExt create(PartyGroupExt partyGroup) {
		int res = partyGroupDao.create(partyGroup);
		if(res == 1)
			return partyGroup;
		
		return null;
	}

	@Override
	public void update(PartyGroupExt partyGroup) {
		partyGroupDao.update(partyGroup);
	}
	
	@Override
	public PartyGroupExt load(Long id) {
		return partyGroupDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		partyGroupDao.delete(id);
	}
	
	
	@Override
	public List<PartyGroupExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<PartyGroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<PartyGroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return partyGroupDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return partyGroupDao.criteriaCount(mqlList,params);
	}
	
}
