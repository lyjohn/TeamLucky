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
    <h1>测试登录</h1>
    <form action="" onsubmit="return false;">
      <div class="form-group">
        <label for="partyName">活动名称</label>
        <input type="text" class="form-control" name="loginName" id="partyName" placeholder="活动名称">
      </div>
      <div class="form-group">
        <label for="partyRemark">活动简介</label>
        <textarea class="form-control" rows="3" id="partyRemark" placeholder="活动简介"></textarea>
      </div>
      <div class="form-group">
        <label for="partyCode">识别码</label>
        <input type="text" class="form-control" name="loginName" id="partyCode" placeholder="活动识别码，活动用户的用户名前缀">
      </div>
      <div class="form-group">
        <label for="partyCode">封面</label>
        <input type="text" class="form-control" name="loginName" id="partyCover" placeholder="文件路径...">
      </div>
      <div class="checkbox">
        <label>
          <input type="checkbox" id="public"> 公共活动
        </label>
      </div>
      <div class="checkbox">
        <label>
          <input type="checkbox" id="group" checked> 是否分组
        </label>
      </div>
      <div class="checkbox for-group">
        <label>
          <input type="checkbox" id="autoBuild"> 允许自行组队
        </label>
      </div>
      <div class="form-group for-group">
        <label for="memberMin">每组最少人数</label>
        <input type="number" class="form-control" name="memberMin" min="0" id="memberMin" placeholder="每组最少人数">
      </div>
      <div class="form-group for-group">
        <label for="memberMax">每组最多人数</label>
        <input type="number" class="form-control" name="memberMax" min="0" id="memberMax" placeholder="每组最多人数">
      </div>
      <div class="form-group for-group">
        <label for="endTime">分组截止日期</label>
        <input type="date" class="form-control" name="loginName" dataformatas="yyyy-mm-dd" id="endTime" placeholder="分组截止日期">
      </div>

      <button id="js-create" type="submit" class="btn btn-info">创建</button>
    </form>

  </div>

</div>
<!-- /container -->


<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript">
  $(function () {
    $(document).on("click","#js-create",function(){
      var pName = $("#partyName").val();
      var pRamark = $("#partyRemark").val();
      var pCode = $("#partyCode").val();
      var pIsGroup = $("#group").prop("checked");
      var pIsPublic = $("#public").prop("checked");
      var pIsAuto = $("#autoBuild").prop("checked");
      var pMemberMin = $("#memberMin").val();
      var pMemberMax = $("#memberMax").val();
      var pEndTime = $("#endTime").val();
      var postData = {partyName:pName,partyRemark:pRamark,partyCode:pCode,isGroup:pIsGroup,isPublic:pIsPublic,isCustomBuild:pIsAuto,memberNumMin:pMemberMin,memberNumMax:pMemberMax};

      if(pEndTime != "")
        postData.buildEndTime = new Date(pEndTime);;

      $.ajax({
        url:"${ctx}/party/doCreate",
        data:postData,
        type:"Post",
        dataType: "json",
        success: function(data, textStatus, jqXHR){
          if(data.status == 0){
            alert("创建成功");
            window.location.href = "${ctx}/party/setting";
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