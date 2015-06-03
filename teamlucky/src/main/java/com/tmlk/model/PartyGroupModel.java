package com.tmlk.model;

import java.util.List;
import java.util.Map;

import com.tmlk.po.*;
import com.tmlk.framework.util.Pagination;

public class PartyGroupModel {

	/************************系统预定义字段，勿动！*****************************/
	private PartyGroupExt partyGroupExt = new PartyGroupExt();
	
	private PartyGroupExt partyGroupQueryCon = new PartyGroupExt();
	
	private Pagination pp = new Pagination();
	
	private Map<String, Object> queryParams;
	
	private Long[] checkId;
	
	private Long dataId;
	
	private List<PartyGroupExt> items;
	
	private String sortType;
	
	private String querySort;
	
	public PartyGroupExt getPartyGroupExt() {
		return partyGroupExt;
	}

	public void setPartyGroupExt(PartyGroupExt partyGroupExt) {
		this.partyGroupExt = partyGroupExt;
	}

	public PartyGroupExt getPartyGroupQueryCon() {
		return partyGroupQueryCon;
	}

	public void setPartyGroupQueryCon(PartyGroupExt partyGroupQueryCon) {
		this.partyGroupQueryCon = partyGroupQueryCon;
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

	public List<PartyGroupExt> getItems() {
		return items;
	}

	public void setItems(List<PartyGroupExt> items) {
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

	/**
	 * 是否小组创建者
	 */
	private boolean isCreater;

	public boolean isCreater() {
		return isCreater;
	}

	public void setCreater(boolean isCreater) {
		this.isCreater = isCreater;
	}

	/*
        小组成员列表
         */
	private List<PartyUserExt> groupUsers;

	public List<PartyUserExt> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(List<PartyUserExt> groupUsers) {
		this.groupUsers = groupUsers;
	}

	/*
	小组新闻列表
	 */
	private List<NewsExt> groupNews;

	public List<NewsExt> getGroupNews() {
		return groupNews;
	}

	public void setGroupNews(List<NewsExt> groupNews) {
		this.groupNews = groupNews;
	}

	/*
	小组文档列表
	 */
	private List<DocumentExt> groupDocs;

	public List<DocumentExt> getGroupDocs() {
		return groupDocs;
	}

	public void setGroupDocs(List<DocumentExt> groupDocs) {
		this.groupDocs = groupDocs;
	}

	/*
	小组论坛列表
	 */
	private List<ForumExt> groupForums;

	public List<ForumExt> getGroupForums() {
		return groupForums;
	}

	public void setGroupForums(List<ForumExt> groupForums) {
		this.groupForums = groupForums;
	}

	/************************自定义字段区域结束*****************************/
}