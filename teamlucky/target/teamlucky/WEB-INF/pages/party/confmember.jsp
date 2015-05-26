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

  <title>管理成员 - 校缘派</title>
</head>
<body>
<div class="header_bg">
  <div class="container">
    <div class="header">
      <div class="logo">
        <a href="${ctx}"><img src="${ctx}/resource/images/logo.png" alt=""/></a>
      </div>
      <jsp:include page="../shared/_header.jsp">
        <jsp:param value="1" name="type"/>
        <jsp:param value="null" name="cur"/>
      </jsp:include>
      <div class="clearfix"></div>
    </div>
  </div>
</div>

<div class="container">

  <div class="jumbotron">
    <h1>测试编辑</h1>
    <form action="${ctx}/party/doEdit" method="post">
      <div class="form-group">
        <label for="partyName">活动名称</label>
        <c:out value="${model.partyExt.partyName}"></c:out>

        <form:input class="form-control" path="model.partyExt.partyName" place="活动名名称" />
      </div>
      <div class="form-group">
        <label for="partyRemark">活动简介</label>
        <form:textarea cssClass="form-control" path="model.partyExt.partyRemark" placeholder="活动简介" rows="3" />
      </div>
      <div class="form-group">
        <label for="partyCode">识别码</label>
        <form:input class="form-control" path="model.partyExt.partyCode" placeholder="活动识别码，活动用户的用户名前缀" />
      </div>
      <div class="form-group">
        <label for="partyCode">封面</label>
        <input type="text" class="form-control" name="loginName" id="partyCover" placeholder="文件路径...">
      </div>
      <div class="checkbox">
        <label>
          <form:checkbox path="model.partyExt.isPublic"/> 公共活动
        </label>
      </div>
      <div class="checkbox">
        <label>
          <form:checkbox path="model.partyExt.isGroup"/> 是否分组
        </label>
      </div>
      <div class="checkbox for-group">
        <label>
          <form:checkbox path="model.partyExt.isCustomBuild"/> 允许自行组队
        </label>
      </div>
      <div class="form-group for-group">
        <label for="memberMin">每组最少人数</label>
        <form:input type="number" class="form-control" path="model.partyExt.memberNumMin" placeholder="每组最少人数" />
      </div>
      <div class="form-group for-group">
        <label for="memberMax">每组最多人数</label>
        <form:input type="number" class="form-control" path="model.partyExt.memberNumMax" placeholder="每组最少人数" />
      </div>
      <%--<div class="form-group for-group">--%>
        <%--<label for="endTime">分组截止日期</label>--%>
        <%--<form:input type="date" class="form-control" path="model.partyExt.buildEndTime" placeholder="分组截止日期" />--%>
      <%--</div>--%>
      <form:input type="hidden" name="model.partyExt.id" path="model.partyExt.id"/>

      <button id="js-create" type="submit" class="btn btn-info">保存</button>
    </form>

  </div>

</div>

<jsp:include page="../shared/_footer.jsp" />


<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript">
  $(function () {

  })
</script>

</body>
</html>