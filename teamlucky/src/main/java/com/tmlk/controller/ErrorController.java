/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.tmlk.controller;

import com.tmlk.aop.SysControllerLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.framework.util.Pagination;
import com.tmlk.model.PartyModel;
import com.tmlk.po.SysUserExt;
import com.tmlk.service.IPartyServiceExt;
import com.tmlk.service.IPartyUserServiceExt;
import com.tmlk.service.ISysUserServiceExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laiguoqiang on 15/5/13.
 */
@Controller
@RequestMapping(value = "/errors")
public class ErrorController {



    @RequestMapping(value = "/error/{id}")
    public String index(@PathVariable("id") int id,ModelMap model) {

        String message = "";
        switch (id){
            case 1:
                message = "用户未登录";
                break;
            case 2:
                message = "活动不存在";//id 没有找到
                break;
            case 21:
                message = "只有活动的创建人，才能管理活动";
                break;
            case 3:
                message = "私有活动，仅成员才能参与";
                break;
            case 30:
                message = "还没有加入任何团队，或者您的申请还未通过.";
                break;
            case 31:
                message = "团队创建人才能管理团队哦";
                break;
            default:
                message = "系统错误，程序猿加班改BUG~";
                break;
        }
        
        model.addAttribute("message",message);
        return "/errors/error";
    }

}
