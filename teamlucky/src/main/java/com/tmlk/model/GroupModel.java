package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.GroupExt;
import com.tmlk.framework.util.Pagination;

public class GroupModel {

	/************************系统预定义字段，勿动！*****************************/
	private GroupExt groupExt = new GroupExt();
	
	private GroupExt groupQueryCon = new GroupExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private Long[] checkId;
	
	private Long dataId;
	
	private List<GroupExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public GroupExt getGroupExt() {
		return groupExt;
	}

	public void setGroupExt(GroupExt groupExt) {
		this.groupExt = groupExt;
	}

	public GroupExt getGroupQueryCon() {
		return groupQueryCon;
	}

	public void setGroupQueryCon(GroupExt groupQueryCon) {
		this.groupQueryCon = groupQueryCon;
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

	public List<GroupExt> getItems() {
		return items;
	}

	public void setItems(List<GroupExt> items) {
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