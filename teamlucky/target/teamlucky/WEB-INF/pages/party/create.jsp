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

  <title>发起活动 - 校缘派</title>
</head>

<body>
<div class="header_bg">
  <div class="container">
    <div class="header">
      <div class="logo">
        <a href="${ctx}"><img src="${ctx}/resource/images/logo.png" alt=""/></a>
      </div>
      <jsp:include page="../shared/_header.jsp">
        <jsp:param value="0" name="type"/>
        <jsp:param value="partycreate" name="cur"/>
      </jsp:include>
      <div class="clearfix"></div>
    </div>
  </div>
</div>

<div class="container">

  <!-- Main component for a primary marketing message or call to action -->
  <div class="jumbotron">
    <h2>创建活动</h2>
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

<jsp:include page="../shared/_footer.jsp" />

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