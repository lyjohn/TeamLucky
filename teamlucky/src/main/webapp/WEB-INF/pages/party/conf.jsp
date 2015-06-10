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
                <li data-modal="tab" data-tab="myDetails" data-load=true class="current_detail">基本配置</li>
                <li data-modal="tab" data-tab="myMembers" data-load=false>成员管理</li>
                <c:if test="${model.partyExt.isGroup}">
                    <li data-modal="tab" data-tab="myGroups" data-load=false>小组管理</li>
                </c:if>
                <li data-modal="tab" data-tab="myNews" data-load=false>新闻通知</li>
                <li data-modal="tab" data-tab="myDocuments" data-load=false>文档维护</li>
                <li data-modal="tab" data-tab="myForums" data-load=false>论坛维护</li>
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
            <div nodetype="myMembers" nodeindex="my2" data-modal="tab-layer" class="myMembers">
                <div class="connection_title_con">
                    <ul class="member-filter">
                        <li class="current_foucus filter all-member" data-seq=0>所有成员(<span>0</span>)</li>
                        <li class="interval_line"></li>
                        <li class="filter invalid-member" data-seq=1>已禁用(<span>0</span>)</li>
                        <li class="interval_line"></li>
                        <li class="filter ingroup-member" data-seq=2>已分组(<span>0</span>)</li>
                        <li class="interval_line"></li>
                        <li class="filter nogroup-member" data-seq=3>未分组(<span>0</span>)</li>
                    </ul>
                    <div class="connection_action_form">
                        <a class="download" href="${ctx}/party/exportModel" target="_blank">活动成员导入模板.xlsx</a>
                        <a class="import_member button button--secondary" href="#" data-method="post" rel="nofollow" style="overflow:hidden;position: relative; margin-right:5px;">批量导入
                            <input id="import_user" name="file" type="file" style="opacity:0; width:200px;height:200px;position:absolute;right: 0;top: 0;">
                        </a>
                        <a class="add_partyuser button button--secondary" href="#" data-method="post" rel="nofollow" style="overflow:hidden;position: relative;">手动添加
                        </a>
                    </div>
                </div>
                <div class="connection_list_con clearfix">
                    <table class="table table-striped member-table">
                        <thead>
                        <tr>
                            <th style="width:11%;min-width:50px">帐号</th>
                            <th style="width:8%;min-width:50px">昵称</th>
                            <th style="width:7%;min-width:20px">性别</th>
                            <th style="width:13%;min-width:100px">简介</th>
                            <th style="width:8%;min-width:50px">电话</th>
                            <th style="width:8%;min-width:50px">邮箱</th>
                            <th style="width:6%;min-width:50px">QQ</th>
                            <th style="width:6%;min-width:50px">微信</th>
                            <th style="width:8%;min-width:50px">所属团队</th>
                            <th style="width:6%;min-width:50px">状态</th>
                            <th style="width:10%;min-width:100px">上次登录日期</th>
                            <th style="width:13%;min-width:50px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <c:if test="${model.partyExt.isGroup}">
                <div nodetype="myGroups" nodeindex="my2" data-modal="tab-layer" class="myGroups">
                    <div class="connection_title_con">
                        <ul class="group-filter">
                            <li class="current_foucus filter all-group" data-seq=0>所有小组(<span>0</span>)</li>
                            <li class="interval_line"></li>
                            <li class="filter complete-group" data-seq=1>分组完成(<span>0</span>)</li>
                            <li class="interval_line"></li>
                            <li class="filter processing-group" data-seq=2>分组进行中(<span>0</span>)</li>
                        </ul>
                        <div class="connection_action_form">
                            <a class="add_partygroup button button--secondary" href="#" data-method="post" rel="nofollow" style="overflow:hidden;position: relative;">创建小组
                            </a>
                        </div>
                    </div>
                    <div class="connection_list_con clearfix">
                        <table class="table table-striped member-table">
                            <thead>
                            <tr>
                                <th style="width:15%;min-width:50px">名称</th>
                                <th style="width:50%;min-width:100px">描述</th>
                                <th style="width:15%;min-width:100px">创建日期</th>
                                <th style="width:10%;min-width:20px">人数</th>
                                <th style="width:10%;min-width:20px">活跃度</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
            <div nodetype="myNews" nodeindex="my2" data-modal="tab-layer" class="myNews">
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
            <div nodetype="myDocuments" nodeindex="my3" data-modal="tab-layer" class="myDocuments">
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
            <div nodetype="myForums" nodeindex="my3" data-modal="tab-layer" class="myForums">
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
    <div class="pop_edit reg_partyuser">
        <h3>创建活动用户</h3>
        <div class="context">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="inputPartyUser" class="col-sm-3 control-label">* 用户帐号</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputPartyUser" name="inputPartyUser" placeholder="活动的登录帐号，不包含_" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPartyPwd" class="col-sm-3 control-label">用户密码</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputPartyPwd" name="inputPartyPwd" placeholder="活动用户的密码，默认 123456" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputUserName" class="col-sm-3 control-label">用户昵称</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputUserName" name="inputUserName" placeholder="用户的显示名名称" />
                    </div>
                </div>
            </form>
        </div>
        <div class="success">
            <a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">创建</a>
        </div>
    </div>
    <div class="pop_edit reg_partygroup">
        <h3>创建小组</h3>
        <div class="context">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="inputGroupName" class="col-sm-3 control-label">* 小组名称</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputGroupName" name="inputGroupName" placeholder="可含中文字母或特殊符号" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputGroupRemark" class="col-sm-3 control-label">* 小组简介</label>
                    <div class="col-sm-9">
                        <textarea class="form-control" id="inputGroupRemark" name="inputGroupRemark" placeholder="小组简介不能为空"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputGroupAdmin" class="col-sm-3 control-label">* 设置组长</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputGroupAdmin" name="inputGroupAdmin" placeholder="输入名字查找" />
                    </div>
                </div>
            </form>
        </div>
        <div class="success">
            <a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">创建</a>
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

<script type="text/javascript" src="${ctx}/resource/js/template-native.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/jquery.caret.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.atwho.min.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script id="partyuserlist" type="text/html">
    ${'<'}% for(var i = 0; i < list.length; i++) { %${'>'}
    ${'<'}% if( list[i].statusName == "已禁用") { %${'>'}
    <tr class="danger">
        ${'<'}% }else{ %${'>'}
    <tr class="">
        ${'<'}% } %${'>'}
        <td><img class="user-hover" data-hover="${'<'}%= list[i].id%${'>'}" alt="${'<'}%= list[i].userName %${'>'}" src="${ctx}/avatar/user/2/${'<'}%= list[i].id%${'>'}" /><span class="user_loginname">${'<'}%= list[i].loginName %${'>'}</span></td>
        <td>${'<'}%= list[i].userName %${'>'}</td>
        <td>${'<'}%= list[i].sex %${'>'}</td>
        <td>${'<'}%= list[i].userRemark %${'>'}</td>
        <td>${'<'}%= list[i].tel %${'>'}</td>
        <td>${'<'}%= list[i].email %${'>'}</td>
        <td>${'<'}%= list[i].qq %${'>'}</td>
        <td>${'<'}%= list[i].weiXin %${'>'}</td>
        ${'<'}% if( list[i].group == null) { %${'>'}
        <td class="nogroup">无组织</td>
        ${'<'}% }else{ %${'>'}
        <td class="ingroup">${'<'}%= list[i].group.groupName%${'>'}</td>
        ${'<'}% } %${'>'}
        <td>${'<'}%= list[i].statusName %${'>'}</td>
        <td>${'<'}%= list[i].lastLoginTimeStr %${'>'}</td>
        <td class="table-actions" data-id="${'<'}%= list[i].id%${'>'}">
            ${'<'}% if( list[i].statusName == "已禁用") { %${'>'}
            <a class='user_valid' data-setvalid=true>激活</a>
            ${'<'}% }else{ %${'>'}
            <a class='user_valid' data-setvalid=false>禁用</a>
            ${'<'}% } %${'>'}
            <a class='action_resetpwd'>重置密码</a>
        </td>
    </tr>
    ${'<'}% } %${'>'}
</script>
<script id="partygrouplist" type="text/html">
    ${'<'}% for(var i = 0; i < list.length; i++) { %${'>'}
    <tr>
        <td><img alt="${'<'}%= list[i].groupName %${'>'}" src="${ctx}/avatar/group/${'<'}%= list[i].id%${'>'}" /><span class="user_loginname">${'<'}%= list[i].groupName %${'>'}</span></td>
        <td>${'<'}%= list[i].groupRemark %${'>'}</td>
        <td>${'<'}%= list[i].createTimeStr %${'>'}</td>
        <td>${'<'}%= list[i].memberCount %${'>'}</td>
        <td>${'<'}%= list[i].hotCount %${'>'}</td>
    </tr>
    ${'<'}% } %${'>'}
</script>
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
        }).on("click",".add_partyuser",function(){
            $(".reg_partyuser").show();
            var width = $(".reg_partyuser").width();
            var height = $(".reg_partyuser").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                area: [width + 'px', height + 'px'],
                content: $(".reg_partyuser")
            });
        }).on("click",".add_partygroup",function(){
            $(".reg_partygroup").show();
            var width = $(".reg_partygroup").width();
            var height = $(".reg_partygroup").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                area: [width + 'px', height + 'px'],
                content: $(".reg_partygroup")
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
        }).on("click", ".reg_partyuser .js_save", function () {//创建活动帐号
            var puName = $(".reg_partyuser #inputPartyUser").val();
            var puPwd = $(".reg_partyuser #inputPartyPwd").val();
            var puNick= $(".reg_partyuser #inputUserName").val();

            if (puName == "") {
                layer.msg("帐号不能为空", {icon: 5, offset: "110px"});
                return false;
            }

            if (puName.indexOf("_") > -1) {
                layer.msg("帐号不能包含_，系统会自动添加", {icon: 5, offset: "110px"});
                return false;
            }

            var postDate = {loginName: puName, loginPwd: puPwd, userName: puNick};
            $.ajax({
                url: "${ctx}/party/createuser",
                dataType: "json",
                data: postDate,
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("创建成员成功", {icon: 6, offset: '110px'});

                        $(".reg_partyuser input").val("");

                        var data = {list:res.data};
                        var html = template('partyuserlist',data);
                        $(".myMembers table tbody").append(html);

                        var total = $(".all-member span").text()*1;
                        var nogroup = $(".nogroup-member span").text()*1;

                        $(".all-member span").text(total+1);
                        $(".nogroup-member span").text(nogroup+1);
                    }
                    else
                        layer.msg(res.message, {icon: 5, offset: '110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("保存失败", {icon: 5, offset: '110px'});
                }
            })
        }).on("click", ".reg_partygroup .js_save", function () {//创建活动小组
            var pgName = $(".reg_partygroup #inputGroupName").val();
            var pgRemark = $(".reg_partygroup #inputGroupRemark").val();
            var pgCreater = $(".reg_partygroup #inputGroupAdmin").val();
            var pgCreaterId = $(".reg_partygroup #inputGroupAdmin").data("id");
            if (pgName == "" || pgRemark=="" || pgCreater=="") {
                layer.msg("所有的内容都是必填的", {icon: 5, offset: "110px"});
                return false;
            }

            var postDate = {groupName: pgName, groupRemark: pgRemark, createBy: pgCreaterId};
            $.ajax({
                url: "${ctx}/party/creategroup",
                dataType: "json",
                data: postDate,
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("创建小组成功", {icon: 6, offset: '110px'});

                        $(".reg_partygroup input").val("");
                        $(".reg_partygroup #inputGroupAdmin").removeData();

                        var data = {list:res.data};
                        var html = template('partygrouplist',data);
                        $(".myGroups table tbody").append(html);

                        var total = $(".all-group span").text()*1;
                        var process = $(".processing-group span").text()*1;

                        $(".all-group span").text(total+1);
                        $(".processing-group span").text(process+1);
                    }
                    else
                        layer.msg(res.message, {icon: 5, offset: '110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("保存失败, JS有误，请重试", {icon: 5, offset: '110px'});
                }
            })
        }).on("change", ".edit_person_pic input[type='file']", function () { //上传图片
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
        }).on("change", ".import_member input[type='file']", function () {//上传成员名单
            var ths = $(this);
            $.ajaxFileUpload({
                url: "${ctx}/party/importuser",
                fileElementId: $(this).attr("id"),
                secureuri: false,
                dataType: "text",
                data: {},
                type: "post",
                success: function (res) {
                    layer.closeAll();

                    var resStr = $(res).text();
                    var bak = JSON.parse(resStr);
                    bak = JSON.parse(bak);
                    if (bak.status == 0) {
                        var added = bak.data.length;
                        layer.msg("成功导入 "+added+" 名新成员", {icon: 6, offset: '110px'});

                        var data = {list:bak.data};
                        var html = template('partyuserlist',data);
                        $(".myMembers table tbody").append(html);

                        var total = $(".all-member span").text()*1;
                        var nogroup = $(".nogroup-member span").text()*1;
                        $(".all-member span").text(total+added);
                        $(".nogroup-member span").text(nogroup+added);
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
        }).on("click", ".person_detail_tab li", function () {//切换tab
            var thisli = $(this);
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
                    $.get("${ctx}/party/userlist",{},function(result){
                        if(result.status==0){
                            var data = {list:result.data};
                            var html = template('partyuserlist',data);
                            $(".myMembers table tbody").html(html);

                            layer.closeAll();

                            var total = $(".member-table tbody tr").length;
                            var invalid = $(".member-table tbody tr.danger").length;
                            var nogroup = $(".member-table tbody td.nogroup").length;
                            var ingroup = total-nogroup;
                            $(".all-member span").text(total);
                            $(".invalid-member span").text(invalid);
                            $(".nogroup-member span").text(nogroup);
                            $(".ingroup-member span").text(ingroup);

                            thisli.data("load",true);
                        }else{
                            layer.closeAll();
                            layer.msg(result.message,{icon:6,offset:'110px'});
                        }
                    },"json")
                }
                else if(nTab == "myGroups"){
                    $.get("${ctx}/party/grouplist",{},function(result){
                        if(result.status==0){
                            var data = {list:result.data};
                            var html = template('partygrouplist',data);
                            $(".myGroups table tbody").html(html);

                            layer.closeAll();
                            thisli.data("load",true);
                        }else{
                            layer.closeAll();
                            layer.msg(result.message,{icon:6,offset:'110px'});
                        }
                    },"json")
                }
                else{
                    layer.closeAll();
                    layer.msg("功能开发中...",{icon:6,offset:'110px'});
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

        $(document).on("click",".myMembers .table-actions a.user_valid",function(){
            var thisa = $(this);
            var thistr = $(this).parent().parent();
            var userId = $(this).parent("td").data("id");
            var setvalid = $(this).data("setvalid");

            $.post("${ctx}/party/uservalid",{userId:userId,setvalid:setvalid},function(result){
                if(result.status==0){
                    if(setvalid){
                        thisa.data("setvalid",!setvalid);

                        layer.msg("激活成功",{icon:6,offset:'110px'});
                        thisa.text("禁用");
                        thistr.removeClass("danger");
                    }else{
                        thisa.data("setvalid",!setvalid);

                        layer.msg("禁用成功",{icon:6,offset:'110px'});
                        thisa.text("激活");
                        thistr.addClass("danger");
                    }
                }else{
                    layer.msg(result.message,{icon:5,offset:'110px'});
                }
            },"json")

        }).on("click",".myMembers .table-actions a.action_resetpwd",function(){
            var userId = $(this).parent("td").data("id");

            $.post("${ctx}/party/resetpwd",{userId:userId},function(result){
                if(result.status==0){
                    layer.msg(result.message,{icon:6,offset:'110px'});
                }else{
                    layer.msg(result.message,{icon:5,offset:'110px'});
                }
            },"json")
        });

        //切换用户列表状态筛选
        $(document).on("click",".member-filter li.filter",function(){
            var seq = $(this).data("seq");
            switch(seq){
                case 0:
                    $(".member-table tbody tr").slideDown();
                    break;
                case 1:
                    $(".member-table tbody tr:not(.danger)").slideUp();
                    break;
                case 2:
                    $(".member-table tbody tr").slideUp();
                    $(".member-table tbody td.ingroup").parent().slideDown();
                    break;
                case 3:
                    $(".member-table tbody tr").slideUp();
                    $(".member-table tbody td.nogroup").parent().slideDown();
                    break;
                default :
                    $(".member-table tbody tr").slideDown();
                    break;
            }
            $(".member-filter li.current_foucus").removeClass("current_foucus");
            $(this).addClass("current_foucus");
        })

        var search_cache = [];
        //人员选择
        $("#inputGroupAdmin").atwho({
            at: "",
            alias: "at-users",
            attype: 1,
            search_key: "userName",
            tpl: "<li data-name='${'$'}{userName}' data-id='${'$'}{id}' title='${'$'}{userRemark}'><span class='atwho-li'>${'$'}{userName}</li>",
            limit: 10,
            callbacks: {
                remote_filter: function (query, render_view) {
                    var thisVal = query;
                    var self = $(this);
                    if (!self.data('active') && thisVal.length >= 1) {
                        self.data('active', true);
                        var cached = search_cache[thisVal];
                        if (typeof cached == "object") {
                            render_view(cached);
                            self.data('active', false);
                        } else {
                            if (self.xhr) {
                                self.xhr.abort();
                            }
                            self.xhr = $.ajax({
                                url: "${ctx}/party/searchnogroupuser",
                                data: { "userName": thisVal },
                                dataType: "json",
                                type: "POST",
                                success: function (res) {
                                    if (res.status == 0) {
                                        search_cache[thisVal] = res.data;

                                        render_view(res.data);
                                        self.data('active', false);
                                    }
                                },
                                error: function (data, status, e) {
                                    sf.sf_Message("查询用户JS错误：" + e + ",请刷新页面重试,抱歉！", 2);
                                }
                            });
                        }
                    }
                }
            }
        }).on('inserted-at-users.atwho', function (e, el) {
            var id = el.data("id");
            var name = el.data("name");

            $(this).val(name).data("id",id);
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