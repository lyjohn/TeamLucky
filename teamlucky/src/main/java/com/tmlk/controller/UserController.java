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
import com.tmlk.framework.util.JsonResult;
import com.tmlk.framework.util.MD5Util;
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
import org.springframework.web.bind.annotation.*;

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
    private IPartyGroupServiceExt partyGroupService;

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

    @RequestMapping(value = "/scontact")
    @ResponseBody
    public JsonResult doContactSave(@ModelAttribute SysUserExt sysUserExt, ModelMap model, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            sysUserExt.setId(sessionUser.getSysUserId());

            sysUserService.updateProfile(sysUserExt,2);

            result.setStatus(0);
        }catch (Exception ex){
            logger.trace(ex);
            result.setMessage("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/sintro")
    @ResponseBody
    public JsonResult doIntroSave(@ModelAttribute SysUserExt sysUserExt, ModelMap model, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            sysUserExt.setId(sessionUser.getSysUserId());

            sysUserService.updateProfile(sysUserExt,1);

            result.setStatus(0);
        }catch (Exception ex){
            logger.trace(ex);
            result.setMessage("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/spwd")
    @ResponseBody
    public JsonResult doPwdSave(@RequestParam(value = "oldPwd",required = true) String oldPwd,@RequestParam(value = "newPwd",required = true) String newPwd, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

            SysUserExt sysUserExt = sysUserService.load(sessionUser.getSysUserId());

            if(MD5Util.MD5(oldPwd).equals(sysUserExt.getLoginPwd())){
                sysUserExt.setLoginPwd(MD5Util.MD5(newPwd));
                sysUserService.updateProfile(sysUserExt,3);

                result.setStatus(0);
                result.setMessage("修改密码成功");
            }else{
                result.setMessage("旧密码不不正确");
            }
        }catch (Exception ex){
            logger.trace(ex);
            result.setMessage("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/pprofile")
    public String goPartyUserProfile(@ModelAttribute PartyUserModel partyUserModel, ModelMap model, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

        partyUserModel.setPartyUserExt(partyUserExt);

        model.addAttribute("model", partyUserModel);

        return "/user/pprofile";
    }

    @RequestMapping(value = "/pcontact")
    @ResponseBody
    public JsonResult doContactSave(@ModelAttribute PartyUserExt partyUserExt, ModelMap model, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            partyUserExt.setId(sessionUser.getPartyUserId());

            partyUserService.updateProfile(partyUserExt, 2);

            result.setStatus(0);
        }catch (Exception ex){
            logger.trace(ex);
            result.setMessage("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/pintro")
    @ResponseBody
    public JsonResult doIntroSave(@ModelAttribute PartyUserExt partyUserExt, ModelMap model, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            partyUserExt.setId(sessionUser.getPartyUserId());

            partyUserService.updateProfile(partyUserExt, 1);

            result.setStatus(0);
        }catch (Exception ex){
            logger.trace(ex);
            result.setMessage("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/ppwd")
    @ResponseBody
    public JsonResult doPPwdSave(@RequestParam(value = "oldPwd",required = true) String oldPwd,@RequestParam(value = "newPwd",required = true) String newPwd, HttpSession session){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

            if(MD5Util.MD5(oldPwd).equals(partyUserExt.getLoginPwd())){
                partyUserExt.setLoginPwd(MD5Util.MD5(newPwd));
                partyUserService.updateProfile(partyUserExt,3);

                result.setStatus(0);
                result.setMessage("修改密码成功");
            }else{
                result.setMessage("旧密码不不正确");
            }
        }catch (Exception ex){
            logger.trace(ex);
            result.setMessage("保存失败");
        }
        return result;
    }

    @RequestMapping(value = "/partylist")
    @ResponseBody
    public JsonResult goPartyList(HttpSession session){
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("sysUserId",sessionUser.getSysUserId()));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.desc("joinTime"));

            //系统用户和活动用户的关联表
            List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(conditions,orders);

            List<PartyExt> partyExts = new ArrayList<PartyExt>();

            for(SysPartyUserLinkExt sysPartyUserLinkExt : sysPartyUserLinkExts){
                PartyExt partyExt = partyService.load(sysPartyUserLinkExt.getPartyId());

                PartyUserExt partyUserExt = partyUserService.load(sysPartyUserLinkExt.getPartyUserId());
                if(partyUserExt.getGroupId() != 0 && partyUserExt.getUserStatus() > 6)//即已成功分配了团队小组
                {
                    partyExt.setGroup(partyGroupService.load(partyUserExt.getGroupId()));
                }

                partyExts.add(partyExt);
            }

            result.setData(partyExts);
            result.setStatus(0);
        }catch (Exception ex){
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/message")
    @ResponseBody
    public JsonResult go(@ModelAttribute MessageModel messageModel ,ModelMap model, HttpSession session){
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //存储 消息接受人  可能是系统用户  可能是活动用户
            List<String> messageToList = new ArrayList<String>();
            //如果Session里面没有SysUserId  那肯定是活动用户
            if (FormatUtils.isEmpty(sessionUser.getSysUserId())) {
                messageToList.add(sessionUser.getPartyUserId());
            } else {
                //把系统用户的ID加入List
                messageToList.add(sessionUser.getSysUserId());

                List<ICondition> inconditions = new ArrayList<ICondition>();
                inconditions.add(new EqCondition("sysUserId", sessionUser.getSysUserId()));

                //系统用户和活动用户的关联表
                List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(inconditions);

                //添加活动用户的ID到List
                for (SysPartyUserLinkExt sysPartyUserLinkExt : sysPartyUserLinkExts) {
                    messageToList.add(sysPartyUserLinkExt.getPartyUserId());
                }
            }

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new InCondition("messageTo", messageToList));
            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.desc("messageTime"));

            List<MessageExt> messageExtList = messageService.criteriaQuery(conditions,orders);

            result.setData(messageExtList);
            result.setStatus(0);
        }catch (Exception ex){
            result.setMessage(ex.getMessage());
        }
        return result;
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
