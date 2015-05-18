package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.IPartyUserDao;
import com.tmlk.po.PartyUserExt;
import com.tmlk.service.IPartyUserService;

public class PartyUserService implements IPartyUserService{
	
	private IPartyUserDao partyUserDao;
	
	public IPartyUserDao getPartyUserDao() {
		return partyUserDao;
	}

	public void setPartyUserDao(IPartyUserDao partyUserDao) {
		this.partyUserDao = partyUserDao;
	}

	@Override
	public PartyUserExt create(PartyUserExt partyUser) {
		int res = partyUserDao.create(partyUser);
		if(res == 1)
			return partyUser;
		
		return null;
	}

	@Override
	public void update(PartyUserExt partyUser) {
		partyUserDao.update(partyUser);
	}
	
	@Override
	public PartyUserExt load(String id) {
		return partyUserDao.load(id);
	}

	@Override
	public void delete(String  id) {
		partyUserDao.delete(id);
	}
	
	
	@Override
	public List<PartyUserExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<PartyUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<PartyUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return partyUserDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return partyUserDao.criteriaCount(mqlList,params);
	}
	
}
