package com.tmlk.controller;

import com.tmlk.aop.SysControllerLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionStatus;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.service.IDocumentServiceExt;
import com.tmlk.service.IPartyServiceExt;
import com.tmlk.service.IPartyUserServiceExt;
import com.tmlk.service.ISysUserServiceExt;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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



}
