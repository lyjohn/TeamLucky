package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.SysExceptionExt;

public interface ISysExceptionService {
	
	public SysExceptionExt create(SysExceptionExt sysException);
	
	public void update(SysExceptionExt sysException);
	
	public SysExceptionExt load(Long  id);
	
	public void delete(Long  id);

	public List<SysExceptionExt> criteriaQuery(List<ICondition> conditions);
	
	public List<SysExceptionExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<SysExceptionExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
