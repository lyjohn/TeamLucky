<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>

<div class="navbar-header">
    <a class="navbar-brand" href="${ctx}/">校缘派</a>
</div>
<div id="navbar" class="navbar-collapse collapse">
    <ul class="nav navbar-nav navbar-right">
        <li><a href="../navbar/">用户名称</a></li>
        <li><a href="${ctx}/party/create">创建活动</a></li>
        <li class="active"><a href="./">退出</a></li>
    </ul>
</div>
<!--/.nav-collapse -->