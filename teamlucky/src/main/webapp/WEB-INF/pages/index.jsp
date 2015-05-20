<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css">
    <title>首页 - 校缘派</title>
</head>

<body style="min-height: 2000px;padding-top: 70px;">

<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li class="dropdown-header">Nav header</li>
                        <li><a href="#">Separated link</a></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="../navbar/">Default</a></li>
                <li><a href="../navbar-static-top/">Static top</a></li>
                <li class="active"><a href="./">Fixed top <span class="sr-only">(current)</span></a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>

<div class="container">

    <!-- Main component for a primary marketing message or call to action -->
    <div class="jumbotron">
        <h1>Navbar example</h1>

        <p>This example is a quick exercise to illustrate how the default, static and fixed to top navbar work. It
            includes the responsive CSS and HTML, so it also adapts to your viewport and device.</p>

        <p>To see the difference between static and fixed top navbars, just scroll.</p>

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

</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(function () {
        $(document).on("click","#js-register",function(){
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();

            if(loginName=="" || loginPwd=="")
            {
                alert("用户名或密码不能为空")
                return false;
            }
            if(loginName.indexOf("_")>0){
                alert("帐号不能包含下划线_")
                return false;
            }
            $.post("${ctx}/register",{loginName: loginName,loginPwd:loginPwd},function(res){
                if(res.status==0){
                    alert("注册成功");
                }else{
                    alert(res.message);
                }
            },"json");
        }).on("click","#js-login",function(){
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();
            if(loginName=="" || loginPwd=="")
            {
                alert("用户名或密码不能为空")
                return false;
            }
            $.post("${ctx}/login",{loginName: loginName,loginPwd:loginPwd},function(res){
                if(res.status==0){
                    alert("登录成功");
                }else{
                    alert(res.message);
                }
            },"json");
        }).on("blur","#loginName",function(){
            var loginName = $(this).val();
            if(loginName=="")
            {
                alert("用户名不能为空")
                $(this).focus();
                return false;
            }
            $.post("${ctx}/checkLoginName",{loginName: loginName},function(res){
                if(res.status!=0){
                    alert(res.message);
                    $(this).focus();
                }
            },"json");
        })
    })
</script>

</body>
</html>