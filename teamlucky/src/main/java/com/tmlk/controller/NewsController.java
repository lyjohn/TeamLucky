package com.tmlk.controller;

/**
 * Created by LaiGuoqiang on 2015/5/14.
 */





import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.session.SessionUser;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.JsonResult;
import com.tmlk.framework.util.Pagination;
import com.tmlk.po.NewsExt;
import com.tmlk.service.INewsServiceExt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/news")
public class NewsController {

    private static final Logger logger = Logger.getLogger(NewsController.class);

    @Autowired
    private INewsServiceExt newsService;

    @RequestMapping(value = "/create")
    public String goCreate(){
        return "/news/createnews";
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doCreate(@ModelAttribute NewsExt newsExt, HttpSession session, HttpServletRequest request){
        JsonResult result = new JsonResult();
        try{

            SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);
            newsExt.setCreateBy(sessionUser.getSysUserId());
            newsExt.setCreateTime(new Date());
            newsExt.setIsPublic(true);
            newsExt.setGroupId(0L);
            newsExt.setPartyId(1L);
            newsExt.setReadCount(0);
            newsService.launch(newsExt);

            result.setStatus(0);
            result.setData(newsExt);
        }catch (Exception ex){
            result.setMessage("服务器异常，请重新提交");
            logger.error(ex);
        }
        return result;
    }




    @RequestMapping(value= "/show")
    @ResponseBody
    public JsonResult goShow(HttpSession session){

        SessionUser sessionUser = (SessionUser)session.getAttribute(Constants.SESSION_USER);

//        Long partyId = sessionUser.getPartyId();
//
//        List<ICondition> conditions = new ArrayList<ICondition>();
//        conditions.add(new EqCondition("partyId",partyId));
//
//        List<Order> orders = new ArrayList<Order>();
//        orders.add(Order.desc("createTime"));
//
//        Pagination pp = new Pagination();
//        pp.setCurrentPage(1);
//        pp.setPageSize(5);
//
//        List<NewsExt> newsExts = newsService.criteriaQuery(conditions,orders,pp);

        NewsExt newsExt = newsService.load(1L);

        JsonResult result = new JsonResult();
        result.setData(newsExt);

        return result;
    }

}
