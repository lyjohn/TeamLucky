package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.SysPartyUserLinkExt;

public interface ISysPartyUserLinkService {
	
	public SysPartyUserLinkExt create(SysPartyUserLinkExt sysPartyUserLink);
	
	public void update(SysPartyUserLinkExt sysPartyUserLink);
	
	public SysPartyUserLinkExt load(Long  id);
	
	public void delete(Long  id);

	public List<SysPartyUserLinkExt> criteriaQuery(List<ICondition> conditions);
	
	public List<SysPartyUserLinkExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<SysPartyUserLinkExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
