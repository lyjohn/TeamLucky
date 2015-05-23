package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.SysLogExt;

public interface ISysLogService {
	
	public SysLogExt create(SysLogExt sysLog);
	
	public void update(SysLogExt sysLog);
	
	public SysLogExt load(Long  id);
	
	public void delete(Long  id);

	public List<SysLogExt> criteriaQuery(List<ICondition> conditions);
	
	public List<SysLogExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);
	
	public List<SysLogExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
