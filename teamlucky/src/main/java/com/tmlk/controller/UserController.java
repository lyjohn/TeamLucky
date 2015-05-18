package com.tmlk.controller;

/**
 * Created by YangJunLin on 2015/4/18.
 */

import com.tmlk.model.UserModel;

import com.tmlk.po.UserExt;
import com.tmlk.service.IUserServiceExt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private IUserServiceExt userService;

    @RequestMapping(value = "/test")
    public String getUsers(HttpServletRequest request, @ModelAttribute UserModel userModel, ModelMap model) throws IOException {

        //根据ID查
        UserExt user = userService.load(Long.parseLong(request.getParameter("userId")));

        userModel.setUser(user);

//        创建
//        UserExt user = new UserExt();
//        user.setAge(29);
//        user.setName("赖国强");
//        userService.create(user);
//
//        更新
//        UserExt user = userService.load(3L);
//        user.setAge(28);
//
//        userService.update(user);
//        userModel.setUser(user);

//        删除
//        Long delId = 1L;
//        userService.delete(delId);

//        查询
//        List<ICondition> conditions = new ArrayList<ICondition>();
//        conditions.add(new GtCondition("id",0));
//
//        List<Order> orders = new ArrayList<Order>();
//        orders.add(Order.desc("id"));
//        orders.add(Order.asc("name"));
//
//        Pagination pp = new Pagination();
//        pp.setCurrentPage(1);
//        pp.setPageSize(2);
//
//        int count = userService.count(conditions);
//        pp.checkPagination(count);
//
//        List<UserExt> users = userService.criteriaQuery(conditions);
//        List<UserExt> users = userService.criteriaQuery(conditions,orders);
//        List<UserExt> users = userService.criteriaQuery(conditions,orders,pp);

        model.addAttribute("model", userModel);

        return "/user/show";
    }

    @RequestMapping(value = "/show")
    @ResponseBody
    public UserExt showUser(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
        UserExt user = new UserExt();

        return user;
    }

    @RequestMapping(value = "/error")
    public String errorUser(HttpServletRequest request, @ModelAttribute UserModel userModel, ModelMap model) {

        return "/user/error";
    }
}
