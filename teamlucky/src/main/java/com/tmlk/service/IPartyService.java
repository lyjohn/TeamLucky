package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.PartyExt;

public interface IPartyService {
	
	public PartyExt create(PartyExt party);
	
	public void update(PartyExt party);
	
	public PartyExt load(Long  id);
	
	public void delete(Long  id);

	public List<PartyExt> criteriaQuery(List<ICondition> conditions);
	
	public List<PartyExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<PartyExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
