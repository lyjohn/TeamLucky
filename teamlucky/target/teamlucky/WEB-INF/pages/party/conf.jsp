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

    <title> ${model.partyExt.partyName} - 管理后台 - 校缘派</title>
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
                <jsp:param value="partyconf" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="main_bg" style="background-color: #fff;">
    <div class="persional_property">
        <div class="person_info_con"><i class="fa fa-edit fa-2x person-info-edit"></i><a name="M_base"></a>
            <dl class="person-photo short-photo">
                <dt>
                    <a href="javascript:;">
                        <img src="${ctx}/avatar/party/${model.partyExt.id}" class="header">
	                	<span class="edit_person_pic" style="overflow:hidden;">
	                		<input id="user_avatar" name="file" type="file"
                                   style="opacity:0; width:200px;height:200px;position:absolute;left:-50px;"/>
	                	</span>
                    </a>
                </dt>
            </dl>
            <dl class="person-info">
                <dt class="person-nick-name">
                    <span>${model.partyExt.partyName}</span>
                </dt>
                <dd class="person-detail">
                    <span class="info_null">${model.partyExt.partyCode}</span>
                    <span>|</span>
                    <c:if test="${model.partyExt.isPublic}">
                        <span class="public_view">公开活动</span>
                    </c:if>
                    <c:if test="${!model.partyExt.isPublic}">
                        <span class="info_null public_view">私密活动</span>
                    </c:if>
                </dd>
                <dd class="person-sign">
                    <span><c:out value="${model.partyExt.partyRemark}"></c:out></span>
                </dd>
            </dl>

        </div>
    </div>

    <div class="persion_section">
        <div class="person_detail_tab party_tab">
            <ul>
                <li data-modal="tab" data-tab="myDetails" class="current_detail">基本配置</li>
                <li data-modal="tab" data-tab="myNews">成员管理</li>
                <c:if test="${model.partyExt.isGroup}">
                    <li data-modal="tab" data-tab="myMessages">小组管理</li>
                </c:if>
                <li data-modal="tab" data-tab="myMessages">新闻通知</li>
                <li data-modal="tab" data-tab="myMessages">文档维护</li>
                <li data-modal="tab" data-tab="myMessages">论坛维护</li>
            </ul>
        </div>
        <div class="aboutMe">
            <div nodetype="myDetails" nodeindex="my0" data-modal="tab-layer" class="myDetails current_content">
                <div class="mod_contact">
                    <c:if test="${model.partyExt.isGroup}">
                        <a href="#" nodetype="contact-modify" class="modify fa fa-edit fa-2x"></a>
                    </c:if>
                    <ul class="clearfix">
                        <li><span class="li_title">是否分组：</span>
                            <span nodetype="isGroup" class="isGroup">
                                <c:if test="${!model.partyExt.isGroup}">
                                    禁用分组
                                </c:if>
                                <c:if test="${model.partyExt.isGroup}">
                                    允许分组
                                </c:if>
                            </span>
                        </li>
                        <c:if test="${model.partyExt.isGroup}">
                            <li><span class="li_title">每组最少人数：</span>
                            <span nodetype="memberNumMin" class="memberNumMin">
                                <c:if test="${model.partyExt.memberNumMin == 0}">
                                    不限制
                                </c:if>
                                <c:if test="${model.partyExt.memberNumMin > 0}">
                                    <c:out value="${model.partyExt.memberNumMin}"></c:out> 人
                                </c:if>
                            </span>
                            </li>
                            <li><span class="li_title">每组最多人数：</span>
                            <span nodetype="memberNumMax" class="memberNumMax">
                                <c:if test="${model.partyExt.memberNumMax == 0}">
                                    不限制
                                </c:if>
                                <c:if test="${model.partyExt.memberNumMax > 0}">
                                    <c:out value="${model.partyExt.memberNumMax}"></c:out> 人
                                </c:if>
                            </span>
                            </li>
                            <li><span class="li_title">是否自行组队：</span>
                            <span nodetype="customBuild" class="customBuild">
                                <c:if test="${!model.partyExt.isCustomBuild}">
                                    成员自行分组
                                </c:if>
                                <c:if test="${model.partyExt.isCustomBuild}">
                                    管理员分组
                                </c:if>
                            </span>
                            </li>
                            <li><span class="li_title">分组截止日期：</span>
                            <span nodetype="buildEndTime" class="buildEndTime">
                                <c:if test="${!empty model.partyExt.buildEndTime}">
                                    <fmt:formatDate value="${model.partyExt.buildEndTime}"
                                                    pattern="yyyy-MM-dd 23:59"></fmt:formatDate></span>
                                </c:if>
                                <c:if test="${empty model.partyExt.buildEndTime}">
                                    不限制
                                </c:if>
                                </span>
                            </li>
                        </c:if>
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
    </div>
    <div class="pop_edit edit_intro">
        <h3>编辑简介</h3>

        <div style="background: #f3f3f3; border:1px solid #DCDCDC; margin-bottom:30px; padding:0 17px; color:#333; font-size:16px; line-height:30px;">
            <span>活动识别码 :</span><span style="color: #666666; margin-left:15px;"><c:out
                value="${model.partyExt.partyCode}"></c:out></span>
        </div>
        <div class="context">
            <form id="form" nodetype="form-popup" class="form">
                <ul>
                    <li class="mp_dl"><em class="red">*</em><span>活动名称：
                  <input name="partyName" nodetype="partyName" important="yes" type="text" placeholder="支持中文、英文、数字"
                         value="<c:out value='${model.partyExt.partyName}'></c:out>" maxlen="20"
                         class="partyName mp_field1">
                  </span></li>
                    <li><span class="mp_label">是否公开：
                    <c:if test="${model.partyExt.isPublic}">
                        <input type="checkbox" name="isPublic" checked="">
                    </c:if>
                    </li>
                    <li class="mp_dl"><em class="red">*</em>活动描述：
                        <textarea name="partyRemark" nodetype="selfdesc" placeholder="200字以内" maxlen="200"
                                  class="intro_info mp_wid" style="width:525px"><c:out
                                value='${model.partyExt.partyRemark}'></c:out></textarea>
                    </li>
                </ul>
            </form>
        </div>
        <div class="success"><a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">确定</a>
        </div>
    </div>
    <div class="pop_edit edit_partyseting">
        <h3>配置组队设置</h3>
        <div class="context">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="inputMinNum" class="col-sm-3 control-label">每队最少人数</label>

                    <div class="col-sm-9">
                        <input type="number" min="0" class="form-control" id="inputMinNum" name="inputMinNum" placeholder="最少人数"
                               value= '<c:out value="${model.partyExt.memberNumMin}"></c:out>' />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputMaxNum" class="col-sm-3 control-label">每队最多人数</label>
                    <div class="col-sm-9">
                        <input type="number" min="0" class="form-control" id="inputMaxNum" name="inputMaxNum" placeholder="最多人数"
                               value= '<c:out value="${model.partyExt.memberNumMax}"></c:out>' />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputCustom" class="col-sm-3 control-label">允许自行组队</label>
                    <div class="col-sm-9">
                        <input type="checkbox" name="inputCustom" id="inputCustom"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputEndTime" class="col-sm-3 control-label">截止日期</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputEndTime" name="inputEndTime" placeholder="组队截止日期"
                               value="<fmt:formatDate value='${model.partyExt.buildEndTime}' pattern='yyyy-MM-dd'></fmt:formatDate>" />
                    </div>
                </div>
            </form>
        </div>
        <div class="success"><a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">确定</a></div>
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
        $("#inputEndTime").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd",
            language: 'zh-CN'
        });

        $(document).on("click", ".modify", function () {//修改配置信息
            $(".edit_partyseting").show();
            var width = $(".edit_partyseting").width();
            var height = $(".edit_partyseting").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                area: [width + 'px', height + 'px'],
                content: $(".edit_partyseting")
            });
        }).on("click", ".person-info-edit", function () {//修改基本信息
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
        }).on("click", ".edit_partyseting .js_save", function () { //保存联系方式
            var minNum = $("#inputMinNum").val();
            var maxNum = $("#inputMaxNum").val();
            var isCustom = $("#inputCustom").prop("checked");
            var endTime = $("#inputEndTime").val();

            var postDate = { memberNumMin: minNum,memberNumMax:maxNum, isCustomBuild: isCustom};
            if(endTime != "")
                postDate.buildEndTime = new Date(endTime);

            $.ajax({
                url: "${ctx}/party/edit",
                dataType: "json",
                data: postDate,
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("组队配置保存成功", {icon: 6, offset: '110px'});

                        $(".mod_contact .memberNumMin").text(minNum);
                        $(".mod_contact .memberNumMax").text(maxNum);
                        $(".mod_contact .buildEndTime").text(endTime);

                        if(isCustom)
                            $(".mod_contact .customBuild").text("成员自行分组");
                        else
                            $(".mod_contact .customBuild").text("管理员分组");
                    }
                    else
                        layer.msg(res.message, {icon: 5, offset: '110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("保存失败", {icon: 5, offset: '110px'});
                }
            })
        }).on("click", ".edit_intro .js_save", function () {//保存基本信息
            var pName = $(".edit_intro .partyName").val();
            var pPublic = $(".edit_intro :checkbox").prop("checked");
            var pRemark = $(".edit_intro textarea").val();

            if (pName == "") {
                layer.msg("活动名称不能为空", {icon: 5, offset: "110px"});
                return false;
            }

            if (pRemark == "") {
                layer.msg("活动描述不能为空", {icon: 5, offset: "110px"});
                return false;
            }

            var postDate = {partyName: pName, isPublic: pPublic, partyRemark: pRemark};
            $.ajax({
                url: "${ctx}/party/edit",
                dataType: "json",
                data: postDate,
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("基本信息保存成功", {icon: 6, offset: '110px'});

                        $(".person-nick-name span").text(pName);
                        if(pPublic)
                            $(".public_view").text("公开活动").removeClass("info_null");
                        else
                            $(".public_view").text("私密活动").addClass("info_null");

                        $(".person-sign span").text(pRemark);
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
                data: {type: 2},
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
        }).on("click", ".person_detail_tab li", function () {
            var nTab = $(this).data("tab");
            var oTab = $(".aboutMe .current_content").attr("nodetype");

            $(".aboutMe .current_content").removeClass("current_content");
            $(".person_detail_tab .current_detail").removeClass("current_detail");

            $(this).addClass("current_detail");
            $(".aboutMe ." + nTab).addClass("current_content");
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
            type: 2,
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