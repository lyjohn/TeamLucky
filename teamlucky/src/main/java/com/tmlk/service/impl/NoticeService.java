package com.tmlk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.framework.util.Constants;

import com.tmlk.dao.INoticeDao;
import com.tmlk.po.NoticeExt;
import com.tmlk.service.INoticeService;

public class NoticeService implements INoticeService{
	
	private INoticeDao noticeDao;
	
	public INoticeDao getNoticeDao() {
		return noticeDao;
	}

	public void setNoticeDao(INoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public NoticeExt create(NoticeExt notice) {
		int res = noticeDao.create(notice);
		if(res == 1)
			return notice;
		
		return null;
	}

	@Override
	public void update(NoticeExt notice) {
		noticeDao.update(notice);
	}
	
	@Override
	public NoticeExt load(Long id) {
		return noticeDao.load(id);
	}

	@Override
	public void delete(Long  id) {
		noticeDao.delete(id);
	}
	
	
	@Override
	public List<NoticeExt> criteriaQuery(List<ICondition> conditions) {
		return this.criteriaQuery(conditions, null, null);
	}
	
	@Override
	public List<NoticeExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {
		return this.criteriaQuery(conditions, orders, null);
	}
	
	@Override
	public List<NoticeExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {
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
		
		return noticeDao.criteriaQuery(mqlList, mortList, params, pp);
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
		
		return noticeDao.criteriaCount(mqlList,params);
	}
	
}
