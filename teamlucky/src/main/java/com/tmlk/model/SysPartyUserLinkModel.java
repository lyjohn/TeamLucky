package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.SysPartyUserLinkExt;
import com.tmlk.framework.util.Pagination;

public class SysPartyUserLinkModel {

	/************************系统预定义字段，勿动！*****************************/
	private SysPartyUserLinkExt sysPartyUserLinkExt = new SysPartyUserLinkExt();
	
	private SysPartyUserLinkExt sysPartyUserLinkQueryCon = new SysPartyUserLinkExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private Long[] checkId;
	
	private Long dataId;
	
	private List<SysPartyUserLinkExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public SysPartyUserLinkExt getSysPartyUserLinkExt() {
		return sysPartyUserLinkExt;
	}

	public void setSysPartyUserLinkExt(SysPartyUserLinkExt sysPartyUserLinkExt) {
		this.sysPartyUserLinkExt = sysPartyUserLinkExt;
	}

	public SysPartyUserLinkExt getSysPartyUserLinkQueryCon() {
		return sysPartyUserLinkQueryCon;
	}

	public void setSysPartyUserLinkQueryCon(SysPartyUserLinkExt sysPartyUserLinkQueryCon) {
		this.sysPartyUserLinkQueryCon = sysPartyUserLinkQueryCon;
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

	public List<SysPartyUserLinkExt> getItems() {
		return items;
	}

	public void setItems(List<SysPartyUserLinkExt> items) {
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