package com.tmlk.controller;

/**
 * Created by YangJunLin on 2015/4/18.
 */

import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.InCondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.model.MessageModel;
import com.tmlk.model.PartyModel;
import com.tmlk.model.PartyUserModel;
import com.tmlk.model.SysUserModel;

import com.tmlk.po.*;
import com.tmlk.service.*;

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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private ISysUserServiceExt sysUserService;

    @Autowired
    private IPartyUserServiceExt partyUserService;

    @Autowired
    private ISysPartyUserLinkServiceExt sysPartyUserLinkService;

    @Autowired
    private IPartyServiceExt partyService;

    @Autowired
    private IMessageServiceExt messageService;

    @RequestMapping(value = {"/","/index"})
    public String show(@ModelAttribute SysUserModel sysUserModel, ModelMap model, HttpSession session) throws IOException {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        SysUserExt sysUserExt = sysUserService.load(sessionUser.getSysUserId());

        sysUserModel.setSysUserExt(sysUserExt);
        model.addAttribute("model", sysUserModel);

        return "/user/index";
    }

    @RequestMapping(value = "/sprofile")
    public String goSysUserProfile(@ModelAttribute SysUserModel sysUserModel, ModelMap model, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        SysUserExt sysUserExt = sysUserService.load(sessionUser.getSysUserId());

        sysUserModel.setSysUserExt(sysUserExt);
        model.addAttribute("model", sysUserModel);

        return "/user/sprofile";
    }

    @RequestMapping(value = "/pprofile")
    public String goPartyUserProfile(@ModelAttribute PartyUserModel partyUserModel, ModelMap model, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

        partyUserModel.setPartyUserExt(partyUserExt);
        model.addAttribute("model", partyUserExt);

        return "/user/pprofile";
    }

    @RequestMapping(value = "/partylist")
    public String goPartyList(@ModelAttribute PartyModel partyModel,ModelMap model, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("sysUserId",sessionUser.getSysUserId()));

        //系统用户和活动用户的关联表
        List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(conditions);

        List<PartyExt> partyExts = new ArrayList<PartyExt>();

        for(SysPartyUserLinkExt sysPartyUserLinkExt : sysPartyUserLinkExts){
            partyExts.add(partyService.load(sysPartyUserLinkExt.getPartyId()));
        }

        partyModel.setItems(partyExts);

        model.addAttribute("model",partyModel);

        return "/user/partylist";
    }

    @RequestMapping(value = "/message")
    public String go(@ModelAttribute MessageModel messageModel ,ModelMap model, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //存储 消息接受人  可能是系统用户  可能是活动用户
        List<String> messageToList = new ArrayList<String>();
        //如果Session里面没有SysUserId  那肯定是活动用户
        if(FormatUtils.isEmpty(sessionUser.getSysUserId())){
            messageToList.add(sessionUser.getPartyUserId());
        }
        else{
            //把系统用户的ID加入List
            messageToList.add(sessionUser.getSysUserId());

            List<ICondition> inconditions = new ArrayList<ICondition>();
            inconditions.add(new EqCondition("sysUserId",sessionUser.getSysUserId()));

            //系统用户和活动用户的关联表
            List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(inconditions);

            //添加活动用户的ID到List
            for(SysPartyUserLinkExt sysPartyUserLinkExt : sysPartyUserLinkExts){
                messageToList.add(sysPartyUserLinkExt.getPartyUserId());
            }
        }

        List<PartyExt> partyExts = new ArrayList<PartyExt>();

        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new InCondition("messageTo",messageToList));

        List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("messageTime"));


        messageModel.setItems(messageService.criteriaQuery(conditions,orders));

        model.addAttribute("model",messageModel);

        return "/user/message";
    }



//    @RequestMapping(value="/users/{userId}")：{×××}占位符，  请求的 URL 可以是  “/users/123456”或
//    “/users/abcd” ，通过 6.6.5 讲的通过@PathV ariable 可以提取 URI 模板模式中的{×××}中的×××变量。
//    @RequestMapping(value="/users/{userId}/create") ： 这样 也 是 可 以 的 ，请 求的 URL 可 以是
//    “/users/123/create” 。
//    @RequestMapping(value="/users/{userId}/topics/{topicId}")：这样也是可以的，请求的 URL 可以是
//    “/users/123/topics/123”


    @RequestMapping(value = "/{id}")
    public String getUser(@PathVariable("id") String id, @ModelAttribute SysUserModel sysUserModel, ModelMap model, HttpSession session) {

        SysUserExt sysUserExt = null;
        if (FormatUtils.isEmpty(id)) {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
            sysUserExt = sysUserService.load(sessionUser.getSysUserId());

        } else {
            sysUserExt = sysUserService.load(id);
        }

        sysUserModel.setSysUserExt(sysUserExt);
        model.addAttribute("model", sysUserModel);
        return "/user/index";
    }

}
