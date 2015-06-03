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
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);
    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/codecademy.css">

    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/blue.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/fwslider.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/allinone_carousel.css">

    <link rel="icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico" type="image/x-icon">

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
        <% if (sessionUser == null) { %>
        <div class="slide_content">
            <div class="fit-fixed slide_content_wrap">
                <div class="grid-row padding-top--3 grid-row-no-collapse">
                    <div class="grid-col-8 grid-col--no-padding screen-col">

                    </div>
                    <div class="grid-col-4" id="home__cta">
                        <div class="grid-row grid-row--no-collapse">
                            <div class="full-tabs">
                                <div class="grid-row grid-row--no-collapse tabs tabs-color-scheme--white">
                                    <div class="grid-col--align-center tab-group tab-group--2 grid-col-12 grid-col--center grid-col--no-padding grid-col--no-margin">
                                        <a href="#" class="tab padding--1" data-state="inactive" data-type="sign-up-form" id="sign-up-form">注 册</a>
                                        <a href="#" class="tab padding--1 active" data-data="null" data-state="active" data-type="sign-in-form" id="sign-in-form">登 录</a>
                                    </div>
                                </div>
                                <div class="grid-row color-scheme--white">
                                    <div class="sign-up-form-body tab-body" data-state-body="inactive">
                                        <div class="grid-row color-scheme--white">
                                            <div class="grid-col-12">
                                                <form accept-charset="UTF-8" action="" onsubmit="return false;" autocomplete="off" class="new_user" id="registerForm" method="post">
                                                    <div class="grid-row margin-bottom--1 error-container" style="display:none">
                                                        <div class="grid-col-12 grid-col--align-left grid-col--center grid-col--no-padding grid-col--no-margin">
                                                            <small style="color:#f65a5b"></small>
                                                        </div>
                                                    </div>
                                                    <div class="field field--text">
                                                        <input autocomplete="off" id="reg_username" name="reg_username" placeholder="用户名" required="required" size="30" tabindex="1" type="text" class="ui-inited">
                                                        <div class="field__status-icon"></div>
                                                    </div>

                                                    <div class="field field--text">
                                                        <input autocomplete="off" id="reg_password" name="reg_password" placeholder="密码" required="required" size="30" tabindex="2" type="password" class="ui-inited">
                                                        <div class="field__status-icon"></div>
                                                    </div>

                                                    <div class="field field--text ">
                                                        <input autocomplete="off" id="reg_repassword" name="reg_repassword" placeholder="确认密码" required="required" size="30" tabindex="3" type="password" class="ui-inited">
                                                        <div class="field__status-icon"></div>
                                                    </div>
                                                    <input class="button button--fill-space button--large  ui-inited" id="js-register" name="commit" type="submit" value="提交注册"/>

                                                    <div class="grid-row margin-top--1">
                                                        <div class="grid-col-12 grid-col--align-center grid-col--center grid-col--no-padding grid-col--no-margin">
                                                            <small>
                                                                您的注册，表示您已同意 <a href="/terms" class="undo" style="color:#76C7C0">服务条款</a>
                                                            </small>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="sign-in-form-body tab-body" data-state-body="active">
                                        <div class="grid-row color-scheme--white">
                                            <div class="grid-col-12">
                                                <form accept-charset="UTF-8" action="" onsubmit="return false;" class="new_user" id="loginForm" method="post">
                                                    <div class="grid-row margin-bottom--1 error-container" style="display:none">
                                                        <div class="grid-col-12 grid-col--align-left grid-col--center grid-col--no-padding grid-col--no-margin">
                                                            <small style="color:#f65a5b"></small>
                                                        </div>
                                                    </div>
                                                    <div class="field field--text">
                                                        <input id="login_username" name="login_username" placeholder="用户名" required="required" size="30" tabindex="1" type="text" class="ui-inited">
                                                        <div class="field__status-icon"></div>
                                                    </div>
                                                    <div class="field field--text ">
                                                        <input id="login_password" name="login_password" placeholder="密码" required="required" size="30" tabindex="2" type="password" class="ui-inited">
                                                        <div class="field__status-icon"></div>
                                                    </div>
                                                    <input class="button button--fill-space button--large  ui-inited" id="js-login" name="commit" type="submit" value="登录系统">

                                                    <div class="grid-row margin-top--1">
                                                        <div class="grid-col-12 grid-col--no-padding grid-col--extra-margin-bottom grid-col--align-center grid-col--center">
                                                            <small>
                                                                <a href="/secret/new" class="secondary undo">忘记密码</a>
                                                            </small>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
        <div class="timers"></div>
        <div class="slidePrev"><span></span></div>
        <div class="slideNext"><span></span></div>
    </div>
</div>
<div class="main_bg">
    <div class="container">

        <div class="grid-row">
            <c:if test="${!empty model.items}">
            <c:forEach items="${ model.items }" var="var" varStatus="status">
            <div class="grid-col-3 grid-col--no--padding grid-col--no-margin">
                <div class="card card--link color-scheme--white">

                    <div class="grid-row card__info">
                        <span exclude="phone">
                            <div class="avatar avatar--large">
                                <img alt="<c:out value="${ var.partyName }" escapeXml="true"></c:out>" src="${ctx}/avatar/party/<c:out value='${var.id}'></c:out>">
                            </div>
                        </span>
                        <div exclude="desktop tablet" class="grid-col-4">
                            <div class="avatar avatar--medium">
                                <img alt="<c:out value="${ var.partyName }" escapeXml="true"></c:out>" src="${ctx}/avatar/party/<c:out value='${var.id}'></c:out>">
                            </div>
                        </div>
                        <div exclude="desktop tablet" class="grid-col-4">
                            <div class="card__headings">
                                <h5 class="text--ellipsis"><c:out value="${ var.partyName }" escapeXml="true"></c:out></h5>
                            </div>
                        </div>
                        <div exclude="phone" class="card__headings">
                            <h5><c:out value="${ var.partyName }" escapeXml="true"></c:out></h5>
                        </div>
                        <div class="grid-row card__text">
                            <div class="grid-col-12">
                                <c:out value="${ var.partyRemark }" escapeXml="true"></c:out>
                            </div>
                        </div>
                        <hr>
                        <div class="grid-row  card__author grid-col--align-left grid-col--no-margin">
                            <img class="img-circle" src="${ctx}/avatar/user/1/<c:out value='${var.partyAuthor.id}'></c:out>" />
                            <span><c:out value="${var.partyAuthor.userName}"></c:out></span>
                            <span class="time_create"><c:out value="${var.createTimeString}"></c:out></span>
                        </div>
                    </div>
                    <a href="${ctx}/party/index/${var.id}" class="link--target"></a>

                </div>
            </div>

            </c:forEach>
            </c:if>
        </div>

        <c:if test="${!empty model.items}">
        <div class="grid-row">
            <div class="grid-col--center grid-col--align-center grid-col--no--padding">
                <a href="${ctx}/party/list">更多公共活动</a>
            </div>
        </div>
        </c:if>
        <hr>
        <div class="grid-row learners padding-bottom--1">
            <div class="grid-col-12 grid-col--center grid-col--align-center grid-col--no--padding">
                <h2>2
                    <small>公共活动</small>
                    10
                    <small>私有活动</small>
                </h2>
                <div class="grid-col-12 grid-col--no-spacing">火热进行中</div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="shared/_footer.jsp"/>

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/css3-mediaqueries.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/fwslider.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.validate.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>


<script type="text/javascript">
    var logined = false;
    $(function () {
        $('#allinone_carousel_charming').allinone_carousel({
            skin: 'charming',
            width: 990,
            height: 454,
            responsive: true,
            autoPlay: 3,
            resizeImages: true,
            autoHideBottomNav: false,
            showElementTitle: false,
            verticalAdjustment: 50,
            showPreviewThumbs: false,
            //easing:'easeOutBounce',
            numberOfVisibleItems: 5,
            nextPrevMarginTop: 23,
            playMovieMarginTop: 0,
            bottomNavMarginBottom: -10
        });

        // 注册时 用户名不能有下划线
        jQuery.validator.addMethod("underline", function(value, element) {
            return this.optional(element) || !(value.indexOf("_")>-1);
        }, "注册时,用户名不能有下划线");

        var validatorRegister = $('#registerForm').validate({
            errorLabelContainer: "#registerForm .error-container small",
            focusInvalid: false,
            focusCleanup: true, //移进来，把错误消掉
            onsubmit: false,
            onfocusout: function (element) { $(element).valid();},
            rules: {
                "reg_username": {
                    required: true,
                    underline:true,
                    remote:{
                        url :"${ctx}/checkLoginName",
                        type:'post',
                        data:{
                            loginName : function(){
                                return $("input[name='reg_username']").val();
                            }
                        }
                    }
                },
                "reg_password":{
                    required: true,
                    minlength: 6
                },
                "reg_repassword":{
                    required: true,
                    minlength: 6,
                    equalTo: "#reg_password"
                }
            },
            messages: {
                "reg_username": {
                    required: "用户名不能为空",
                    remote: "该用户名已注册"
                },
                "reg_password":{
                    required: "密码不能为空",
                    minlength: "密码不能小于6位"
                },
                "reg_repassword":{
                    required: "请重复输入新密码",
                    minlength: "密码不能小于6位",
                    equalTo: "两次输入密码不一致"
                }
            },
            highlight: function (element) {
                $("#registerForm .error-container").fadeIn();
            },
            success: function (label) {
                $("#registerForm .error-container").fadeOut();
                label.remove();
            }
        });

        var validatorLogin = $('#loginForm').validate({
            errorLabelContainer: "#loginForm .error-container small",
            focusInvalid: false,
            focusCleanup: true, //移进来，把错误消掉
            onsubmit: false,
            onfocusout: function (element) { $(element).valid();},
            rules: {
                "login_username": {
                    required: true
                },
                "login_password":{
                    required: true,
                    minlength: 6
                }
            },
            messages: {
                "login_username": {
                    required: "用户名不能为空"
                },
                "login_password":{
                    required: "密码不能为空",
                    minlength: "密码不能小于6位"
                }
            },
            highlight: function (element) {
                $("#loginForm .error-container").fadeIn();
            },
            success: function (label) {
                $("#loginForm .error-container").fadeOut();
                label.remove();
            }
        });

        $(document).on("click", "#js-register", function () {

            var isSuc = validatorRegister.form();
            if (!isSuc) {
                layer.msg("有错误项，还不能注册", {icon: 5, offset: '110px'});
                return false;
            }

            var loginName = $("#reg_username").val();
            var loginPwd = $("#reg_password").val();

            if (loginName == "" || loginPwd == "") {
                layer.msg("用户名或密码不能为空", {icon: 5, offset: '110px'});
                return false;
            }
            if (loginName.indexOf("_") > 0) {
                layer.msg("注册时，用户名不能包含下划线", {icon: 5, offset: '110px'});
                return false;
            }
            $.post("${ctx}/register", {loginName: loginName, loginPwd: loginPwd}, function (res) {
                if (res.status == 0) {
                    layer.msg('恭喜，注册成功，马上去填写个人信息吧~', {offset: '110px'}, function () {
                        //关闭后的操作
                        window.location.href = "${ctx}/user/sprofile";
                    });

                } else {
                    layer.msg("注册失败，请重试~", {icon: 5, offset: '110px'});
                }
            }, "json");
        }).on("click", "#js-login", function () {
            var isSuc = validatorLogin.form();
            if (!isSuc) {
                layer.msg("有错误项，还不能登录", {icon: 5, offset: '110px'});
                return false;
            }

            var loginName = $("#login_username").val();
            var loginPwd = $("#login_password").val();

            $.post("${ctx}/login", {loginName: loginName, loginPwd: loginPwd}, function (res) {
                if (res.status == 0) {
                    $(".slide_content").remove();
                    layer.msg("欢迎回来，我的朋友~", {icon: 6, offset: '110px'});
                    logined = true;
                    if (res.data == "1") {
                        $(".js_userset,.js_partycreate,.js_logout").removeClass("hide");
                        $(".js_userset a").attr("href", "${ctx}/user/sprofile");
                        $(".js_partycreate a").attr("href", "${ctx}/party/create");
                    } else {
                        $(".js_userset").removeClass("hide")
                        $(".js_userset a").attr("href", "${ctx}/user/pprofile");
                    }
                } else {
                    layer.msg(res.message, {icon: 5, offset: '110px'});
                }
            }, "json");
        }).on("click",".tab-group > a",function() {
            var $this = $(this);
            var $old = $(".tab-group .active");

            var type = $this.data("type");
            var oldtype = $old.data("type");

            $this.attr("data-state","active");
            $this.addClass("active");
            $("."+type+"-body").attr("data-state-body","active");

            $old.attr("data-state","inactive");
            $old.removeClass("active");
            $("."+oldtype+"-body").attr("data-state-body","inactive");

            return false;
        }).on("click",".link--target",function(){
            if(logined)
                return logined;

            <% if (sessionUser != null) {%>
            return true;
            <% } else {%>
            layer.msg("您需要先登录", { offset: '110px'});
            return false;
            <% } %>
        })
    })

</script>

</body>
</html>