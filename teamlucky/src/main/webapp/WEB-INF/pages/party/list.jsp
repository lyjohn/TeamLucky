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

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/codecademy.css" />

    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/blue.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/fwslider.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/allinone_carousel.css" />

    <link rel="icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico" type="image/x-icon">

    <title>公共活动列表 - 校缘派</title>
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
                <jsp:param value="null" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="main_bg">
    <div class="container">

        <div class="grid-row">
            <div class="grid-col-12 grid-col--align-center grid-col--no-margin dynamic-container margin-top--1">
                <div class="grid-col-12">
                    <h2>公共活动</h2>
                    <p>可以自由查看活动，并参与其中.</p>
                </div>
            </div>
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
                                    <span><c:out value="${var.createTimeString}"></c:out></span>
                                </div>
                            </div>
                            <a href="${ctx}/party/index/${var.id}" class="link--target"></a>
                        </div>
                    </div>

                    <div id="before-container" class="grid-col-12 grid-col--align-center">
                        <div class="grid-col-12 grid-col--center grid-col--no-margin grid-col--no-padding">
                            <c:if test="${ fn:length(model.items) == model.pp.pageSize}">
                                <div class="js-show-more show-more">
                                    <div><small><a href="#" class="cta">查看更多</a></small></div>
                                    <div><span class="new-cc-icon icon-downarrow icon--"></span></div>
                                </div>
                            </c:if>
                        </div>
                    </div>


                    <form:input type="hidden" name="model.pp.currentPage" path="model.pp.currentPage" />
                    <form:input type="hidden" name="model.pp.pageSize" path="model.pp.pageSize" />

                </c:forEach>
            </c:if>
        </div>
    </div>
</div>

<div id="content"></div>

<jsp:include page="../shared/_footer.jsp"/>

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/layer/layer.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/css3-mediaqueries.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/fwslider.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.validate.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>


<script type="text/javascript" src="${ctx}/resource/js/template-native.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script id="partylist" type="text/html">
    <div class="grid-col-3 grid-col--no--padding grid-col--no-margin">
        <div class="card card--link color-scheme--white">
            ${'<'}% for(var i = 0; i < list.length; i++) { %${'>'}
            <div class="grid-row card__info">
                <span exclude="phone">
                    <div class="avatar avatar--large">
                        <img alt="${'<'}%= list[i].partyName %${'>'}" src="${ctx}/avatar/party/${'<'}%= list[i].id %${'>'}">
                    </div>
                </span>
                <div exclude="desktop tablet" class="grid-col-4">
                    <div class="avatar avatar--medium">
                        <img alt="${'<'}%= list[i].partyName %${'>'}" src="${ctx}/avatar/party/${'<'}%= list[i].id %${'>'}">
                    </div>
                </div>
                <div exclude="desktop tablet" class="grid-col-4">
                    <div class="card__headings">
                        <h5 class="text--ellipsis">${'<'}%= list[i].partyName %${'>'}</h5>
                    </div>
                </div>
                <div exclude="phone" class="card__headings">
                    <h5>${'<'}%= list[i].partyName %${'>'}</h5>
                </div>
                <div class="grid-row card__text">
                    <div class="grid-col-12">
                        ${'<'}%= list[i].partyRemark %${'>'}
                    </div>
                </div>
                <hr>
                <div class="grid-row  card__author grid-col--align-left grid-col--no-margin">
                    <img class="img-circle" src="${ctx}/avatar/user/1/${'<'}%= list[i].partyAuthor.id%${'>'}" />
                    <span>${'<'}%= list[i].partyAuthor.userName %${'>'}</span>
                    <span>${'<'}%= list[i].createTimeString %${'>'}</span>
                </div>
            </div>
            <a href="${ctx}/party/index/${'<'}%= list[i].id%${'>'}" class="link--target"></a>
            ${'<'}% } %${'>'}
        </div>
    </div>
</script>

<script type="text/javascript">
    var layerLoadIndex=0;
    $(function () {
        $(document).on("click",".js-show-more a",function() {
            var currentPage = $("input[name='pp.currentPage']").val();
            var pageSize =$("input[name='pp.pageSize']").val();

            $.ajax({
                url:"${ctx}/party/list/next",
                dataType:"json",
                data:{currentPage:currentPage,pageSize:pageSize},
                type:"post",
                success:function(result){
                    if(result.status==0){
                        var datas = result.data;
                        if(datas.length == 0){
                            layer.msg("没有更多公共活动了...",{offset: '110px'});
                            $(".js-show-more").fadeOut();
                        }
                        else
                        {
                            var data = {list:datas};
                            var html = template('partylist',data);

                            $("#before-container").before(html);

                            if(datas.length < pageSize)
                                $(".js-show-more").fadeOut();
                            else
                                $("input[name='pp.currentPage']").val(currentPage+1);
                        }
                    }else
                        layer.msg(result.message,{icon:5,offset:'110px'});
                },
                complete: function (XHR, TS) {
                    layer.close(layerLoadIndex);
                },
                beforeSend: function (XHR) {
                    layerLoadIndex = layer.load(2);
                }
            });
            return false;
        }).on("click",".link--target",function(){
            <% if (sessionUser != null) {%>
            return true;
            <% } else {%>
            layer.msg("您需要先登录", { offset: '110px'});
            return false;
            <% } %>
        })

    });
</script>

</body>
</html>