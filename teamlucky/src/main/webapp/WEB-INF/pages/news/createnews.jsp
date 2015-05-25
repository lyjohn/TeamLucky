<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css">
  <title>创建通知</title>
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
    <h1>测试登录</h1>
    <form action="" onsubmit="return false;">
      <div class="form-group">
        <label for="newsTitle">通知标题</label>
        <input type="text" class="form-control" name="loginName" id="newsTitle" placeholder="通知标题">
      </div>
      <div class="form-group">
        <label for="newsContent">通知内容</label>
        <textarea class="form-control" rows="3" id="newsContent" placeholder="通知内容"></textarea>
      </div>
      <button id="js-create" type="submit" class="btn btn-info">发送通知</button>
    </form>
  </div>
</div>
<!-- /container -->


<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript">
  $(function () {
    $(document).on("click","#js-create",function(){

      var nTitle=$("#newsTitle").val();
      var nContent=$("#newsContent").val();
      var postData={newsTitle:nTitle,newsContent:nContent};

      $.ajax({
        url:"${ctx}/news/doCreate",
        data:postData,
        type:"Post",
        dataType: "json",
        success: function(data, textStatus, jqXHR){
          if(data.status == 0){
            alert("创建成功");
            window.location.href = "${ctx}/news/show";
          }else{
            alert(data.message);
          }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
          alert(errorThrown);
        },
        complete: function(XHR, TS){

        },
        beforeSend: function(XHR) {

        }

      })


      <%--$.post("${ctx}/party/doCreate",{},function(res){--%>

      <%--},"json");--%>
    }).on("click","#group",function(){ //切换是否分组
      if($(this).prop("checked")){
        $(".for-group").slideDown();
      }else {
        $(".for-group input").val("");
        $(".for-group").slideUp();
      }
    });
  })
</script>

</body>
</html>