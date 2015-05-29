package com.tmlk.controller;

import com.tmlk.aop.SysControllerLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.*;
import com.tmlk.model.PartyModel;
import com.tmlk.po.SysUserExt;
import com.tmlk.service.IDocumentServiceExt;
import com.tmlk.service.IPartyServiceExt;
import com.tmlk.service.IPartyUserServiceExt;
import com.tmlk.service.ISysUserServiceExt;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by laiguoqiang on 15/5/13.
 */
@Controller
@RequestMapping(value = "/doc")
public class DocmentController {

    private static SessionStatus sessionStatus = SessionStatus.getInstance();

    private static final Logger logger = Logger.getLogger(DocmentController.class);

    @Autowired
    private ISysUserServiceExt sysUserService;

    @Autowired
    private IPartyUserServiceExt partyUserService;

    @Autowired
    private IDocumentServiceExt documentService;

    @Autowired
    private IPartyServiceExt partyService;

    @RequestMapping(value = "/upload/avatar")
    @ResponseBody
    public String upload(@RequestParam(value = "file",required = true) MultipartFile file,HttpServletRequest request,HttpSession session) {
        JsonResult result = new JsonResult();
        SessionUser user = (SessionUser) session.getAttribute(Constants.SESSION_USER);

        try {
            //上传头像
            String filePath = documentService.doAvatarSave(request, file, 1);

            result.setData(filePath);
            result.setStatus(0);
        }catch(Exception e) {
            result.setStatus(1);
            result.setMessage(e.getMessage());
        }
        String str = JSONUtil.object2JsonString(result);

        return str;
    }

}
