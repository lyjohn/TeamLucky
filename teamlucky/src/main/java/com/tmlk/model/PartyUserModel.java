package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.PartyUserExt;
import com.tmlk.framework.util.Pagination;

public class PartyUserModel {

	/************************系统预定义字段，勿动！*****************************/
	private PartyUserExt partyUserExt = new PartyUserExt();
	
	private PartyUserExt partyUserQueryCon = new PartyUserExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private String[] checkId;
	
	private String dataId;
	
	private List<PartyUserExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public PartyUserExt getPartyUserExt() {
		return partyUserExt;
	}

	public void setPartyUserExt(PartyUserExt partyUserExt) {
		this.partyUserExt = partyUserExt;
	}

	public PartyUserExt getPartyUserQueryCon() {
		return partyUserQueryCon;
	}

	public void setPartyUserQueryCon(PartyUserExt partyUserQueryCon) {
		this.partyUserQueryCon = partyUserQueryCon;
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

	public List<PartyUserExt> getItems() {
		return items;
	}

	public void setItems(List<PartyUserExt> items) {
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