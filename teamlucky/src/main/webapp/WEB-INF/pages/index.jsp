<%@ page import="com.tmlk.framework.session.SessionUser" %>
<%@ page import="com.tmlk.framework.util.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>

<%
    SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(
            Constants.SESSION_USER);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/blue.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/fwslider.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/allinone_carousel.css" >

    <title>首页 - 校缘派</title>
</head>

<body>
<div class="header_bg">
    <div class="container">
        <div class="header">
            <div class="logo">
                <a href="${ctx}"><img src="${ctx}/resource/images/logo.png" alt=""/></a>
            </div>
            <jsp:include page="shared/_header.jsp">
                <jsp:param value="0" name="type"/>
                <jsp:param value="null" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div id="fwslider"><!-- start slider -->
    <div class="slider_container">
        <div class="slide">
            <!-- Slide image -->
            <img src="${ctx}/resource/images/slider1.jpg">
            <!-- /Slide image -->
        </div>
        <!-- /Duplicate to create more slides -->
        <div class="slide">
            <img src="${ctx}/resource/images/slider2.jpg">
            <!--/slide -->
        </div>
        <% if(sessionUser == null ) { %>
        <div class="slide_content">
            <div class="slide_content_wrap">
                <div class="col-md-4">
                    <div class="contact-form">
                        <h4 class="title">校缘派 专注分组</h4>
                        <form action="" onsubmit="return false;" class="description">
                            <input type="text" id="loginName" value="" />
                            <input type="password" id="loginPwd" value="" />

						     <span><input id="js-login" type="submit" value="登录">&nbsp;&nbsp;<input id="js-register" type="submit" value="注册"></span>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        <div class="timers"></div>
        <div class="slidePrev"><span></span></div>
        <div class="slideNext"><span></span></div>
    </div><!--/slider -->
</div>
<div class="main_bg"><!-- start main -->
    <div class="container">
        <div class="main_grid">
            <div class="span_of_4">
                <c:if test="${!empty model.items}">
                    <c:forEach items="${ model.items }" var="var" varStatus="status">
                        <div class="col-md-3 span1_of_4">
                            <div class="span4_of_list">
                                <span><i class="fa fa-thumbs-o-up"></i></span>
                                <h3><c:out value="${ var.partyName }" escapeXml="true"></c:out></h3>
                                <h4></h4>
                                <p><c:out value="${ var.partyRemark }" escapeXml="true"></c:out></p>
                                <div class="read_more">
                                    <a class="btn btn-2 active" href="${ctx}/party/index/${var.id}">立即查看</a>
                                </div>
                            </div>
                        </div>
                       </c:forEach>
                </c:if>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<div class="footer1_bg">
    <div class="container">
        <div class="footer1">
            <div class="copy pull-left">
                <p class="link">Copyright &copy; 2014.Company 校缘派 All rights reserved.<a target="_blank" href="#"></a></p>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/css3-mediaqueries.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/fwslider.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/menu.js" ></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>

<script type="text/javascript" src="${ctx}/resource/plugins/layer/layer.js"></script>

<script type="text/javascript">
    $(function () {
        $('#allinone_carousel_charming').allinone_carousel({
            skin: 'charming',
            width: 990,
            height: 454,
            responsive:true,
            autoPlay: 3,
            resizeImages:true,
            autoHideBottomNav:false,
            showElementTitle:false,
            verticalAdjustment:50,
            showPreviewThumbs:false,
            //easing:'easeOutBounce',
            numberOfVisibleItems:5,
            nextPrevMarginTop:23,
            playMovieMarginTop:0,
            bottomNavMarginBottom:-10
        });
        $(document).on("click", "#js-register", function () {
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();

            if (loginName == "" || loginPwd == "") {
                layer.msg("用户名或密码不能为空",{icon: 5});
                return false;
            }
            if (loginName.indexOf("_") > 0) {
                layer.msg("注册时，用户名不能包含下划线",{icon: 5});
                return false;
            }
            $.post("${ctx}/register", {loginName: loginName, loginPwd: loginPwd}, function (res) {
                if (res.status == 0) {
                    layer.msg('恭喜，注册成功，马上去填写个人信息吧~', function(){
                        //关闭后的操作
                        window.location.href = "${ctx}/user/sprofile";
                    });

                } else {
                    layer.msg("注册失败，请重试~",{icon: 5});
                }
            }, "json");
        }).on("click", "#js-login", function () {
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();
            if (loginName == "" || loginPwd == "") {
                layer.msg("用户名或密码不能为空",{icon: 5});
                return false;
            }
            $.post("${ctx}/login", {loginName: loginName, loginPwd: loginPwd}, function (res) {
                if (res.status == 0) {
                    $(".slide_content").remove();
                    layer.msg("欢迎回来，我的朋友~", {icon: 6});

                    if (res.data == "1"){
                        $(".js_userset,.js_partycreate,.js_logout").removeClass("hide");
                        $(".js_userset a").attr("href","${ctx}/user/sprofile");
                        $(".js_partycreate a").attr("href","${ctx}/party/create");
                    }else
                    {
                        $(".js_userset").removeClass("hide")
                        $(".js_userset a").attr("href","${ctx}/user/pprofile");
                    }
                } else {
                    layer.msg(res.message, {icon: 5});
                }
            }, "json");
        }).on("blur", "#loginName", function () {
            var loginName = $(this).val();
            if (loginName == "") {
                layer.msg("姓名不能为空", {icon: 0});
                $(this).focus();
                return false;
            }
            $.post("${ctx}/checkLoginName", {loginName: loginName}, function (res) {
                if (res.status != 0) {
                    console.log(res.message);
                    $(this).focus();
                }
            }, "json");
        })
    })

</script>

</body>
</html>