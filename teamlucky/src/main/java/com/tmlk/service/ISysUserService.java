package com.tmlk.service;

import java.util.List;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;

import com.tmlk.po.SysUserExt;

public interface ISysUserService {
	
	public SysUserExt create(SysUserExt sysUser);

	public void update(SysUserExt sysUser);
	
	public SysUserExt load(String  id);
	
	public void delete(String  id);

	public List<SysUserExt> criteriaQuery(List<ICondition> conditions);
	
	public List<SysUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);

	public List<SysUserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);
	
	public int count(List<ICondition> conditions);
	
}
