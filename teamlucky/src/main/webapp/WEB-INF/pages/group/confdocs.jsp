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

  <title>创建团队 - 校缘派</title>
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
        <jsp:param value="groupcreate" name="cur"/>
      </jsp:include>
      <div class="clearfix"></div>
    </div>
  </div>
</div>

<jsp:include page="../shared/_footer.jsp" />

</body>
</html>
