package com.tmlk.model;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.UserExt;

import java.util.List;
import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class UserModel {

    /**
     * *********************系统预定义字段，勿动！****************************
     */
    private UserExt user = new UserExt();

    private UserExt userQueryCon = new UserExt();

    private Pagination pp = new Pagination();

    private Map<String, Object> queryParams;

    private String[] checkId;

    private String dataId;

    private List<UserExt> items;

    private String sortType;

    private String querySort;

    public UserExt getUser() {
        return user;
    }

    public void setUser(UserExt User) {
        this.user = User;
    }

    public UserExt getUserQueryCon() {
        return userQueryCon;
    }

    public void setUserQueryCon(UserExt userQueryCon) {
        this.userQueryCon = userQueryCon;
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

    public List<UserExt> getItems() {
        return items;
    }

    public void setItems(List<UserExt> items) {
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
