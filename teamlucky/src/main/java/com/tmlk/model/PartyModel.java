package com.tmlk.model;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.Party;

import java.util.List;
import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class PartyModel {

    /**
     * *********************系统预定义字段，勿动！****************************
     */
    private Party party = new Party();

    private Party sysPartyQueryCon = new Party();

    private Pagination pp = new Pagination();

    private Map<String, Object> queryParams;

    private String[] checkId;

    private String dataId;

    private List<Party> items;

    private String sortType;

    private String querySort;

    public Party getParty() {
        return party;
    }

    public void setParty(Party Party) {
        this.party = Party;
    }

    public Party getSysPartyQueryCon() {
        return sysPartyQueryCon;
    }

    public void setSysPartyQueryCon(Party sysPartyQueryCon) {
        this.sysPartyQueryCon = sysPartyQueryCon;
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

    public List<Party> getItems() {
        return items;
    }

    public void setItems(List<Party> items) {
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
}
