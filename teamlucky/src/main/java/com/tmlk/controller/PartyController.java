package com.tmlk.controller;

/**
 * Created by LaiGuoqiang on 2015/5/14.
 */

import com.alibaba.fastjson.JSON;
import com.tmlk.framework.mybatis.*;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.*;
import com.tmlk.model.*;
import com.tmlk.po.*;
import com.tmlk.service.*;
import com.tmlk.service.impl.PartyGroupService;
import com.tmlk.service.impl.PartyService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/party")
public class PartyController {

    private static SessionStatus sessionStatus = SessionStatus.getInstance();

    private static final Logger logger = Logger.getLogger(PartyController.class);

    @Autowired
    private ISysUserServiceExt sysUserService;

    @Autowired
    private IPartyServiceExt partyService;

    @Autowired
    private IPartyGroupServiceExt partyGroupService;

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
    public String goCreate() {
        return "/party/create";
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doCreate(@ModelAttribute PartyExt partyExt, HttpSession session, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            String partyCode = partyExt.getPartyCode();

            if (!partyService.existParty(partyCode)) {

                SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
                partyExt.setCreateBy(sessionUser.getSysUserId());

                PartyUserExt partyUserExt = partyService.launch(partyExt, request);
                sessionStatus.checkAndInParty(session, partyUserExt);

                result.setStatus(0);
            } else {
                result.setMessage("识别码已存在，请修改重新提交");
            }
        } catch (Exception ex) {
            result.setMessage("服务器异常，请重新提交");
            logger.error(ex);
        }
        return result;
    }

    /**
     * 检测活动识别码是否已被注册
     *
     * @param partyCode
     * @return true:未被注册，可以通过，false:已被注册，不能通过
     */
    @RequestMapping(value = "/checkPartyCode")
    @ResponseBody
    public boolean checkPartyCode(@RequestParam(value = "partyCode", required = true) String partyCode) {
        boolean result;
        try {
            result = !partyService.existParty(partyCode);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    /*
    *  下面是活动管理功能模块
    */
    @RequestMapping(value = "/conf")
    public String confIndex(@ModelAttribute PartyModel partyModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //只有活动创建者才能进入管理页面
        PartyExt partyExt = partyService.load(sessionUser.getPartyId());
        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());
        if (partyUserExt.getUserStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

            partyModel.setPartyExt(partyExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            partyModel.setPartyUsers(partyUserService.criteriaQuery(conditions));

            model.addAttribute("model", partyModel);
            return "/party/conf";
        } else {
            return "redirect:/errors/error/21";
        }
    }

    //管理活动成员
    @RequestMapping(value = "/userlist")
    @ResponseBody
    public JsonResult confMember(HttpServletRequest request, HttpSession session, ModelMap model) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(sessionUser.getPartyId());
            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

            if (partyUserExt.getUserStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

                List<ICondition> conditions = new ArrayList<ICondition>();
                conditions.add(new EqCondition("partyId", partyExt.getId()));

                List<Order> orders = new ArrayList<Order>();
                orders.add(Order.desc("lastLoginTime"));

                List<PartyUserExt> partyUserExtList = partyUserService.criteriaQuery(conditions);

                for (PartyUserExt partyUser : partyUserExtList) {
                    if (partyUser.getGroupId() > 0)
                        partyUser.setGroup(partyGroupService.load(partyUser.getGroupId()));

                    //用户状态
                    String statusName = Constants.PARTY_USER_STATUS_MAP.get(partyUser.getUserStatus());
                    if (FormatUtils.isEmpty(statusName)) {
                        if (partyUser.getUserStatus() % 2 == 1) {
                            partyUser.setStatusName("已禁用");

                        }
                        else
                            partyUser.setStatusName("未知");
                    } else
                        partyUser.setStatusName(statusName);

                    if (partyUser.getLastLoginTime() != null)
                        partyUser.setLastLoginTimeStr(FormatUtils.date2Str(partyUser.getLastLoginTime(),"yyyy-MM-dd HH:mm"));
                    else
                        partyUser.setLastLoginTimeStr("");
                }

                result.setData(partyUserExtList);
                result.setStatus(0);
            } else {
                result.setMessage("没有权限");
            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    //管理团队  获取所有的团队
    @RequestMapping(value = "/grouplist")
    @ResponseBody
    public JsonResult confGroup(HttpServletRequest request, HttpSession session, ModelMap model) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(sessionUser.getPartyId());
            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

            if (partyUserExt.getUserStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

                List<ICondition> conditions = new ArrayList<ICondition>();
                conditions.add(new EqCondition("partyId", partyExt.getId()));

                List<PartyGroupExt> partyGroupExtList = partyGroupService.criteriaQuery(conditions);
                for (PartyGroupExt partyGroup : partyGroupExtList) {
                    partyGroup.setCreateTimeStr(FormatUtils.date2Str(partyGroup.getCreateTime()));
                }
                result.setData(partyGroupExtList);
                result.setStatus(0);
            } else {
                result.setMessage("没有权限");
            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    //管理通知 获取所有的通知列表
    @RequestMapping(value = "/newslist")
    @ResponseBody
    public JsonResult confNews(HttpServletRequest request, HttpSession session, ModelMap model) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(sessionUser.getPartyId());

            if (sessionUser.isPartyAdmin()) {
                List<ICondition> conditions = new ArrayList<ICondition>();
                conditions.add(new EqCondition("partyId", partyExt.getId()));

                List<Order> orders = new ArrayList<Order>();
                orders.add(Order.desc("createTime"));

                List<NewsExt> newsExtList = newsService.criteriaQuery(conditions, orders);
                for (NewsExt newsExt : newsExtList) {
                    newsExt.setCreateTimeStr(FormatUtils.date2Str(newsExt.getCreateTime()));
                }
                result.setData(newsExtList);
                result.setStatus(0);
            } else {
                result.setMessage("没有权限");
            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    //管理论坛
    @RequestMapping(value = "/forumlist")
    @ResponseBody
    public JsonResult confForum(HttpServletRequest request, HttpSession session, ModelMap model) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(sessionUser.getPartyId());

            if (sessionUser.isPartyAdmin()) {
                List<ICondition> conditions = new ArrayList<ICondition>();
                conditions.add(new EqCondition("partyId", partyExt.getId()));

                List<Order> orders = new ArrayList<Order>();
                orders.add(Order.desc("createTime"));

                List<ForumExt> forumExtList = forumService.criteriaQuery(conditions, orders);
                for (ForumExt forumExt : forumExtList) {
                    forumExt.setCreateTimeStr(FormatUtils.date2Str(forumExt.getCreateTime()));
                }
                result.setData(forumExtList);
                result.setStatus(0);
            } else {
                result.setMessage("没有权限");
            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    //管理文档
    @RequestMapping(value = "/doclist")
    @ResponseBody
    public JsonResult confDoc(HttpServletRequest request, HttpSession session, ModelMap model) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(sessionUser.getPartyId());

            if (sessionUser.isPartyAdmin()) {
                List<ICondition> conditions = new ArrayList<ICondition>();
                conditions.add(new EqCondition("partyId", partyExt.getId()));

                List<Order> orders = new ArrayList<Order>();
                orders.add(Order.desc("createTime"));

                List<DocumentExt> documentExtList = documentService.criteriaQuery(conditions, orders);
                for (DocumentExt documentExt : documentExtList) {
                    documentExt.setCreateTimeStr(FormatUtils.date2Str(documentExt.getCreateTime()));
                }
                result.setData(documentExtList);
                result.setStatus(0);
            } else {
                result.setMessage("没有权限");
            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    /*
    *  上面是活动管理功能模块
    */

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String initIndex(HttpSession session, HttpServletRequest request, @ModelAttribute PartyModel partyModel, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        Long id = sessionUser.getPartyId();
        PartyExt partyExt = partyService.load(id);
        if (partyExt == null) {
            return "redirect:/errors/error/2";
        } else {
            session.setAttribute(Constants.SESSION_ISMEMBER, true);
            session.setAttribute(Constants.SESSION_AUTOCREATE, partyExt.getIsCustomBuild());//是否自动分组 进入活动的时候初始化

            List<ICondition> conditions = new ArrayList<ICondition>();

            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

            //更新Session
            sessionStatus.checkAndInParty(session, partyUserExt);
            //用户进入活动 活动 小组 活跃度+1
            partyService.loginParty(partyUserExt, partyExt);

            //用户已经是正式成员了
            if (partyUserExt.getGroupId() > 0 && partyUserExt.getUserStatus() > 6)
                model.addAttribute("join", true);
            else
                model.addAttribute("join", false);

            model.addAttribute("groupId", partyUserExt.getGroupId());


            conditions.clear();
            conditions.add(new EqCondition("partyId", partyExt.getId()));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.desc("groupStatus"));
            orders.add(Order.desc("hotCount"));
            List<PartyGroupExt> groupExtList = partyGroupService.criteriaQuery(conditions, orders);

            partyModel.setPartyGroups(groupExtList);

            conditions.clear();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            List<Integer> userStatusList = Arrays.asList(2, 4, 8, 10, 16);
            conditions.add(new InCondition("userStatus", userStatusList));

            orders.clear();
            orders.add(Order.desc("groupId"));
            orders.add(Order.asc("userName"));

            List<PartyUserExt> groupUserList = partyUserService.criteriaQuery(conditions, orders);

            partyModel.setPartyUsers(groupUserList);
            partyModel.setPartyExt(partyExt);

            model.addAttribute("model", partyModel);
            return "/party/index";
        }
    }

    @RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
    public String index(@PathVariable("id") Long id, HttpSession session, HttpServletRequest request, @ModelAttribute PartyModel partyModel, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
        if (id == null)
            id = sessionUser.getPartyId();
        PartyExt partyExt = partyService.load(id);
        if (partyExt == null) {
            return "redirect:/errors/error/2";
        } else {
            session.setAttribute(Constants.SESSION_AUTOCREATE, partyExt.getIsCustomBuild());//是否自动分组 进入活动的时候初始化

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            conditions.add(new EqCondition("sysUserId", sessionUser.getSysUserId()));
            List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(conditions);

            String partyUserId = sessionUser.getPartyUserId();
            if (sysPartyUserLinkExts.size() == 0 && partyUserId == null) {//已登录 但是不是该活动的成员
                if (partyExt.getIsPublic()) {
                    session.setAttribute(Constants.SESSION_ISMEMBER, false);
                } else {
                    return "redirect:/errors/error/3";
                }

                model.addAttribute("join", false);
                model.addAttribute("groupId", -1);
            } else {
                session.setAttribute(Constants.SESSION_ISMEMBER, true);

                if (sysPartyUserLinkExts.size() > 0)//按理说是1,如果这个系统用户登录过其他活动，partyuserid就会有值，这儿覆盖一下
                    partyUserId = sysPartyUserLinkExts.get(0).getPartyUserId();

                PartyUserExt partyUserExt = partyUserService.load(partyUserId);

                sessionStatus.checkAndInParty(session, partyUserExt);
                //用户进入活动 活动 小组 活跃度+1
                partyService.loginParty(partyUserExt, partyExt);

                if (sessionUser.getGroupId() > 0)
                    model.addAttribute("join", true);
                else
                    model.addAttribute("join", false);
                model.addAttribute("groupId", partyUserExt.getGroupId());
            }

            conditions.clear();
            conditions.add(new EqCondition("partyId", partyExt.getId()));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.desc("groupStatus"));
            orders.add(Order.desc("hotCount"));

            List<PartyGroupExt> groupExtList = partyGroupService.criteriaQuery(conditions, orders);

            partyModel.setPartyGroups(groupExtList);

            conditions.clear();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            List<Integer> userStatusList = Arrays.asList(2, 4, 8, 10, 16);
            conditions.add(new InCondition("userStatus", userStatusList));

            orders.clear();
            orders.add(Order.desc("groupId"));
            orders.add(Order.asc("userName"));

            List<PartyUserExt> groupUserList = partyUserService.criteriaQuery(conditions, orders);
            partyModel.setPartyUsers(groupUserList);

            partyModel.setPartyExt(partyExt);

            model.addAttribute("model", partyModel);
            return "/party/index";
        }
    }

    //公共活动列表
    @RequestMapping(value = "/list")
    public String goList(@ModelAttribute PartyModel partyModel, HttpServletRequest request, ModelMap model) {
        List<ICondition> conditions = new ArrayList<ICondition>();
        conditions.add(new EqCondition("isPublic", true));

        List<Order> orders = new ArrayList<Order>();
        orders.add(Order.desc("createTime"));

        int count = partyService.count(conditions);
        Pagination pp = partyModel.getPp();
        if (pp == null) {
            pp = new Pagination();
        }
        pp.setPageSize(16);
        pp.checkPagination(count);

        List<PartyExt> partyExtList = partyService.criteriaQuery(conditions, orders, pp);
        for (PartyExt partyExt : partyExtList) {
            partyExt.setPartyAuthor(sysUserService.load(partyExt.getCreateBy()));
            partyExt.setCreateTimeString(RelativeDateUtils.format(partyExt.getCreateTime()));
        }
        partyModel.setItems(partyExtList);

        model.addAttribute("model", partyModel);

        return "/party/list";
    }

    //更多公共活动
    @RequestMapping(value = "/list/next")
    @ResponseBody
    public JsonResult goListNext(@RequestParam(value = "currentPage", required = true) int currentPage, @RequestParam(value = "pageSize", required = true) int pageSize) {
        JsonResult result = new JsonResult();

        try {
            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("isPublic", true));

            List<Order> orders = new ArrayList<Order>();
            orders.add(Order.desc("createTime"));

            int count = partyService.count(conditions);
            Pagination pp = new Pagination();
            pp.setPageSize(pageSize);
            pp.setCurrentPage(currentPage + 1);
            pp.checkPagination(count);

            List<PartyExt> partyExtList = partyService.criteriaQuery(conditions, orders, pp);


            for (PartyExt partyExt : partyExtList) {
                partyExt.setPartyAuthor(sysUserService.load(partyExt.getCreateBy()));
                partyExt.setCreateTimeString(RelativeDateUtils.format(partyExt.getCreateTime()));
            }

            result.setStatus(0);
            result.setData(partyExtList);
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    /**
     * 保存设置信息
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public JsonResult saveInto(@ModelAttribute PartyExt partyExt, ModelMap model, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
            partyExt.setId(sessionUser.getPartyId());

            if (!FormatUtils.isEmpty(partyExt.getPartyName())) {
                partyService.updateParty(partyExt, 1);
            } else {
                partyService.updateParty(partyExt, 2);
            }
            result.setStatus(0);
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
            logger.trace(ex);
        }
        return result;
    }

    /*下面和用户导入相关*/

    @RequestMapping(value = "importuser")
    @ResponseBody
    public String importUser(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            String fileName = file.getOriginalFilename();

            String[] str = {".cvs", ".xls", ".xlsx"};

            boolean isExcel = false;
            for (String s : str) {
                if (fileName.endsWith(s)) {
                    isExcel = true;
                    break;
                }
            }

            if (!isExcel)
                throw new Exception("上传的文件不是支持的格式: xls xlsx");

            //获取数据
            Workbook wb = ExcelUtils.readWorkbook(file.getOriginalFilename(), file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);

            List<PartyUserExt> partyUserExtList = partyService.importMember(sheet, sessionUser.getPartyId(), request);
            for (PartyUserExt partyUserExt : partyUserExtList) {
                partyUserExt.setStatusName(Constants.PARTY_USER_STATUS_MAP.get(partyUserExt.getUserStatus()));
                partyUserExt.setLastLoginTimeStr(FormatUtils.date2Str(partyUserExt.getLastLoginTime()));
            }
            result.setData(partyUserExtList);
            result.setStatus(0);
        } catch (Exception e) {
            result.setStatus(1);
            result.setMessage(e.getMessage());
        }
        String str = JSONUtil.object2JsonString(result);

        return str;
    }

    @RequestMapping(value = "/exportModel")
    public void exportModel(HttpServletResponse response, HttpSession session) {
        OutputStream outputStream = null;
        try {
            String filePath = session.getServletContext().getRealPath("resource/model/memberImport.xlsx");
            String filename = "活动成员导入模板.xlsx";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            outputStream = response.getOutputStream();
            FileUtils.copyFile(new File(filePath), outputStream);
            outputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    /* 用户导入结束 */

    /* 用户手动创建 */
    @RequestMapping(value = "createuser")
    @ResponseBody
    public JsonResult createUser(@RequestParam(value = "loginName", required = true) String loginName, @RequestParam(value = "loginPwd", required = true) String loginPwd, @RequestParam(value = "userName", required = true) String userName, HttpServletRequest request, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);


            PartyExt party = partyService.load(sessionUser.getPartyId());

            if (partyUserService.findUserByName(party.getPartyCode() + "_" + loginName) != null) {
                throw new Exception("活动用户已存在");
            }

            PartyUserExt partyUserExt = new PartyUserExt();
            partyUserExt.setHotCount(0);
            partyUserExt.setPartyId(party.getId());
            partyUserExt.setGroupId(0L);//没有分组就是0
            partyUserExt.setUserStatus(2);
            partyUserExt.setLoginName(party.getPartyCode() + "_" + loginName);
            if (FormatUtils.isEmpty(loginPwd))
                partyUserExt.setLoginPwd(MD5Util.MD5("123456"));
            else
                partyUserExt.setLoginPwd(MD5Util.MD5(loginPwd));
            partyUserExt.setRegisterIP(FormatUtils.getIpAddress(request));
            partyUserExt.setRegisterTime(new Date());
            if (FormatUtils.isEmpty(userName))
                partyUserExt.setUserName(loginName);
            else
                partyUserExt.setUserName(userName);

            partyUserService.register(partyUserExt);

            result.setStatus(0);

            List<PartyUserExt> partyUserExtList = new ArrayList<PartyUserExt>();
            partyUserExt.setStatusName(Constants.PARTY_USER_STATUS_MAP.get(partyUserExt.getUserStatus()));
            partyUserExt.setLastLoginTimeStr(FormatUtils.date2Str(partyUserExt.getLastLoginTime()));
            partyUserExtList.add(partyUserExt);

            result.setData(partyUserExtList);
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }
    /* 用户手动创建结束 */


    /* 活动用户管理 */
    @RequestMapping(value = "/uservalid")
    @ResponseBody
    public JsonResult setUserValid(@RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "setvalid", required = true) boolean setvalid, HttpServletRequest request, HttpSession session) {
        JsonResult result = new JsonResult();
        try {

            PartyUserExt partyUserExt = partyUserService.load(userId);
            if (partyUserExt != null) {
                if (partyUserExt.getUserStatus() == 16) {
                    throw new Exception("活动创建者不能禁用...");
                }

                int userStatus = partyUserExt.getUserStatus();
                if (setvalid) {//设置为激活
                    partyUserExt.setUserStatus(userStatus - 1);
                } else
                    partyUserExt.setUserStatus(userStatus + 1);

                partyUserService.update(partyUserExt);

                result.setStatus(0);
            } else {
                result.setMessage("用户不存在");
            }
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/resetpwd")
    @ResponseBody
    public JsonResult resetPwd(@RequestParam(value = "userId", required = true) String userId, HttpServletRequest request, HttpSession session) {
        JsonResult result = new JsonResult();
        try {

            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);
            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(sessionUser.getPartyId());
            PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

            if (partyUserExt.getUserStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

                PartyUserExt partyUser = partyUserService.load(userId);
                if (partyUserExt != null) {

                    partyUser.setLoginPwd(MD5Util.MD5("123456"));

                    partyUserService.update(partyUser);

                    result.setMessage("密码已重置为123456");
                    result.setStatus(0);
                } else {
                    result.setMessage("用户不存在");
                }
            } else
                result.setMessage("没有权限");
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }
    /* 活动用户管理结束 */

    /* 查询某个成员为小组长 */
    @RequestMapping(value = "searchnogroupuser")
    @ResponseBody
    public JsonResult SearchNoGroupUser(@RequestParam(value = "userName", required = true) String userName,HttpSession session){
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            if (sessionUser.isPartyAdmin()) {
                List<ICondition> conditions = new ArrayList<ICondition>();
                conditions.add(new EqCondition("partyId",sessionUser.getPartyId()));
                conditions.add(new EqCondition("userStatus",2));
                conditions.add(new LikeCondition("userName",userName));

                List<Order> orders = new ArrayList<Order>();
                orders.add(Order.asc("userName"));

                Pagination pp = new Pagination();
                pp.setCurrentPage(1);
                pp.setPageSize(10);

                List<PartyUserExt> partyUserExtList = partyUserService.criteriaQuery(conditions,null,pp);

                result.setData(partyUserExtList);
                result.setStatus(0);
            } else
                result.setMessage("没有权限");
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }

    /* 小组手动创建 */
    @RequestMapping(value = "creategroup")
    @ResponseBody
    public JsonResult createGroup(@ModelAttribute PartyGroupExt partyGroupExt, HttpServletRequest request, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            if(sessionUser.isPartyAdmin()) {
                partyGroupExt.setCreateTime(new Date());
                partyGroupExt.setPartyId(sessionUser.getPartyId());
                partyGroupExt.setGroupStatus(1);
                partyGroupExt.setMemberCount(1);
                partyGroupExt.setHotCount(0);
                partyGroupExt.setIsSourcePublic(true);
                partyGroupExt.setIsCustomJoin(true);

                partyGroupService.build(partyGroupExt);

                //小组长 设置其小组
                PartyUserExt partyUserExt = partyUserService.load(partyGroupExt.getCreateBy());
                partyUserExt.setGroupId(partyGroupExt.getId());
                partyUserExt.setUserStatus(10);//已进入小组,且是活动创建者
                partyUserService.update(partyUserExt);


                List<PartyGroupExt> partyGroupExtList = new ArrayList<PartyGroupExt>();
                partyGroupExt.setCreateTimeStr(FormatUtils.date2Str(partyGroupExt.getCreateTime()));
                partyGroupExtList.add(partyGroupExt);

                result.setData(partyGroupExtList);
                result.setStatus(0);
            }else{
                result.setMessage("不是活动创建者，没有权限");
            }
        } catch (Exception ex) {
            result.setMessage("服务器异常，请重新提交");
            logger.error(ex);
        }
        return result;
    }
    /* 小组手动创建结束 */


    /*加入公共活动*/
    @RequestMapping(value = "/join")
    @ResponseBody
    public JsonResult joinParty(@RequestParam(value = "partyId", required = true) Long partyId, HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            //只有活动创建者才能进入管理页面
            PartyExt partyExt = partyService.load(partyId);

            //系统用户
            SysUserExt sysUserExt = sysUserService.load(sessionUser.getSysUserId());

            //加入活动
            PartyUserExt partyUserExt = partyService.join(sysUserExt, partyExt);

            //刷新Session
            sessionStatus.checkAndInParty(session, partyUserExt);

            result.setStatus(0);
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }
}
