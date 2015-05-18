package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.PartyUserExt;

public interface IPartyUserService {
	
	public PartyUserExt create(PartyUserExt partyUser);
	
	public void update(PartyUserExt partyUser);
	
	public PartyUserExt load(String  id);
	
	public void delete(String  id);

	public List<PartyUserExt> criteriaQuery(List<ICondition> conditions);
	
	public List<PartyUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<PartyUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
