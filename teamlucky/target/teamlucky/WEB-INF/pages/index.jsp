<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap-responsive.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/index.css">
    <title>首页 - 校缘派</title>
</head>

<body style="min-height: 2000px;padding-top: 70px;">

<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">校缘派</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="../navbar/">用户名称</a></li>
                <li><a href="${ctx}/party/create">创建活动</a></li>
                <li><a href="${ctx}/news/create">news</a></li>
                <li class="active"><a href="./">退出</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>

<div class="container">
    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>测试登录</h1>

        <form action="" onsubmit="return false;">
            <div class="form-group">
                <label for="loginName">帐号</label>
                <input type="text" class="form-control" name="loginName" id="loginName" placeholder="用户名 或者 邮箱">
            </div>
            <div class="form-group">
                <label for="loginPwd">登录</label>
                <input type="password" class="form-control" name="loginPwd" id="loginPwd" placeholder="密码">
            </div>
            <%--<div class="form-group">--%>
            <%--<label for="exampleInputPassword1">密码</label>--%>
            <%--<input type="password" class="form-control" id="exampleInputPassword1" placeholder="密码">--%>
            <%--</div>--%>
            <div class="checkbox">
                <label>
                    <input type="checkbox"> 记住我
                </label>
            </div>
            <button id="js-login" type="submit" class="btn btn-success">提交</button>
            <button id="js-register" type="submit" class="btn btn-info">注册</button>
        </form>

    </div>


    <div class="span12 top-head-line2">热门的公共活动</div>
    <div class="span12 top-head-line4">欢迎您的参与</div>

    <div class="row-fluid">
        <div class="span12">
            <div class="carousel slide  media-carousel" id="media1">
                <div class="carousel-inner">
                    <c:if test="${empty model.items}">
                        <div class="alert alert-warning">怎么可能没有数据，这种情况是一定不能有的！</div>
                    </c:if>
                    <c:if test="${!empty model.items}">
                        <c:forEach items="${ model.items }" var="var" varStatus="status">
                            <c:if test="${status.index%4 == 0}">
                                <c:if test="${status.index==0}">
                    <div class="item active">
                        <div class="row">
                                </c:if>
                                <c:if test="${status.index!=0}">
                        </div>
                    </div>
                    <div class="item">
                        <div class="row">
                                </c:if>
                            </c:if>
                            <div class="span3">
                                <div class="list_div">
                                    <div class="list_header">
                                        <div class="head_img">
                                            <a target="_blank"><img src="http://ycpai.b0.upaiyun.com/index/a.jpg"></a>
                                        </div>
                                        <div class="head_tit">
                                            <font class="head_tit_big"><a target="_blank" data-id="<c:out value='${var.id}'></c:out>"><c:out value="${ var.partyName }" escapeXml="true"></c:out></a> </font>
                                            <font class="head_tit_small">活动类型</font>
                                        </div>
                                    </div>
                                    <div class="list_middle"><a target="_blank"><c:out value="${ var.partyRemark }" escapeXml="true"></c:out></a></div>
                                    <div class="list_foot">
                                        <hr>
                                        <div>
                                            <img class="img-circle" src="https://www.baidu.com/img/baidu_jgylogo3.gif?v=31680756.gif" style=" width: 50px;">
                                            <span style="margin-left: 20px;font-size: 16px;color: #767676;">创建人</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        </div>
                    </div>
                    </c:if>
                </div>
                <c:if test="${fn:length(model.items) >4 }">
                        <a data-slide="prev" href="#media1" class="carousel-control left">‹</a>
                        <a data-slide="next" href="#media1" class="carousel-control right">›</a>
                </c:if>
            </div>
        </div>
    </div>

    <div class="span12 top-head-line2">您参与的活动</div>
    <div class="span12 top-head-line4">期待您的支持</div>

    <div class="row">
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/100%x200" alt="100%x200"
                     src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI0MiAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjkyLjQ2MDkzNzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMXB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjI0MngyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
                     data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">

                <div class="caption">
                    <h3>Thumbnail label<a class="anchorjs-link" href="#thumbnail-label"><span
                            class="anchorjs-icon"></span></a></h3>

                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta
                        gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>

                    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#"
                                                                                       class="btn btn-default"
                                                                                       role="button">Button</a></p>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/100%x200" alt="100%x200"
                     src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI0MiAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjkyLjQ2MDkzNzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMXB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjI0MngyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
                     data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">

                <div class="caption">
                    <h3>Thumbnail label<a class="anchorjs-link" href="#thumbnail-label"><span
                            class="anchorjs-icon"></span></a></h3>

                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta
                        gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>

                    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#"
                                                                                       class="btn btn-default"
                                                                                       role="button">Button</a></p>
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/100%x200" alt="100%x200"
                     src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI0MiAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjkyLjQ2MDkzNzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMXB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjI0MngyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
                     data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">

                <div class="caption">
                    <h3>Thumbnail label<a class="anchorjs-link" href="#thumbnail-label"><span
                            class="anchorjs-icon"></span></a></h3>

                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta
                        gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>

                    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#"
                                                                                       class="btn btn-default"
                                                                                       role="button">Button</a></p>
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/100%x200" alt="100%x200"
                     src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI0MiAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjQyIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjkyLjQ2MDkzNzUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMXB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjI0MngyMDA8L3RleHQ+PC9nPjwvc3ZnPg=="
                     data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">

                <div class="caption">
                    <h3>Thumbnail label<a class="anchorjs-link" href="#thumbnail-label"><span
                            class="anchorjs-icon"></span></a></h3>

                    <p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta
                        gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>

                    <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#"
                                                                                       class="btn btn-default"
                                                                                       role="button">Button</a></p>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(function () {
        $(document).on("click", "#js-register", function () {
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();

            if (loginName == "" || loginPwd == "") {
                alert("用户名或密码不能为空")
                return false;
            }
            if (loginName.indexOf("_") > 0) {
                alert("帐号不能包含下划线_")
                return false;
            }
            $.post("${ctx}/register", {loginName: loginName, loginPwd: loginPwd}, function (res) {
                if (res.status == 0) {
                    alert("注册成功");
                    window.location.href = "${ctx}/user/index";
                } else {
                    alert(res.message);
                }
            }, "json");
        }).on("click", "#js-login", function () {
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();
            if (loginName == "" || loginPwd == "") {
                alert("用户名或密码不能为空")
                return false;
            }
            $.post("${ctx}/login", {loginName: loginName, loginPwd: loginPwd}, function (res) {
                if (res.status == 0) {
                    alert("登录成功");
                    if (res.data == "1")
                        window.location.href = "${ctx}/user/index";
                    else
                        alert("活动用户直接进入活动");
                } else {
                    alert(res.message);
                }
            }, "json");
        }).on("blur", "#loginName", function () {
            var loginName = $(this).val();
            if (loginName == "") {
                alert("用户名不能为空")
                $(this).focus();
                return false;
            }
            $.post("${ctx}/checkLoginName", {loginName: loginName}, function (res) {
                if (res.status != 0) {
                    alert(res.message);
                    $(this).focus();
                }
            }, "json");
        }).on("click",".head_tit a",function(){
            var pid = $(this).data("id");

            window.location.href = "${ctx}/party/goView/"+pid;
        })

        $('.carousel').carousel({
            interval: 5000
        })
    })

</script>

</body>
</html>