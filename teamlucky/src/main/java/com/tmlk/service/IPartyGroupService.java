package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.PartyGroupExt;

public interface IPartyGroupService {
	
	public PartyGroupExt create(PartyGroupExt partyGroup);
	
	public void update(PartyGroupExt partyGroup);
	
	public PartyGroupExt load(Long  id);
	
	public void delete(Long  id);

	public List<PartyGroupExt> criteriaQuery(List<ICondition> conditions);
	
	public List<PartyGroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<PartyGroupExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
