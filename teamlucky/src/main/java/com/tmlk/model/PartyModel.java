package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.*;
import com.tmlk.framework.util.Pagination;

public class PartyModel {

	/************************系统预定义字段，勿动！*****************************/
	private PartyExt partyExt = new PartyExt();
	
	private PartyExt partyQueryCon = new PartyExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private Long[] checkId;
	
	private Long dataId;
	
	private List<PartyExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public PartyExt getPartyExt() {
		return partyExt;
	}

	public void setPartyExt(PartyExt partyExt) {
		this.partyExt = partyExt;
	}

	public PartyExt getPartyQueryCon() {
		return partyQueryCon;
	}

	public void setPartyQueryCon(PartyExt partyQueryCon) {
		this.partyQueryCon = partyQueryCon;
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

	public List<PartyExt> getItems() {
		return items;
	}

	public void setItems(List<PartyExt> items) {
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

	/*
	活动用户列表
	 */
	private List<PartyUserExt> partyUsers;

	public List<PartyUserExt> getPartyUsers() {
		return partyUsers;
	}

	public void setPartyUsers(List<PartyUserExt> partyUsers) {
		this.partyUsers = partyUsers;
	}

	/*
	活动团队列表
	 */
	private List<PartyGroupExt> partyGroups;

	public List<PartyGroupExt> getPartyGroups() {
		return partyGroups;
	}

	public void setPartyGroups(List<PartyGroupExt> partyGroups) {
		this.partyGroups = partyGroups;
	}

	/*
    活动新闻列表
    */
	private List<NewsExt> partyNews;

	public List<NewsExt> getPartyNews() {
		return partyNews;
	}

	public void setPartyNews(List<NewsExt> partyNews) {
		this.partyNews = partyNews;
	}

	/*
    活动文档列表
    */
	private List<DocumentExt> partyDocs;

	public List<DocumentExt> getPartyDocs() {
		return partyDocs;
	}

	public void setPartyDocs(List<DocumentExt> partyDocs) {
		this.partyDocs = partyDocs;
	}

	/*
    活动论坛列表
    */
	private List<ForumExt> partyForums;

	public List<ForumExt> getPartyForums() {
		return partyForums;
	}

	public void setPartyForums(List<ForumExt> partyForums) {
		this.partyForums = partyForums;
	}

	/************************自定义字段区域结束*****************************/
}