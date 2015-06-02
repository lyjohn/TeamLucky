package com.tmlk.controller;

/**
 * Created by LaiGuoqiang on 2015/5/14.
 */

import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.OrCondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.model.*;
import com.tmlk.po.*;
import com.tmlk.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/group")
public class GroupController {

    private static SessionStatus sessionStatus = SessionStatus.getInstance();

    private static final Logger logger = Logger.getLogger(GroupController.class);

    @Autowired
    private IPartyGroupServiceExt partyGroupService;

    @Autowired
    private IPartyServiceExt partyService;

    @Autowired
    private ISysPartyUserLinkServiceExt sysPartyUserLinkService;

    @Autowired
    private IPartyUserServiceExt partyUserService;

    @Autowired
    private INewsServiceExt newsService;

    @Autowired
    private IDocumentServiceExt documentService;

    @Autowired
    private IForumServiceExt forumService;


    @RequestMapping(value = "/create")
    public String goCreate(HttpSession session){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());
        if(partyUserExt.getUserStatus() == 8){ //已经在小组 则不创建 直接进入了 也就是不能创建多个小组

            return "redirect:/group/index/"+partyUserExt.getGroupId();
        }

        return "/group/create";
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doCreate(@ModelAttribute PartyGroupExt partyGroupExt, HttpSession session, HttpServletRequest request){
        JsonResult result = new JsonResult();
        try{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            partyGroupExt.setCreateBy(sessionUser.getPartyUserId()); //创建者是活动用户的帐号
            partyGroupExt.setCreateTime(new Date());
            partyGroupExt.setPartyId(sessionUser.getPartyId());
            partyGroupExt.setGroupStatus(1);
            partyGroupExt.setMemberCount(1);
            partyGroupExt.setHotCount(0);

            partyGroupService.build(partyGroupExt);

            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());
            partyUserExt.setGroupId(partyGroupExt.getId());
            if(partyUserExt.getUserStatus() == 16){ //是这个活动的创建者

            }else{
                partyUserExt.setUserStatus(8);//已进入小组
            }
            partyUserService.update(partyUserExt);

            sessionStatus.checkAndJoinGroup(session, partyGroupExt.getId());

            result.setStatus(0);
        }catch (Exception ex){
            result.setMessage("服务器异常，请重新提交");
            logger.error(ex);
        }
        return result;
    }


    @RequestMapping(value = {"/index/{id}"},method = RequestMethod.GET)
    public String indexPublic(@PathVariable("id") Long id,@ModelAttribute PartyGroupModel partyGroupModel, HttpSession session,HttpServletRequest request, ModelMap model){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(id);
        if(partyGroupExt == null){
            return "redirect:/errors/error/30";
        }else{
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("groupId",partyGroupExt.getId()));
            conditions.add(new EqCondition("userStatus",8));//只能看到当前在活动的用户  被禁用，或者审批中的不能看到
            List<PartyUserExt> partyUserExts = partyUserService.criteriaQuery(conditions);


            partyGroupModel.setGroupUsers(partyUserExts);
            model.addAttribute("model",partyGroupModel);

            return "/group/index";
        }
    }

    @RequestMapping(value = {"/","/index"},method = RequestMethod.GET)
    public String index(@ModelAttribute PartyGroupModel partyGroupModel, HttpSession session,HttpServletRequest request, ModelMap model){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getGroupId());
        if(partyGroupExt == null){
            return "redirect:/errors/error/30";
        }else{
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("groupId",partyGroupExt.getId()));
            conditions.add(new EqCondition("userStatus",8));//只能看到当前在活动的用户  被禁用，或者审批中的不能看到
            List<PartyUserExt> partyUserExts = partyUserService.criteriaQuery(conditions);


            partyGroupModel.setGroupUsers(partyUserExts);
            model.addAttribute("model",partyGroupModel);

            return "/group/index";
        }
    }

    /*
    *  下面是小组管理功能模块
    */
    //管理小组基本信息
    @RequestMapping(value = "/conf/info")
    public String confInfo(@ModelAttribute PartyGroupModel partyGroupModel, HttpServletRequest request,HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getGroupId());
        //小组创建者才行
        if(partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())){

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            model.addAttribute("model",partyGroupModel);

            return "/group/confinfo";
        }
        else
            return "redirect:/errors/error/31";
    }

    //管理活动成员
    @RequestMapping(value = "/conf/member")
    public String confMember(@ModelAttribute PartyGroupModel partyGroupModel,HttpServletRequest request,HttpSession session,ModelMap model){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if(partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())){

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            //显示所有用户，包括被禁用，未审核的
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("groupId",partyGroupExt.getId()));
            List<PartyUserExt> partyUserExts = partyUserService.criteriaQuery(conditions);
            partyGroupModel.setGroupUsers(partyUserExts);

            model.addAttribute("model",partyGroupModel);

            return "/group/confmember";
        }
        else
            return "redirect:/errors/error/31";
    }

    //管理通知
    @RequestMapping(value = "/conf/news")
    public String confNews(@ModelAttribute PartyGroupModel partyGroupModel,HttpServletRequest request,HttpSession session,ModelMap model){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if(partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())){

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId",partyGroupExt.getPartyId()));
            //团队的通知 包含 活动的groupId=0和团队自己的groupId=自己的
            conditions.add(new OrCondition(new EqCondition("groupId",partyGroupExt.getId()),new EqCondition("groupId",0)));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.asc("groupId"));
            orders.add(Order.desc("createTime"));

            List<NewsExt> newsExts = newsService.criteriaQuery(conditions,orders);
            partyGroupModel.setGroupNews(newsExts);

            model.addAttribute("model",partyGroupModel);

            return "/group/confnews";
        }
        else
            return "redirect:/errors/error/31";
    }

    //管理论坛
    @RequestMapping(value = "/conf/forum")
    public String confForum(@ModelAttribute PartyGroupModel partyGroupModel,HttpServletRequest request,HttpSession session,ModelMap model){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if(partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())){

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId",partyGroupExt.getPartyId()));
            //团队的通知 包含 活动的groupId=0和团队自己的groupId=自己的
            conditions.add(new OrCondition(new EqCondition("groupId",partyGroupExt.getId()),new EqCondition("groupId",0)));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.asc("groupId"));
            orders.add(Order.desc("createTime"));

            List<DocumentExt> documentExts = documentService.criteriaQuery(conditions,orders);
            partyGroupModel.setGroupDocs(documentExts);

            model.addAttribute("model",partyGroupModel);

            return "/group/confdocs";
        }
        else
            return "redirect:/errors/error/31";
    }
    //管理文档
    @RequestMapping(value = "/conf/doc")
    public String confDocs(@ModelAttribute PartyGroupModel partyGroupModel,HttpServletRequest request,HttpSession session,ModelMap model){
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if(partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())){

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId",partyGroupExt.getPartyId()));
            //团队的通知 包含 活动的groupId=0和团队自己的groupId=自己的
            conditions.add(new OrCondition(new EqCondition("groupId",partyGroupExt.getId()),new EqCondition("groupId",0)));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.asc("groupId"));
            orders.add(Order.desc("createTime"));

            List<ForumExt> forumExts = forumService.criteriaQuery(conditions,orders);
            partyGroupModel.setGroupForums(forumExts);

            model.addAttribute("model",partyGroupModel);

            return "/group/confforums";
        }
        else
            return "redirect:/errors/error/31";
    }

    /*
    *  上面是小组管理功能模块
    */

}
