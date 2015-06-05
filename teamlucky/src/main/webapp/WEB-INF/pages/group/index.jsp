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


    <link rel="icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico"
          type="image/x-icon">
    <link rel="shortcut icon" href="${ctx}/resource/images/favicon.ico" mce_href="${ctx}/resource/images/favicon.ico"
          type="image/x-icon">

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/datepicker.css"/>
    <title> ${model.partyGroupExt.groupName} - 校缘派</title>
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
                <jsp:param value="groupindex" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="main_bg" style="background-color: #fff;">
    <div class="persional_property">
        <div class="person_info_con">
            <c:if test="${model.creater}">
                <i class="fa fa-edit fa-2x person-info-edit"></i><a name="M_base"></a>
            </c:if>
            <dl class="person-photo short-photo">
                <dt>
                    <a href="javascript:;">
                        <img src="${ctx}/avatar/group/${model.partyGroupExt.id}" class="header">
                        <c:if test="${model.creater}">
                        <span class="edit_person_pic" style="overflow:hidden;">
	                		<input id="user_avatar" name="file" type="file"
                                   style="opacity:0; width:200px;height:200px;position:absolute;left:-50px;"/>
	                	</span>
                        </c:if>
                    </a>
                </dt>
            </dl>
            <dl class="person-info">
                <dt class="person-nick-name">
                    <span>${model.partyGroupExt.groupName}</span>
                </dt>
                <dd class="person-detail">
                    <span>由 <c:out value="${model.partyGroupExt.groupAuthor.userName}"></c:out> 创建于 <fmt:formatDate
                            value="${model.partyGroupExt.createTime}"
                            pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></span></span>
                    <span>|</span>
                    <c:if test="${model.partyGroupExt.isSourcePublic}">
                        <span class="info_null public_view">资源共享<i class="fa fa-info-circle"
                                                                   title="资源能够使其他活动成员查看，不过需设置资源公开属性"></i></span>
                    </c:if>
                    <c:if test="${!model.partyGroupExt.isSourcePublic}">
                        <span class="public_view">资源私有<i class="fa fa-info-circle" title="资源仅供本小组成员查看"></i></span>
                    </c:if>
                </dd>
                <dd class="person-sign">
                    <span><c:out value="${model.partyGroupExt.groupRemark}"></c:out></span>
                </dd>
            </dl>

        </div>
    </div>

    <div class="persion_section">
        <div class="person_detail_tab party_tab">
            <ul>
                <li data-modal="tab" data-tab="myTimeline" data-load=true class="current_detail">小组动态</li>
                <li data-modal="tab" data-tab="myNews" data-load=false >新闻通知</li>
                <li data-modal="tab" data-tab="myDocs" data-load=false >文档</li>
                <li data-modal="tab" data-tab="myForum" data-load=false >论坛</li>
            </ul>
        </div>
        <div class="aboutMe">
            <div nodetype="myDetails" nodeindex="my0" data-modal="tab-layer" class="myDetails current_content">
                <div class="mod_contact">
                    <a href="#" nodetype="contact-modify" class="modify fa fa-edit fa-2x"></a>
                    <ul class="clearfix">
                    </ul>
                </div>
            </div>
            <div nodetype="myNews" nodeindex="my2" data-modal="tab-layer" class="myNews">

            </div>
            <div nodetype="myMessages" nodeindex="my3" data-modal="tab-layer" class="myMessages">
                <div data-bind="collect" class="mod-my-collect">
                    <div class="silder-wraper">

                        <div class="operate clearfix"></div>
                    </div>
                    <div class="silder-content">
                        <ul class="J-more"></ul>
                    </div>
                    <a href="#" class="more" style="display: none;">显示更多<i class="icon-angle-down"></i></a>
                </div>
            </div>
        </div>
        <div class="group_section">
            <div class="section_title">
                <i class="fa fa-group"></i>小组成员
            </div>
            <div class="section_content">
                <ul>
                    <c:forEach items="${ model.groupUsers }" var="var" varStatus="status">
                        <li>
                            <div class="media">
                                <div class="media-left">
                                    <img class="img-circle" src="${ctx}/avatar/user/2/<c:out value='${var.id}'></c:out>">
                                </div>
                                <div class="media-body">
                                    <h4 class="media-heading" id="media-heading"><c:out value="${var.userName}"></c:out>
                                        <span class="time_lastlogin"><fmt:formatDate
                                                value="${model.partyGroupExt.createTime}"
                                                pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></span>
                                    </h4>
                                    <div class="media-content">
                                        <c:out value="${var.userRemark}"></c:out>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="pop_edit edit_intro">
        <h3>小组配置</h3>

        <div class="context">
            <form id="form" nodetype="form-popup" class="form">
                <ul>
                    <li class="mp_dl"><em class="red">*</em><span>小组名称：
                  <input name="groupName" nodetype="partyName" important="yes" type="text" placeholder="支持中文、英文、数字"
                         value="<c:out value='${model.partyGroupExt.groupName}'></c:out>" maxlen="20"
                         class="groupName mp_field1">
                  </span>
                    </li>
                    <li class="mp_dl">自由加入：
                        <label>
                            <c:if test="${model.partyGroupExt.isCustomJoin}">
                                <input type="checkbox" class="isCustomIn" checked /> 不需要创建者审核就能加入该组
                            </c:if>
                            <c:if test="${!model.partyGroupExt.isCustomJoin}">
                                <input type="checkbox" class="isCustomIn"/> 不需要创建者审核就能加入该组
                            </c:if>
                        </label>
                    </li>
                    <li class="mp_dl">资源共享：
                        <label>
                            <c:if test="${model.partyGroupExt.isSourcePublic}">
                                <input type="checkbox" class="isPublic" checked /> 设置为共享的资源，其他组成员可见
                            </c:if>
                            <c:if test="${!model.partyGroupExt.isSourcePublic}">
                                <input type="checkbox" class="isPublic" /> 设置为共享的资源，其他组成员可见
                            </c:if>
                        </label>
                    </li>
                    <li class="mp_dl"><em class="red">*</em>小组描述：
                        <textarea name="groupRemark" nodetype="groupRemark" placeholder="200字以内" maxlen="200"
                                  class="intro_info mp_wid groupRemark" style="width:525px"><c:out
                                value='${model.partyGroupExt.groupRemark}'></c:out></textarea>
                    </li>
                </ul>
            </form>
        </div>
        <div class="success"><a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">确定</a>
        </div>
    </div>
    <div class="clearfix"></div>
</div>

<div style="display: none;" id="photo_popup">
    <div id="edit-photo" style="height: 390px;">
        <div class="photo">
            <img src="" alt=""/>
        </div>
        <div class="preview">
            <img src="" alt=""/>
        </div>
        <div class="btn-div">
            <a id="js-save" href="#" class="button button--secondary" data-method="post" rel="nofollow">确定</a>
        </div>
    </div>
</div>

<jsp:include page="../shared/_footer.jsp"/>

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/ajaxfileupload.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.validate.ext.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/json2.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery.imgareaselect.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/plugins/layer/layer.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>

<script type="text/javascript" src="${ctx}/resource/plugins/datetime/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/datetime/locales/bootstrap-datepicker.zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script type="text/javascript">
    var layerLoadIndex = 0;
    $(function () {

        $(document).on("click", ".person-info-edit", function () {//修改基本信息
            $(".edit_intro").show();
            var width = $(".edit_intro").width();
            var height = $(".edit_intro").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                area: [width + 'px', height + 'px'],
                content: $(".edit_intro")
            });
        }).on("click", ".pop_edit a[nodetype='cancel']", function () { //取消按钮
            layer.closeAll();
        }).on("click", ".edit_intro .js_save", function () {//保存基本信息
            var gName = $(".edit_intro .groupName").val();
            var gRemark = $(".edit_intro textarea").val();
            var gCustom = $(".edit_intro .isCustomIn").prop("checked");
            var gPublic = $(".edit_intro .isPublic").prop("checked");

            if (gName == "") {
                layer.msg("小组名称不能为空", {icon: 5, offset: "110px"});
                return false;
            }
            if (gRemark == "") {
                layer.msg("小组简介不能为空", {icon: 5, offset: "110px"});
                return false;
            }

            var postDate = {groupName: gName, groupRemark: gRemark,isSourcePublic:gPublic,isCustomJoin:gCustom};

            $.ajax({
                url: "${ctx}/group/edit",
                dataType: "json",
                data: postDate,
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("基本信息保存成功", {icon: 6, offset: '110px'});

                        $(".person-nick-name span").text(gName);

                        if (gPublic)
                            $(".public_view").html('资源共享<i class="fa fa-info-circle" title="资源能够使其他活动成员查看，不过需设置资源公开属性"></i>').addClass("info_null");
                        else
                            $(".public_view").html('资源私有<i class="fa fa-info-circle" title="资源仅供本小组成员查看"></i>').removeClass("info_null");

                        $(".person-sign span").text(gRemark);
                    }
                    else
                        layer.msg(res.message, {icon: 5, offset: '110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("保存失败", {icon: 5, offset: '110px'});
                }
            })
        }).on("change", ".edit_person_pic input[type='file']", function () {
            var ths = $(this);
            $.ajaxFileUpload({
                url: "${ctx}/avatar/upload",
                fileElementId: $(this).attr("id"),
                secureuri: false,
                dataType: "text",
                data: {type: 3},
                type: "post",
                success: function (res) {
                    layer.closeAll();

                    var resStr = $(res).text();
                    var bak = JSON.parse(resStr);
                    bak = JSON.parse(bak);
                    if (bak.status == 0) {
                        $("#edit-photo .photo img").attr("src", "${ctx}/" + bak.data);
                        $("#edit-photo .preview img").attr("src", "${ctx}/" + bak.data);
                        $('#edit-photo .photo img').imgAreaSelect({
                            x1: 0,
                            y1: 0,
                            x2: 80,
                            y2: 80,
                            selectionOpacity: 0.2,
                            aspectRatio: '1:1',
                            onSelectChange: preview,
                            zIndex: 10000,
                            persistent: true
                        });
                        $("#edit-photo #js-save").attr({
                            "scale": "1",
                            "x1": "0",
                            "y1": "0",
                            "x2": "80",
                            "y2": "80"
                        }).data("path", bak.data);
                    } else {
                        layer.msg(bak.message, {icon: 5, offset: '110px'});
                    }
                },
                complete: function (XHR, TS) {
                },
                beforeSend: function (XHR) {
                    layer.load(2);
                }
            });
        }).on("click", "#edit-photo #js-save", function () {
            avatarResize();
            return false;
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

        //图片加载完成之后
        $("#edit-photo .photo img").load(function () {
            $("#photo_popup").show();

            $img = $("#edit-photo .photo img");
            var imgW = $img.width();
            var imgH = $img.height();
            $("#edit-photo").width(imgW + 140);

            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                scrollbar: false,
                area: ['500px', '390px'],
                content: $("#edit-photo"),
                success: function (layero, index) {
                    setTimeout(function () {
                        var initOffset = $img.offset();

                        var imgW = $img.width();
                        var imgH = $img.height();

                        $(".imgareaselect-selection").parent("div").css({
                            "top": initOffset.top,
                            "left": initOffset.left
                        });

                        $(".imgareaselect-outer").each(function (indx) {
                            if (indx < 2)
                                $(this).css({"top": initOffset.top, "left": initOffset.left});
                            else if (indx == 2)
                                $(this).css({
                                    "top": initOffset.top,
                                    "left": initOffset.left + 80,
                                    "width": (imgW - 80),
                                    "height": imgH
                                });
                            else
                                $(this).css({
                                    "top": initOffset.top + 80,
                                    "left": initOffset.left,
                                    "width": 80,
                                    "height": (imgH - 80)
                                });
                        });
                    }, 500);
                },
                end: function () {
                    if ($(".imgareaselect-outer").length != 0) {
                        $(".imgareaselect-selection").parent("div").hide();
                        $(".imgareaselect-outer").hide();
                    }
                    $("#photo_popup").hide();
                }
            });
        });

    });

    //图片选区
    function preview(img, selection) {
        var scaleX = 80 / (selection.width || 1);
        var scaleY = 80 / (selection.height || 1);

        $('.preview img').css({
            width: Math.round(scaleX * $(img).width()) + 'px',
            height: Math.round(scaleY * $(img).height()) + 'px',
            marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
            marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
        });
        $("#edit-photo #js-save").attr({
            "scale": scaleX,
            "x1": selection.x1,
            "y1": selection.y1,
            "x2": selection.x2,
            "y2": selection.y2
        });
    }

    //确定所选区域
    function avatarResize() {
        var obj = $("#edit-photo #js-save");
        //需裁剪的头像 和 当前头像
        var src = obj.data("path");

        var dataToSend = {
            filePath: src,
            type: 3,
            user: 3,
            x1: obj.attr('x1'),
            y1: obj.attr('y1'),
            x2: obj.attr('x2'),
            y2: obj.attr('y2')
        };
        $.ajax({
            url: "${ctx}/avatar/cut",
            data: dataToSend,
            dataType: "json",
            type: "POST",
            success: function (res) {
                if (res.status == 0) {
                    layer.closeAll();

                    layer.msg("头像保存成功", {icon: 6, offset: '110px'});
                    $(".person-photo img").attr("src", "${ctx}/" + res.data);
                }
                else
                    layer.msg(res.message, {icon: 5, offset: '110px'});
            },
            error: function (data, status, e) {
                layer.closeAll();
                layer.msg("头像保存失败", {icon: 5, offset: '110px'});
            }
        });
    }
</script>

</body>
</html>