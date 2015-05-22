package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.NoticeExt;
import com.tmlk.framework.util.Pagination;

public class NoticeModel {

	/************************系统预定义字段，勿动！*****************************/
	private NoticeExt noticeExt = new NoticeExt();
	
	private NoticeExt noticeQueryCon = new NoticeExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private Long[] checkId;
	
	private Long dataId;
	
	private List<NoticeExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public NoticeExt getNoticeExt() {
		return noticeExt;
	}

	public void setNoticeExt(NoticeExt noticeExt) {
		this.noticeExt = noticeExt;
	}

	public NoticeExt getNoticeQueryCon() {
		return noticeQueryCon;
	}

	public void setNoticeQueryCon(NoticeExt noticeQueryCon) {
		this.noticeQueryCon = noticeQueryCon;
	}

	public Pagination getPp() {
		return pp;
	}

	public void setPp(Pagination pp) {
		this.pp = pp;
	}
	
	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}
	
	public Long[] getCheckId() {
		return checkId;
	}

	public void setCheckId(Long[] checkId) {
		this.checkId = checkId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public List<NoticeExt> getItems() {
		return items;
	}

	public void setItems(List<NoticeExt> items) {
		this.items = items;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public String getQuerySort() {
		return querySort;
	}

	public void setQuerySort(String querySort) {
		this.querySort = querySort;
	}
	
	/************************自定义字段区域开始*****************************/
	
	
	/************************自定义字段区域结束*****************************/
}