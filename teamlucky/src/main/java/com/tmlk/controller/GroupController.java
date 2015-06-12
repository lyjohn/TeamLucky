package com.tmlk.controller;

/**
 * Created by LaiGuoqiang on 2015/5/14.
 */

import com.tmlk.framework.mybatis.*;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.model.*;
import com.tmlk.po.*;
import com.tmlk.service.*;
import com.tmlk.service.impl.PartyUserService;
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
    public String goCreate(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());
        if (partyUserExt.getUserStatus() == 8) { //已经在小组 则不创建 直接进入了 也就是不能创建多个小组

            return "redirect:/group/index/" + partyUserExt.getGroupId();
        }

        return "/group/create";
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doCreate(@ModelAttribute PartyGroupExt partyGroupExt, HttpSession session, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
            partyGroupExt.setCreateBy(sessionUser.getPartyUserId()); //创建者是活动用户的帐号
            partyGroupExt.setCreateTime(new Date());
            partyGroupExt.setPartyId(sessionUser.getPartyId());

            partyGroupExt.setGroupStatus(1);
            partyGroupExt.setMemberCount(1);
            partyGroupExt.setHotCount(0);

            partyGroupService.build(partyGroupExt);

            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());
            partyUserExt.setGroupId(partyGroupExt.getId());
            if (partyUserExt.getUserStatus() == 16) { //是这个活动的创建者

            } else {
                partyUserExt.setUserStatus(10);//已进入小组,且是活动创建者
            }
            partyUserService.update(partyUserExt);

            sessionStatus.checkAndJoinGroup(session, partyGroupExt.getId());

            result.setStatus(0);
        } catch (Exception ex) {
            result.setMessage("服务器异常，请重新提交");
            logger.error(ex);
        }
        return result;
    }


    @RequestMapping(value = {"/index/{id}"}, method = RequestMethod.GET)
    public String indexPublic(@PathVariable("id") Long id, @ModelAttribute PartyGroupModel partyGroupModel, HttpSession session, HttpServletRequest request, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(id);
        if (partyGroupExt == null) {
            return "redirect:/errors/error/30";
        } else {
            //非本队成员（活动创建者除外）不能进入该页
            if (sessionUser.getGroupId() != id && !sessionUser.isPartyAdmin()) {
                return "redirect:/errors/error/32";
            }

            if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {
                partyGroupModel.setCreater(true);
            } else
                partyGroupModel.setCreater(false);

            PartyUserExt partyUserExt = partyUserService.load(partyGroupExt.getCreateBy());
            partyGroupExt.setGroupAuthor(partyUserExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("groupId", partyGroupExt.getId()));
            if (!sessionUser.isPartyAdmin())
                conditions.add(new GtCondition("userStatus", 8));//只能看到当前在活动的用户  被禁用，或者审批中的不能看到

            List<PartyUserExt> partyUserExts = partyUserService.criteriaQuery(conditions);

            partyGroupModel.setGroupUsers(partyUserExts);
            partyGroupModel.setPartyGroupExt(partyGroupExt);
            model.addAttribute("model", partyGroupModel);

            return "/group/index";
        }
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(@ModelAttribute PartyGroupModel partyGroupModel, HttpSession session, HttpServletRequest request, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getGroupId());
        if (partyGroupExt == null) {
            return "redirect:/errors/error/30";
        } else {
            if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {
                partyGroupModel.setCreater(true);
            } else
                partyGroupModel.setCreater(false);

            PartyUserExt partyUserExt = partyUserService.load(partyGroupExt.getCreateBy());
            partyGroupExt.setGroupAuthor(partyUserExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("groupId", partyGroupExt.getId()));
            conditions.add(new GeCondition("userStatus", 8));//只能看到当前在活动的用户  被禁用，或者审批中的不能看到

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.desc("lastLoginTime"));

            List<PartyUserExt> partyUserExts = partyUserService.criteriaQuery(conditions,orders);

            partyGroupModel.setGroupUsers(partyUserExts);
            partyGroupModel.setPartyGroupExt(partyGroupExt);

            model.addAttribute("model", partyGroupModel);

            return "/group/index";
        }
    }

    /*
    *  下面是小组管理功能模块
    */
    //管理小组基本信息
    @RequestMapping(value = "/conf/info")
    public String confInfo(@ModelAttribute PartyGroupModel partyGroupModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getGroupId());
        //小组创建者才行
        if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            model.addAttribute("model", partyGroupModel);

            return "/group/confinfo";
        } else
            return "redirect:/errors/error/31";
    }

    //管理活动成员
    @RequestMapping(value = "/conf/member")
    public String confMember(@ModelAttribute PartyGroupModel partyGroupModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            //显示所有用户，包括被禁用，未审核的
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("groupId", partyGroupExt.getId()));
            List<PartyUserExt> partyUserExts = partyUserService.criteriaQuery(conditions);
            partyGroupModel.setGroupUsers(partyUserExts);

            model.addAttribute("model", partyGroupModel);

            return "/group/confmember";
        } else
            return "redirect:/errors/error/31";
    }

    //管理通知
    @RequestMapping(value = "/conf/news")
    public String confNews(@ModelAttribute PartyGroupModel partyGroupModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyGroupExt.getPartyId()));
            //团队的通知 包含 活动的groupId=0和团队自己的groupId=自己的
            conditions.add(new OrCondition(new EqCondition("groupId", partyGroupExt.getId()), new EqCondition("groupId", 0)));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.asc("groupId"));
            orders.add(Order.desc("createTime"));

            List<NewsExt> newsExts = newsService.criteriaQuery(conditions, orders);
            partyGroupModel.setGroupNews(newsExts);

            model.addAttribute("model", partyGroupModel);

            return "/group/confnews";
        } else
            return "redirect:/errors/error/31";
    }

    //管理论坛
    @RequestMapping(value = "/conf/forum")
    public String confForum(@ModelAttribute PartyGroupModel partyGroupModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyGroupExt.getPartyId()));
            //团队的通知 包含 活动的groupId=0和团队自己的groupId=自己的
            conditions.add(new OrCondition(new EqCondition("groupId", partyGroupExt.getId()), new EqCondition("groupId", 0)));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.asc("groupId"));
            orders.add(Order.desc("createTime"));

            List<DocumentExt> documentExts = documentService.criteriaQuery(conditions, orders);
            partyGroupModel.setGroupDocs(documentExts);

            model.addAttribute("model", partyGroupModel);

            return "/group/confdocs";
        } else
            return "redirect:/errors/error/31";
    }

    //管理文档
    @RequestMapping(value = "/conf/doc")
    public String confDocs(@ModelAttribute PartyGroupModel partyGroupModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getPartyId());
        //小组创建者才行
        if (partyGroupExt.getCreateBy().equals(sessionUser.getPartyUserId())) {

            partyGroupModel.setPartyGroupExt(partyGroupExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyGroupExt.getPartyId()));
            //团队的通知 包含 活动的groupId=0和团队自己的groupId=自己的
            conditions.add(new OrCondition(new EqCondition("groupId", partyGroupExt.getId()), new EqCondition("groupId", 0)));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.asc("groupId"));
            orders.add(Order.desc("createTime"));

            List<ForumExt> forumExts = forumService.criteriaQuery(conditions, orders);
            partyGroupModel.setGroupForums(forumExts);

            model.addAttribute("model", partyGroupModel);

            return "/group/confforums";
        } else
            return "redirect:/errors/error/31";
    }

    /*
    *  上面是小组管理功能模块
    */


    /**
     * 保存设置信息
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public JsonResult saveInto(@ModelAttribute PartyGroupExt partyGroupExt, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
            partyGroupExt.setId(sessionUser.getGroupId());

            partyGroupService.updateGroup(partyGroupExt);

            result.setStatus(0);
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
            logger.trace(ex);
        }
        return result;
    }

    @RequestMapping(value = "/join")
    @ResponseBody
    public JsonResult doJoinGroup(@RequestParam(value = "groupId", required = true) Long groupId, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            PartyGroupExt partyGroupExt = partyGroupService.load(groupId);
            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

            PartyExt partyExt = partyService.load(sessionUser.getPartyId());
            if (partyGroupExt.getGroupStatus() == 2) { //组已满
                throw new Exception("该组人数已满");
            }

            if (partyExt.getMemberNumMax() != 0 && partyExt.getMemberNumMax() == partyGroupExt.getMemberCount()) {
                partyGroupExt.setGroupStatus(2);
                partyGroupService.update(partyGroupExt);
                throw new Exception("您所在的小组人数已达上限");
            }

            if (partyUserExt.getGroupId() > 0) {
                throw new Exception("您是不是已经加入了其他小组");
            }

            if (partyGroupExt.getIsCustomJoin()) {
                partyUserExt.setGroupId(partyGroupExt.getId());

                if(partyUserExt.getUserStatus()!=16) //活动创建者.. 只需要设置GroupId即可
                    partyUserExt.setUserStatus(8);
                partyUserService.joinGroup(partyUserExt);

                partyGroupExt.setMemberCount(partyGroupExt.getMemberCount() + 1);
                partyGroupService.update(partyGroupExt);

                result.setMessage("成功加入小组【" + partyGroupExt.getGroupName() + "】");
            } else {
                partyUserExt.setGroupId(partyGroupExt.getId());
                //partyUserExt.setUserStatus(4); //预备组员

                //TODO 为了测试效果 暂时都将所有加入的 直接置为正式的
                if(partyUserExt.getUserStatus()!=16) //活动创建者.. 只需要设置GroupId即可
                    partyUserExt.setUserStatus(8);
                partyUserService.joinGroup(partyUserExt);

                partyGroupExt.setMemberCount(partyGroupExt.getMemberCount() + 1);
                partyGroupService.update(partyGroupExt);

                result.setMessage("申请加入小组【" + partyGroupExt.getGroupName() + "】,等待组长通过，只能同时申请一个小组");
            }

            result.setData(partyUserExt);
            result.setStatus(0);

        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
            logger.trace(ex);
        }
        return result;
    }


    @RequestMapping(value = "/invite")
    @ResponseBody
    public JsonResult doInviteUser(@RequestParam(value = "userId", required = true) String userId, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            PartyExt partyExt = partyService.load(sessionUser.getPartyId());

            PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getGroupId());
            if (partyGroupExt == null)
                throw new Exception("您未加入小组，不能邀请.");

            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());
//            if(!partyUserExt.getId().equals(partyGroupExt.getCreateBy()))
//            {
//                throw new Exception("您不是小组的创建人，不能要请别人");
//            }
            //不是自动进入的 并且邀请人不是创建人 则不能邀请
            if (!partyGroupExt.getIsCustomJoin() && !partyUserExt.getId().equals(partyGroupExt.getCreateBy())) {
                throw new Exception("您没有邀请他人的权限");
            }
            PartyUserExt inviteWho = partyUserService.load(userId);
            if (inviteWho == null)
                throw new Exception("您在邀请谁？");

            if (partyGroupExt.getGroupStatus() == 2) //组已满
                throw new Exception("您的小组人数已满");

            if (partyExt.getMemberNumMax() != 0 && partyExt.getMemberNumMax() == partyGroupExt.getMemberCount()) {
                partyGroupExt.setGroupStatus(2);
                partyGroupService.update(partyGroupExt);
                throw new Exception("您的小组人数已满");
            }

            if (inviteWho.getGroupId() != 0) {
                result.setMessage("【" + inviteWho.getUserName() + "】已有小组");
            } else {
                inviteWho.setGroupId(partyGroupExt.getId());

                if(inviteWho.getUserStatus() != 16)//活动创建者.. 只需要设置GroupId即可
                    inviteWho.setUserStatus(8);

                partyUserService.joinGroup(inviteWho);
                result.setMessage("成功邀请【" + inviteWho.getUserName() + "】加入小组【" + partyGroupExt.getGroupName() + "】");

                partyGroupExt.setMemberCount(partyGroupExt.getMemberCount() + 1);
                partyGroupService.update(partyGroupExt);

                result.setData(partyGroupExt);
                result.setStatus(0);

            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
            logger.trace(ex);
        }
        return result;
    }
}
