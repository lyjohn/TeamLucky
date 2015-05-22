package com.tmlk.controller;

/**
 * Created by YangJunLin on 2015/4/18.
 */

import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.model.SysUserModel;

import com.tmlk.po.SysUserExt;
import com.tmlk.service.ISysUserServiceExt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private ISysUserServiceExt sysUserService;

    @RequestMapping(value = {"/","/index"})
    public String show(@ModelAttribute SysUserModel sysUserModel, ModelMap model, HttpSession session) throws IOException {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        SysUserExt sysUserExt = (SysUserExt) sessionUser.getUser();

        sysUserModel.setSysUserExt(sysUserExt);
        model.addAttribute("model", sysUserModel);

        return "/user/index";
    }

//    @RequestMapping(value="/users/{userId}")：{×××}占位符，  请求的 URL 可以是  “/users/123456”或
//    “/users/abcd” ，通过 6.6.5 讲的通过@PathV ariable 可以提取 URI 模板模式中的{×××}中的×××变量。
//    @RequestMapping(value="/users/{userId}/create") ： 这样 也 是 可 以 的 ，请 求的 URL 可 以是
//    “/users/123/create” 。
//    @RequestMapping(value="/users/{userId}/topics/{topicId}")：这样也是可以的，请求的 URL 可以是
//    “/users/123/topics/123”


    @RequestMapping(value = "/show")
    @ResponseBody
    public SysUserExt showUser(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
        SysUserExt user = new SysUserExt();

        return user;
    }

    @RequestMapping(value = "/error")
    public String errorUser(HttpServletRequest request, @ModelAttribute SysUserModel sysUserModel, ModelMap model) {

        return "/error";
    }

    @RequestMapping(value = "/{id}")
    public String getUser(@PathVariable("id") String id, @ModelAttribute SysUserModel sysUserModel, ModelMap model, HttpSession session) {

        SysUserExt sysUserExt = null;
        if (FormatUtils.isEmpty(id)) {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
            sysUserExt = (SysUserExt) sessionUser.getUser();

        } else {
            sysUserExt = sysUserService.load(id);
        }

        sysUserModel.setSysUserExt(sysUserExt);
        model.addAttribute("model", sysUserModel);
        return "/user/index";
    }

}
