package com.tmlk.service;

import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.po.UserExt;

import java.util.List;

/**
 * Created by laiguoqiang on 15/5/17.
 */

public interface IUserService {
    public UserExt create(UserExt UserExt);

    public void update(UserExt UserExt);

    public UserExt load(Long id);

    public void delete(Long id);

    public List<UserExt> criteriaQuery(List<ICondition> conditions);

    public List<UserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders);

    public List<UserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp);

    public int count(List<ICondition> conditions);
}
