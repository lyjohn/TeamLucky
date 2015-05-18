<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css">
    <title>测试 - 校缘派</title>
</head>
<body style="padding-top: 70px">
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <jsp:include page="/WEB-INF/pages/shared/_header.jsp">
            <jsp:param value="1" name="admin"/>
        </jsp:include>
    </div>
</nav>

<div class="container" id="main-container">
    <div class="main-container" id="inner-container">
        <div class="main-container-inner">
            <div class="form-toolbar-container">
                <div class="btn-tools-large">
                    <button class="btn btn-success galaxy-back" onclick="javascript:history.go(-1)">
                        <i class="icon-reply smaller-80"></i> 返回
                    </button>
                </div>
            </div>
            <div class="page-content">
                <div class="widget-box">

                    <div class="widget-body">
                        <div class="widget-main">
                            <div class="row">
                                <div class="col-xs-12">
                                    <form class="form-horizontal" id="sysUserForm" action="" method="post">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label no-padding-right"
                                                   for="name-field">用户名:</label>

                                            <div class="col-xs-9">
                                                <span class="galaxy-form-value"><c:out value="${model.user.name}"
                                                                                       escapeXml="true"></c:out></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label no-padding-right"
                                                   for="age-field">姓名:</label>

                                            <div class="col-xs-9">
                                                <span class="galaxy-form-value"><c:out value="${model.user.age}"
                                                                                       escapeXml="true"></c:out></span>
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

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(function () {

    });
</script>
</body>
</html>