package com.tmlk.controller;

/**
 * Created by LaiGuoqiang on 2015/5/14.
 */

import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.model.*;
import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysPartyUserLinkExt;
import com.tmlk.po.SysUserExt;
import com.tmlk.service.IPartyServiceExt;
import com.tmlk.service.IPartyUserServiceExt;
import com.tmlk.service.ISysPartyUserLinkServiceExt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/party")
public class PartyController {

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

    private static SessionStatus sessionStatus = SessionStatus.getInstance();

    private static final Logger logger = Logger.getLogger(PartyController.class);

    @Autowired
    private IPartyServiceExt partyService;

    @Autowired
    private ISysPartyUserLinkServiceExt sysPartyUserLinkService;

    @Autowired
    private IPartyUserServiceExt partyUserService;

    @RequestMapping(value = "/create")
    public String goCreate(){
        return "/party/create";
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doCreate(@ModelAttribute PartyExt partyExt, HttpSession session, HttpServletRequest request){
        JsonResult result = new JsonResult();
        try{
            String partyCode = partyExt.getPartyCode();

            if(!partyService.existParty(partyCode)){

                SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
                partyExt.setCreateBy(sessionUser.getSysUserId());

                partyService.launch(partyExt, request);

                result.setStatus(0);

                //测试
                result.setData(partyExt);
            }
            else{
                result.setMessage("识别码已存在，请修改重新提交");
            }
        }catch (Exception ex){
            result.setMessage("服务器异常，请重新提交");
            logger.error(ex);
        }
        return result;
    }

    /*
    *  下面是活动管理功能模块
    */
    //管理活动基本信息
    @RequestMapping(value = "/conf/info")
    public String confInfo(@ModelAttribute PartyModel partyModel, HttpServletRequest request,HttpSession session, ModelMap model) {
        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

        //查询获取活动用户
        PartyUserExt partyUserExt = partyUserService.load(sessionUser.getPartyUserId());


        return "/party/confinfo";
    }

    //管理活动成员
    @RequestMapping(value = "/conf/member")
    public String confMember(@ModelAttribute PartyUserModel partyUserModel,HttpServletRequest request,HttpSession session,ModelMap model){

        return "/party/confmember";
    }

    //管理团队
    @RequestMapping(value = "/conf/group")
    public String confGroup(@ModelAttribute PartyGroupModel partyGroupModel,HttpServletRequest request,HttpSession session,ModelMap model){

        return "/party/confgroup";
    }

    //管理通知
    @RequestMapping(value = "/conf/news")
    public String confNews(@ModelAttribute NewsModel newsModel,HttpServletRequest request,HttpSession session,ModelMap model){

        return "/party/confnews";
    }

    //管理论坛
    @RequestMapping(value = "/conf/forum")
    public String confForum(@ModelAttribute ForumModel forumModel,HttpServletRequest request,HttpSession session,ModelMap model){

        return "/party/confforum";
    }
    //管理文档
    @RequestMapping(value = "/conf/doc")
    public String confDocs(@ModelAttribute DocumentModel documentModel,HttpServletRequest request,HttpSession session,ModelMap model){

        return "/party/confdocs";
    }

    /*
    *  上面是活动管理功能模块
    */

    @RequestMapping(value = "/index/{id}",method = RequestMethod.GET)
    public String index(@PathVariable("id") Long id, HttpSession session,HttpServletRequest request,@ModelAttribute PartyModel partyModel, ModelMap model){
        PartyExt partyExt = partyService.load(id);
        if(partyExt == null){
            return "/errors/error/2";
        }else{
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

            List<ICondition> conditions = new ArrayList<ICondition>();
            conditions.add(new EqCondition("partyId",partyExt.getId()));
            conditions.add(new EqCondition("sysUserId",sessionUser.getSysUserId()));
            List<SysPartyUserLinkExt> sysPartyUserLinkExts = sysPartyUserLinkService.criteriaQuery(conditions);
            if(sysPartyUserLinkExts.size() == 0){//已登录 但是不是该活动的成员
                if(partyExt.getIsPublic()){
                    //TODO 公共活动用户可以参观
                }else{
                    return "/errors/error/3";
                }
            }else{
                //TODO 用户进入活动 活动 小组 活跃度+1

                PartyUserExt partyUserExt = partyUserService.load(sysPartyUserLinkExts.get(0).getPartyUserId());
                sessionStatus.checkAndInParty(session,partyUserExt,request);
            }

            partyModel.setPartyExt(partyExt);
            model.addAttribute("model",partyModel);
            return "/party/index";
        }
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    public String doEdit(@ModelAttribute PartyModel partyModel, HttpServletRequest request, ModelMap model) {
        try {
            PartyExt partyExt = partyModel.getPartyExt();
            PartyExt partyExtPer = partyService.load(partyExt.getId());

            partyExtPer.setPartyName(partyExt.getPartyName());
            partyExtPer.setPartyRemark(partyExt.getPartyRemark());
            partyExtPer.setIsCustomBuild(partyExt.getIsCustomBuild());
            partyExtPer.setMemberNumMax(partyExt.getMemberNumMax());
            partyExtPer.setMemberNumMin(partyExt.getMemberNumMin());
//            partyExtPer.setBuildEndTime(partyExt.getBuildEndTime());

            partyService.edit(partyExtPer);

            partyModel.setPartyExt(partyExtPer);
            model.addAttribute("model",partyModel);

            JsonResult result = new JsonResult();
            result.setStatus(0);
            result.setMessage("保存成功");
            model.addAttribute("result", result);

            return "/party/index";
        } catch (Exception ex){
            partyModel.setPartyExt(partyModel.getPartyExt());
            model.addAttribute("model",partyModel);

            JsonResult result = new JsonResult();
            result.setMessage("保存活动失败");
            model.addAttribute("result", result);

            logger.error(ex.getStackTrace());
            return "/party/index";
        }
    }

}
