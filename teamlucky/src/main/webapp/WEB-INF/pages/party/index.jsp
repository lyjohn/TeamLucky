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

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/codecademy.css"/>

    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/blue.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/fwslider.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/allinone_carousel.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/user-profile.css"/>

    <link rel="icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico" type="image/x-icon">

    <title>${model.partyExt.partyName} - 校缘派</title>
</head>
<body>
<div class="header_bg">
    <div class="container">
        <div class="header">
            <div class="logo">
                <a href="${ctx}"><img src="${ctx}/resource/images/logo.png" alt=""/></a>
                <span class="gap">·</span><a class="subtitle" href="${ctx}/party/index"><c:out value="${model.partyExt.partyName}"></c:out></a>
            </div>
            <jsp:include page="../shared/_header.jsp">
                <jsp:param value="1" name="type"/>
                <jsp:param value="acthome" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="main_bg body_bg" style="background-color:#fff">
    <div class="persion_section">
        <div class="person_detail_tab party_tab">
            <ul>
                <li data-modal="tab" data-tab="myMembers" data-load=true class="current_detail">活动大厅</li>
                <li data-modal="tab" data-tab="myNews" data-load=false>公告栏</li>
                <li data-modal="tab" data-tab="myDocuments" data-load=false>文档成果</li>
                <li data-modal="tab" data-tab="myForums" data-load=false>论坛峰会</li>
            </ul>
        </div>
        <div class="aboutMe partyIndex">
            <div nodetype="myMembers" nodeindex="my0" data-modal="tab-layer" class="myMembers current_content">
                <ul class="user_list">
                    <c:forEach items="${ model.partyUsers }" var="var" varStatus="status">
                        <c:if test="${var.userStatus==2}">
                            <li class="user_along" data-id="<c:out value='${var.id}'></c:out>" data-group="<c:out value='${var.groupId}'></c:out>">
                        </c:if>
                        <c:if test="${var.userStatus!=2}">
                            <li class="user_ingroup ${var.groupId==groupId ? 'user_insame' :''}" data-id="<c:out value='${var.id}'></c:out>" data-group="<c:out value='${var.groupId}'></c:out>">
                        </c:if>
                        <div class="media">
                            <div class="media-left">
                                <img class="img-circle user-hover" data-hover="<c:out value='${var.id}'></c:out>" src="${ctx}/avatar/user/2/<c:out value='${var.id}'></c:out>">
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading"><c:out value="${var.userName}"></c:out></h4>
                                <div class="media-content">
                                    <c:out value="${var.userRemark}"></c:out>
                                </div>
                            </div>
                            <!-- <c:if test="${var.userStatus== 2 && join}">
                                <div class="media-hover"></div>
                                <div class="media-action">
                                    <a data-id="<c:out value='${var.id}'></c:out>" class="button button_inviteuser" href="javascrit:;">邀 请</a>
                                </div>
                            </c:if> -->
                        </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div nodetype="myNews" nodeindex="my1" data-modal="tab-layer" class="myNews">

            </div>
            <div nodetype="myDocuments" nodeindex="my2" data-modal="tab-layer" class="myDocuments">

            </div>
            <div nodetype="myForums" nodeindex="my3" data-modal="tab-layer" class="myForums">

            </div>
        </div>
    </div>
    <div class="group_section">
        <div class="section_title">
            <i class="fa fa-group"></i>在建团队
        </div>
        <div class="section_content">
            <ul>
                <c:forEach items="${ model.partyGroups }" var="var" varStatus="status">
                    <c:if test="${var.groupStatus==2}">
                        <li class="full ${var.id == groupId ? 'insame' :''}" data-id="${var.id}">
                    </c:if>
                    <c:if test="${var.groupStatus!=2}">
                        <li class="${var.id == groupId ? 'insame' :''}" data-id="${var.id}">
                    </c:if>
                    <div class="media">
                        <div class="media-left">
                            <a href="${ctx}/group/index/<c:out value='${var.id}'></c:out>">
                                <img class="img-circle" src="${ctx}/avatar/group/<c:out value='${var.id}'></c:out>">
                            </a>
                        </div>
                        <div class="media-body">
                            <h4 class="media-heading"><a href="${ctx}/group/index/<c:out value='${var.id}'></c:out>"><c:out value="${var.groupName}"></c:out></a>
                                <span class="group_usercount"><c:out value="${var.memberCount}"></c:out>人</span>
                            </h4>
                            <div class="media-content">
                                <c:out value="${var.groupRemark}"></c:out>
                            </div>
                        </div>
                        <c:if test="${var.groupStatus==1 && !join && groupId!=-1}">
                            <div class="media-hover"></div>
                            <div class="media-action">
                                <a data-id="<c:out value='${var.id}'></c:out>" class="button button_joingroup" href="javascrit:;">入 伙</a>
                            </div>
                        </c:if>
                    </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>


<jsp:include page="../shared/_footer.jsp" />


<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/json2.js"></script>


<script type="text/javascript" src="${ctx}/resource/plugins/layer/layer.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/css3-mediaqueries.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script type="text/javascript">

    $(function () {
        var mWidth = $(".partyIndex").width();
        $(".user_list > li").width((mWidth-16)/4);
        $(document).on("click",".button_joingroup",function(){
            var groupId = $(this).data("id");
            var thisbtn = $(this);
            var thisli = thisbtn.parent().parent().parent();
            $.post("${ctx}/group/join",{groupId:groupId},function(result){
                if(result.status==0){
                    layer.msg(result.message,{icon:6,offset:'110px'});
                    var guserdata = result.data;
                    $(".user_list li[data-id="+guserdata.id+"]").data("group",guserdata.groupId).addClass("user_ingroup user_insame").removeClass("user_along");
                    $(".user_list li[data-id="+guserdata.id+"]").find(".media-action").remove();
                    $(".user_list li[data-id="+guserdata.id+"]").find(".media-hover").remove();

                    $(".user_list li[data-group="+groupId+"]").addClass("user_insame");

                    //把申请按钮去掉
                    thisli.find(".media-hover").remove();
                    thisli.find(".media-action").remove();
                    thisli.addClass("insame");
                }
                else
                    layer.msg(result.message,{icon:5,offset:'110px'});
            },"json");
        }).on("click",".user_list .button_inviteuser",function(){
            var userId = $(this).data("id");
            var thisbtn = $(this);
            var thisli = thisbtn.parent().parent().parent();
            $.post("${ctx}/group/invite",{userId:userId},function(result){
                if(result.status==0){
                    layer.msg(result.message,{icon:6,offset:'110px'});

                    var groupdata = result.data;
                    thisli.data("group",groupdata.id);
                    thisli.addClass("user_ingroup user_insame").removeClass("user_along");
                    //把按钮去掉
                    thisli.find(".media-hover").remove();
                    thisli.find(".media-action").remove();
                }
                else
                    layer.msg(result.message,{icon:5,offset:'110px'});
            },"json");
        }).on("click", ".person_detail_tab li", function () {var thisli = $(this);
            var nTab = $(this).data("tab");
            var oTab = $(".aboutMe .current_content").attr("nodetype");

            $(".aboutMe .current_content").removeClass("current_content");
            $(".person_detail_tab .current_detail").removeClass("current_detail");

            $(this).addClass("current_detail");
            $(".aboutMe ."+nTab).addClass("current_content");

            if(!thisli.data("load")){
                //加载数据
                layer.load(2);

                if(nTab == "myMembers"){

                }
                else{
                    setTimeout(function(){layer.closeAll();},1000);
                    // layer.msg("功能开发中...",{icon:6,offset:'110px'});
                    thisli.data("load",true);
                }
            }
        });
    });

    $(document).on("click",".head_action_joinparty",function(){
        layer.confirm('确定加入该活动？加入之后可参与互动', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var url = window.location.href;
            var partyId = url.substr(url.lastIndexOf("/")+1).replace("#","");
            if(partyId==""){
                layer.msg('获取不到url上的活动信息，请返回再进来？', {shift: 6});
            }else{
                $.post("${ctx}/party/join",{partyId:partyId},function(result){
                    if(result.status==0)
                    {
                        window.location.reload();
                    }else{
                        layer.msg(result.message,{icon:6,offset:'110px'});
                    }
                },"json");
            }
        }, function(){
            layer.closeAll();
        });
        return false;
    })
</script>

</body>
</html>