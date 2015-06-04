package com.tmlk.controller;

/**
 * Created by LaiGuoqiang on 2015/5/14.
 */

import com.alibaba.fastjson.JSON;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.*;
import com.tmlk.model.*;
import com.tmlk.po.*;
import com.tmlk.service.*;
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
                        if (partyUser.getUserStatus() % 2 == 1)
                            partyUser.setStatusName("已禁用");
                        else
                            partyUser.setStatusName("未知");
                    } else
                        partyUser.setStatusName(statusName);

                    if (partyUser.getLastLoginTime() != null)
                        partyUser.setLastLoginTimeStr(FormatUtils.date2Str(partyUser.getLastLoginTime()));
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

    //管理活动基本信息
    @RequestMapping(value = "/conf/info")
    public String confInfo(@ModelAttribute PartyModel partyModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //只有活动创建者才能进入管理页面
        PartyExt partyExt = partyService.load(sessionUser.getPartyId());
        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());

        if (partyUserExt.getUserStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

            partyModel.setPartyExt(partyExt);

            model.addAttribute("model", partyModel);
            return "/party/confinfo";
        } else {
            return "redirect:/errors/error/21";
        }
    }

    //管理团队
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

    //管理通知
    @RequestMapping(value = "/conf/news")
    public String confNews(@ModelAttribute PartyModel partyModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //只有活动创建者才能进入管理页面
        PartyExt partyExt = partyService.load(sessionUser.getPartyId());
        if (partyExt.getPartyStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

            partyModel.setPartyExt(partyExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            partyModel.setPartyNews(newsService.criteriaQuery(conditions));

            model.addAttribute("model", partyModel);
            return "/party/confnews";
        } else {
            return "redirect:/errors/error/21";
        }
    }

    //管理论坛
    @RequestMapping(value = "/conf/forum")
    public String confForum(@ModelAttribute PartyModel partyModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //只有活动创建者才能进入管理页面
        PartyExt partyExt = partyService.load(sessionUser.getPartyId());
        if (partyExt.getPartyStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

            partyModel.setPartyExt(partyExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            partyModel.setPartyForums(forumService.criteriaQuery(conditions));

            model.addAttribute("model", partyModel);
            return "/party/confforum";
        } else {
            return "redirect:/errors/error/21";
        }
    }

    //管理文档
    @RequestMapping(value = "/conf/doc")
    public String confDocs(@ModelAttribute PartyModel partyModel, HttpServletRequest request, HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        //只有活动创建者才能进入管理页面
        PartyExt partyExt = partyService.load(sessionUser.getPartyId());
        if (partyExt.getPartyStatus() == 16 && partyExt.getCreateBy().equals(sessionUser.getSysUserId())) { //16才是管理员

            partyModel.setPartyExt(partyExt);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            partyModel.setPartyDocs(documentService.criteriaQuery(conditions));

            model.addAttribute("model", partyModel);

            return "/party/confdocs";
        } else {
            return "redirect:/errors/error/21";
        }
    }

    /*
    *  上面是活动管理功能模块
    */

    @RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
    public String index(@PathVariable("id") Long id, HttpSession session, HttpServletRequest request, @ModelAttribute PartyModel partyModel, ModelMap model) {
        PartyExt partyExt = partyService.load(id);
        if (partyExt == null) {
            return "redirect:/errors/error/2";
        } else {
            SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_USER);

            session.setAttribute(Constants.SESSION_AUTOCREATE, partyExt.getIsCustomBuild());//是否自动分组 进入活动的时候初始化

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId", partyExt.getId()));
            conditions.add(new EqCondition("sysUserId", sessionUser.getSysUserId()));
            List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(conditions);
            if (sysPartyUserLinkExts.size() == 0) {//已登录 但是不是该活动的成员
                if (partyExt.getIsPublic()) {
                    //TODO 公共活动用户可以参观
                } else {
                    return "redirect:/errors/error/3";
                }
            } else {
                //TODO 用户进入活动 活动 小组 活跃度+1

                PartyUserExt partyUserExt = partyUserService.load(sysPartyUserLinkExts.get(0).getPartyUserId());
                sessionStatus.checkAndInParty(session, partyUserExt);
            }

            partyModel.setPartyExt(partyExt);
            model.addAttribute("model", partyModel);
            return "/party/index";
        }
    }

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
            if(partyUserExt!=null){
                if(partyUserExt.getUserStatus() == 16){
                    throw new Exception("活动创建者不能禁用...");
                }

                int userStatus = partyUserExt.getUserStatus();
                if(setvalid){//设置为激活
                    partyUserExt.setUserStatus(userStatus-1);
                }
                else
                    partyUserExt.setUserStatus(userStatus+1);

                partyUserService.update(partyUserExt);

                result.setStatus(0);
            }else{
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

            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
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
            }else
                result.setMessage("没有权限");
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
        }
        return result;
    }
    /* 活动用户管理结束 */
}
