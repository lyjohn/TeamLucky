package com.tmlk.service.impl;

import com.tmlk.dao.IUserDao;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Pagination;
import com.tmlk.po.UserExt;
import com.tmlk.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/17.
 */
public class UserService implements IUserService {

    private IUserDao userDao;

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserExt create(UserExt user) {

        int res = userDao.create(user);
        if (res == 1)
            return user;

        return null;
    }

    @Override
    public void update(UserExt user) {

        userDao.update(user);
    }

    @Override
    public UserExt load(Long id) {

        return userDao.load(id);
    }

    @Override
    public void delete(Long id) {

        userDao.delete(id);
    }


    @Override
    public List<UserExt> criteriaQuery(List<ICondition> conditions) {

        return this.criteriaQuery(conditions, null, null);
    }

    @Override
    public List<UserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders) {

        return this.criteriaQuery(conditions, orders, null);
    }

    @Override
    public List<UserExt> criteriaQuery(List<ICondition> conditions, List<Order> orders, Pagination pp) {

        Map<String, Object> params = new HashMap<String, Object>();
        List<String> mqlList = new ArrayList<String>();
        if (conditions != null) {
            for (ICondition condition : conditions) {
                mqlList.add(condition.generateExpression(params));
            }
        }

        List<String> mortList = new ArrayList<String>();
        if (orders != null) {
            for (Order order : orders) {
                mortList.add(order.toSqlString());
            }
        }

        return userDao.criteriaQuery(mqlList, mortList, params, pp);
    }

    @Override
    public int count(List<ICondition> conditions) {

        Map<String, Object> params = new HashMap<String, Object>();
        List<String> mqlList = new ArrayList<String>();
        if (conditions != null) {
            for (ICondition condition : conditions) {
                mqlList.add(condition.generateExpression(params));
            }
        }

        return userDao.criteriaCount(mqlList, params);
    }

}
