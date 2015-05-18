package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.SysUserExt;
import com.tmlk.framework.util.Pagination;

public class SysUserModel {

	/************************系统预定义字段，勿动！*****************************/
	private SysUserExt sysUserExt = new SysUserExt();
	
	private SysUserExt sysUserQueryCon = new SysUserExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private String[] checkId;
	
	private String dataId;
	
	private List<SysUserExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public SysUserExt getSysUserExt() {
		return sysUserExt;
	}

	public void setSysUserExt(SysUserExt sysUserExt) {
		this.sysUserExt = sysUserExt;
	}

	public SysUserExt getSysUserQueryCon() {
		return sysUserQueryCon;
	}

	public void setSysUserQueryCon(SysUserExt sysUserQueryCon) {
		this.sysUserQueryCon = sysUserQueryCon;
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
	
	public String[] getCheckId() {
		return checkId;
	}

	public void setCheckId(String[] checkId) {
		this.checkId = checkId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public List<SysUserExt> getItems() {
		return items;
	}

	public void setItems(List<SysUserExt> items) {
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