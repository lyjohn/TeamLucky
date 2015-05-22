package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IPartyDao;
import com.tmlk.po.PartyExt;
import com.tmlk.service.IPartyService;

public class PartyService implements IPartyService{
	
	private IPartyDao partyDao;
	
	public IPartyDao getPartyDao() {
		return partyDao;
	}

	public void setPartyDao(IPartyDao partyDao) {
		this.partyDao = partyDao;
	}

	@Override
	public PartyExt create(PartyExt party) {
		int res = partyDao.create(party);
		if(res == 1)
			return party;
		
		return null;
	}

	@Override
	public void update(PartyExt party) {
		partyDao.update(party);
	}
	
	@Override
	public PartyExt load(Long id) {
		return partyDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		partyDao.delete(id);
	}
	
	
	@Override
	public List<PartyExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<PartyExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<PartyExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return partyDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return partyDao.criteriaCount(mqlList,params);
	}
	
}
