package com.tmlk.controller;

import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.*;
import com.tmlk.po.*;
import com.tmlk.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by laiguoqiang on 15/5/13.
 */
@Controller
@RequestMapping(value = "/avatar")
public class AvatarController {

    private static SessionStatus sessionStatus = SessionStatus.getInstance();

    private static final Logger logger = Logger.getLogger(AvatarController.class);

    @Autowired
    private ISysUserServiceExt sysUserService;

    @Autowired
    private IPartyUserServiceExt partyUserService;

    @Autowired
    private IPartyServiceExt partyService;

    @Autowired
    private IPartyGroupServiceExt partyGroupService;

    @Autowired
    private IDocumentServiceExt documentService;

    //下面是头像相关的页面

    @RequestMapping(value = "/upload")
    @ResponseBody
    public String uploadAvatar(@RequestParam(value = "file",required = true) MultipartFile file,@RequestParam(value = "type",required = true) int type,HttpServletRequest request,HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            //这个路径已略过，未登录还是可以进来的  没做进一步的拦截 在这儿判断下是否登录吧
            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            if(session == null)
                return null;

            String fileName = file.getOriginalFilename();

            String[] str = { ".jpg", ".jpeg", ".bmp", ".gif", ".png"};

            boolean isPic = false;
            for (String s : str) {
                if (fileName.endsWith(s)) {
                    isPic = true;
                    break;
                }
            }

            if(!isPic)
                throw new Exception("上传的文件不是有效图片: jpg jpeg bmp gif png");

            //上传头像
            String filePath = documentService.doAvatarSave(request, file, type);

            result.setData(filePath);
            result.setStatus(0);
        }catch(Exception e) {
            result.setStatus(1);
            result.setMessage(e.getMessage());
        }
        String str = JSONUtil.object2JsonString(result);

        return str;
    }

    //type: 1:用户 2:活动 3:小组
    @RequestMapping(value = "/cut")
    @ResponseBody
    public JsonResult cutAvatar(@RequestParam(value = "filePath",required = true) String filePath,@RequestParam(value = "type",required = true) int type,@RequestParam(value = "x1",required = true) int x1,@RequestParam(value = "y1",required = true) int y1,@RequestParam(value = "x2",required = true) int x2,@RequestParam(value = "y2",required = true) int y2,@RequestParam(value = "user",required = false,defaultValue = "0") int userTye,HttpServletRequest request,HttpSession session) {
        JsonResult result = new JsonResult();
        try {
            int width = x2-x1;
            int height = y2-y1;
            //剪切头像
            String savePath = documentService.doAvatarCut(request,session, filePath, type,x1,y1,width,height);

            if(userTye == 1){//是系统用户， 直接把头像存了
                SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
                sysUserService.uploadAvatar(filePath,sessionUser.getSysUserId());
            }else if(userTye == 2){//是活动用户，直接把头像存了
                SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
                partyUserService.uploadAvatar(filePath,sessionUser.getPartyUserId());
            }
            else if(userTye == 3) {//主要和创建活动或小组的情况区分
                if (type == 2) {
                    SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

                    PartyExt partyExt = partyService.load(sessionUser.getPartyId());
                    partyExt.setPartyCover(filePath);
                    partyService.update(partyExt);
                } else if (type == 3) {
                    SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

                    PartyGroupExt partyGroupExt = partyGroupService.load(sessionUser.getGroupId());
                    partyGroupExt.setGroupCover(filePath);
                    partyGroupService.update(partyGroupExt);
                }
            }
            result.setData(savePath);
            result.setStatus(0);
        }catch(Exception e) {
            result.setStatus(1);
            result.setMessage(e.getMessage());
        }

        return result;
    }



    /**
     * 前台页面显示图片
     * @param t 标志用户是 1:系统用户 还是 2:活动用户
     * @param id 用户的ID
     * @param response 返回
     * @return
     */
    @RequestMapping(value = "/user/{t}/{id}")
    public String getUserAvatar(@PathVariable("t") int t,@PathVariable("id") String id, HttpServletResponse response,HttpServletRequest request){
        try{
            String avatarPath = "";
            if(t==1) {
                SysUserExt sysUserExt = sysUserService.load(id);
                if(sysUserExt != null)
                    avatarPath = sysUserExt.getUserAvatar();
            }
            else {
                PartyUserExt partyUserExt = partyUserService.load(id);
                if(partyUserExt != null)
                    avatarPath = partyUserExt.getUserAvatar();
            }

            if(FormatUtils.isEmpty(avatarPath))
                avatarPath = "resource"+File.separator+"avatar"+File.separator+"default"+File.separator+"user.png";

            avatarPath = request.getSession().getServletContext().getRealPath(avatarPath);

            File file = new File(avatarPath);

            response.setContentType("image/jpeg; charset=GBK");
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;
            return null;
        }catch(Exception e){
            return null;
        }
    }


    /**
     * 前台页面显示图片
     * @param id 活动的ID
     * @param response 返回
     * @return
     */
    @RequestMapping(value = "/party/{id}")
    public String getPartyAvatar(@PathVariable("id") Long id, HttpServletResponse response,HttpServletRequest request){
        try{
            String avatarPath = "";
            PartyExt partyExt = partyService.load(id);
            avatarPath = partyExt.getPartyCover();

            if(FormatUtils.isEmpty(avatarPath)) {
                int rdn = (int)Math.round(Math.random()*5);
                System.out.println(rdn);
                avatarPath = "resource" + File.separator + "avatar" + File.separator + "default" + File.separator + "party"+rdn+".png";
            }
            avatarPath = request.getSession().getServletContext().getRealPath(avatarPath);

            File file = new File(avatarPath);

            response.setContentType("image/jpeg; charset=GBK");
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;
            return null;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 前台页面显示图片
     * @param id 小组的ID
     * @param response 返回
     * @return
     */
    @RequestMapping(value = "/group/{id}")
    public String getGroupAvatar(@PathVariable("id") Long id, HttpServletResponse response,HttpServletRequest request){
        try{
            String avatarPath = "";
            PartyGroupExt partyGroupExt = partyGroupService.load(id);
            avatarPath = partyGroupExt.getGroupCover();

            if(FormatUtils.isEmpty(avatarPath))
                avatarPath = "resource"+File.separator+"avatar"+File.separator+"default"+File.separator+"group.png";

            avatarPath = request.getSession().getServletContext().getRealPath(avatarPath);

            File file = new File(avatarPath);

            response.setContentType("image/jpeg; charset=GBK");
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;
            return null;
        }catch(Exception e){
            return null;
        }
    }
}
