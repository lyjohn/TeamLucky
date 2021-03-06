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
    <title>个人设置 - 校缘派</title>
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
                <jsp:param value="userset" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="main_bg" style="background-color: #fff;">
    <div class="persional_property">
        <div class="person_info_con"><i class="fa fa-edit fa-2x person-info-edit"></i><a name="M_base"></a>
            <dl class="person-photo">
                <dt>
                    <a href="javascript:;">
                        <img src="${ctx}/avatar/user/2/${model.partyUserExt.id}" class="header">
	                	<span class="edit_person_pic" style="overflow:hidden;">
	                		<input id="user_avatar" name="file" type="file"
                                   style="opacity:0; width:200px;height:200px;position:absolute;left:-50px;"/>
	                	</span>
                    </a>
                </dt>
            </dl>
            <dl class="person-info">
                <dt class="person-nick-name">
                    <span>${model.partyUserExt.userName}</span>
                    <span title="修改密码"><i class="fa fa-key password-edit" style="color:#999;cursor:pointer; margin-left:10px;"></i></span>
                </dt>
                <dd class="person-detail">
                    <span class="info_null">${model.partyUserExt.loginName}</span>
                    <span>|</span>
                    <c:if test="${empty model.partyUserExt.sex}">
                        <span class="info_null sex_view">未填写性别</span>
                    </c:if>
                    <c:if test="${!empty model.partyUserExt.sex}">
                        <span class="sex_view"><c:out value="${model.partyUserExt.sex}"></c:out></span>
                    </c:if>
                    <span>|</span>
                    <c:if test="${empty model.partyUserExt.birthDay}">
                        <span class="info_null birthday_view">未填写生日</span>
                    </c:if>
                    <c:if test="${!empty model.partyUserExt.birthDay}">
                        <span class="birthday_view"><fmt:formatDate value="${model.partyUserExt.birthDay}"
                                                                    pattern="yyyy-MM-dd"></fmt:formatDate></span>
                    </c:if>
                </dd>
                <dd class="person-sign">
                    <c:if test="${empty model.partyUserExt.userRemark}">
                        <span class="info_null">开始是一片空白...</span>
                    </c:if>
                    <c:if test="${!empty model.partyUserExt.userRemark}">
                        <span><c:out value="${model.partyUserExt.userRemark}"></c:out></span>
                    </c:if>
                </dd>
            </dl>

        </div>
    </div>

    <div class="persion_section">
        <div class="person_detail_tab">
            <ul>
                <li data-modal="tab" data-tab="myDetails" class="current_detail">联系方式</li>
                <li data-modal="tab" data-tab="myNews">我的动态</li>
                <li data-modal="tab" data-tab="myMessages">我的消息</li>
                <% if(sessionUser.getSysUserId() == null) {%>
                <li data-modal="btn"><a class="button button--secondary link_sysuser">关联系统帐号</a></li>
                <%}%>
            </ul>
        </div>
        <div class="aboutMe">
            <div nodetype="myDetails" nodeindex="my0" data-modal="tab-layer" class="myDetails current_content">
                <div class="mod_contact">
                    <a href="#" nodetype="contact-modify" class="modify fa fa-edit fa-2x"></a>
                    <ul class="clearfix">
                        <li><span class="li_title">电子邮箱：</span><span nodetype="mail" class="email mail" title=""><c:out
                                value="${model.partyUserExt.email}"></c:out></span></li>
                        <li><span class="li_title">手机号码：</span><span nodetype="mobile" class="mobile" title=""><c:out
                                value="${model.partyUserExt.tel}"></c:out></span></li>
                        <li><span class="li_title">QQ号码：</span><span nodetype="qq" class="qq" title="357431972"><c:out
                                value="${model.partyUserExt.qq}"></c:out></span></li>
                        <li><span class="li_title">微信号：</span><span nodetype="weixin" class="weixin"><c:out
                                value="${model.partyUserExt.weiXin}"></c:out></span></li>
                    </ul>
                </div>
            </div>
            <div nodetype="myNews" nodeindex="my2" data-modal="tab-layer" class="myNews">
                <div class="connection_title_con">
                    <!-- 显示我的操作日志 -->
                </div>
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

    <div class="pop_edit edit_password">
        <h3>修改密码</h3>
        <div class="context">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="inputOldPwd" class="col-sm-4 control-label">旧密码</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="inputOldPwd" name="inputOldPwd" placeholder="输入旧密码" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNewPwd" class="col-sm-4 control-label">新密码</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="inputNewPwd" name="inputNewPwd" placeholder="输入新密码，6位以上" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNewPwd" class="col-sm-4 control-label">新密码确认</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="inputReNewPwd" name="inputReNewPwd" placeholder="请重复新密码" />
                    </div>
                </div>
            </form>
        </div>
        <div class="success">
            <a class="js_show_pwd" href="#" style="background-color:#ddd;" rel="nofollow" data-show=false>显示密码</a>
            <a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">确定</a>
        </div>
    </div>
    <div class="pop_edit edit_intro">
        <h3>编辑简介</h3>
        <div style="background: #f3f3f3; border:1px solid #DCDCDC; margin-bottom:30px; padding:0 17px; color:#333; font-size:16px; line-height:30px;">
            <span>用户名 :</span><span style="color: #666666; margin-left:15px;"><c:out value="${model.partyUserExt.loginName}"></c:out></span>
        </div>
        <div class="context">
            <form id="form" nodetype="form-popup" class="form">
                <ul>
                    <li class="mp_dl"><em class="red">*</em><span>昵称：
                  <input name="nickname" nodetype="nickName" important="yes" type="text" placeholder="支持中文、英文、数字"
                         value="<c:out value='${model.partyUserExt.userName}'></c:out>" maxlen="20"
                         class="nick_name mp_field1">
                  </span></li>
                    <li><span class="mp_label">性别：
                    <c:if test="${model.partyUserExt.sex == '男'}">
                        <input name="gender" type="radio" value="男" class="radio_sex" checked><span>男</span>
                    </c:if>
                    <c:if test="${model.partyUserExt.sex != '男'}">
                        <input name="gender" type="radio" value="男" class="radio_sex"><span>男</span>
                    </c:if>
                    <c:if test="${model.partyUserExt.sex == '女'}">
                        <input name="gender" type="radio" value="女" class="radio_sex" checked><span>女</span>
                    </c:if>
                    <c:if test="${model.partyUserExt.sex != '女'}">
                        <input name="gender" type="radio" value="女" class="radio_sex"><span>女</span>
                    </c:if>
                    </li>
                    <li><span>生日：
                  <input name="birthday" nodetype="birthday" type="text" class="birthday mp_field1"
                         value="<fmt:formatDate value='${model.partyUserExt.birthDay}' pattern='yyyy-MM-dd'></fmt:formatDate>"></span>
                    </li>
                    <li>简述：
                        <textarea name="selfdesc" nodetype="selfdesc" placeholder="200字以内" maxlen="200"
                                  class="intro_info mp_wid" style="width:560px"><c:out
                                value='${model.partyUserExt.userRemark}'></c:out></textarea>
                    </li>
                </ul>
            </form>
        </div>
        <div class="success"><a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">确定</a>
        </div>
    </div>
    <div class="pop_edit login_sysuser">
        <h3>关联系统帐号</h3>
        <div class="context">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="inputSysUserName" class="col-sm-4 control-label">系统用户帐号</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="inputSysUserName" name="inputSysUserName" placeholder="系统登录帐号，没有_" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputSysUserpwd" class="col-sm-4 control-label">系统用户密码</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="inputSysUserpwd" name="inputSysUserpwd" placeholder="系统用户的密码" />
                    </div>
                </div>
            </form>
        </div>
        <div class="success">
            <a href="#" nodetype="cancel" class="button">取消</a>
            <a class="js_save button button--secondary" href="#" data-method="post" rel="nofollow">关联</a>
        </div>
    </div>
    <div class="pop_edit edit_contact">
        <h3>编辑联系方式</h3>
        <div class="context">
            <form class="form-horizontal">
                <div class="form-group">
                    <label for="inputEmail" class="col-sm-2 control-label">邮箱</label>

                    <div class="col-sm-10">
                        <input type="email" class="form-control" id="inputEmail" name="inputEmail" placeholder="电子邮箱"
                               value="<c:out value='${model.partyUserExt.email}'></c:out>"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputTel" class="col-sm-2 control-label">手机</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputTel" maxlength="11" name="inputTel" placeholder="手机号码"
                               value="<c:out value='${model.partyUserExt.tel}'></c:out>"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputQq" class="col-sm-2 control-label">QQ</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputQq" maxlength="11" name="inputQq" placeholder="QQ号"
                               value="<c:out value='${model.partyUserExt.qq}'></c:out>"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWeixin" class="col-sm-2 control-label">微信</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputWeixin" name="inputWeixin" placeholder="微信号"
                               value="<c:out value='${model.partyUserExt.weiXin}'></c:out>"/>
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

<script type="text/javascript" src="${ctx}/resource/plugins/datetime/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/datetime/locales/bootstrap-datepicker.zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script type="text/javascript">
    var layerLoadIndex = 0;
    $(function () {

        $(".birthday").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd",
            language: 'zh-CN'
        });

        $(document).on("click",".password-edit",function(){//修改密码
            $(".edit_password").show();
            var width = $(".edit_password").width();
            var height = $(".edit_password").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                offset: '110px',
                area: [width+'px', height+'px'],
                content: $(".edit_password")
            });
        }).on("click", ".modify", function () {//修改联系方式
            $(".edit_contact").show();
            var width = $(".edit_contact").width();
            var height = $(".edit_contact").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                area: [width + 'px', height + 'px'],
                content: $(".edit_contact")
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
        }).on("click",".link_sysuser",function(){//关联系统账户
            $(".login_sysuser").show();
            var width = $(".login_sysuser").width();
            var height = $(".login_sysuser").height();
            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                area: [width+'px', height+'px'],
                content: $(".login_sysuser")
            });
        }).on("click", ".pop_edit a[nodetype='cancel']", function () { //取消按钮
            layer.closeAll();
        }).on("click",".edit_password .js_show_pwd",function(){ //切换密码显示
            var shown = $(this).data("show");
            if(shown){
                $(".edit_password form input").attr("type","password");
                $(this).text("显示密码");
            }
            else{
                $(".edit_password form input").attr("type","text");
                $(this).text("隐藏密码");
            }
            $(this).data("show",!shown);
        }).on("click",".edit_password .js_save",function(){ //保存密码
            var oldpwd = $("#inputOldPwd").val();
            var newpwd = $("#inputNewPwd").val();
            var newRepwd = $("#inputReNewPwd").val();

            if(oldpwd.length < 6 || newpwd.length<6 || newRepwd<6){
                layer.msg("密码长度必须是6位以上",{icon:5,offset:'110px'});
                return false;
            }
            if(newpwd != newRepwd){
                layer.msg("两次输入密码不一致",{icon:5,offset:'110px'});
                return false;
            }

            $.ajax({
                url:"${ctx}/user/ppwd",
                dataType:"json",
                data:{oldPwd:oldpwd,newPwd:newpwd},
                type:"post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("修改密码成功",{icon:6,offset:'110px'});
                        $(".edit_password form input").val("");
                    }
                    else
                        layer.msg(res.message,{icon:5,offset:'110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("保存失败",{icon:5,offset:'110px'});
                }
            })
        }).on("click", ".edit_contact .js_save", function () { //保存联系方式
            var email = $("#inputEmail").val();
            var tel = $("#inputTel").val();
            var qq = $("#inputQq").val();
            var weixin = $("#inputWeixin").val();

            $.ajax({
                url: "${ctx}/user/pcontact",
                dataType: "json",
                data: {email: email, tel: tel, qq: qq, weiXin: weixin},
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("联系方式保存成功", {icon: 6, offset: '110px'});

                        $(".mod_contact .email").text(email);
                        $(".mod_contact .mobile").text(tel);
                        $(".mod_contact .qq").text(qq);
                        $(".mod_contact .weixin").text(weixin);
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
            var username = $(".nick_name").val();
            var sex = $(".edit_intro :radio").val();
            var birthday = $(".birthday").val();
            var userRemark = $(".edit_intro textarea").val();

            if (username == "") {
                layer.msg("昵称不能为空", {icon: 5, offset: "110px"});
                return false;
            }
            var postDate = {userName: username, sex: sex, userRemark: userRemark};
            if (birthday != "")
                postDate.birthDay = new Date(birthday);

            $.ajax({
                url: "${ctx}/user/pintro",
                dataType: "json",
                data: postDate,
                type: "post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("基本信息保存成功", {icon: 6, offset: '110px'});

                        $(".person-nick-name span").text(username);
                        $(".sex_view").text(sex).removeClass("info_null");

                        if (birthday != "")
                            $(".birthday_view").text(birthday).removeClass("info_null");
                        else
                            $(".birthday_view").text("未填写生日").addClass("info_null");

                        if (userRemark != "")
                            $(".person-sign span").text(userRemark).removeClass("info_null");
                        else
                            $(".person-sign span").text("开始是一片空白...").addClass("info_null");
                    }
                    else
                        layer.msg(res.message, {icon: 5, offset: '110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("保存失败", {icon: 5, offset: '110px'});
                }
            })
        }).on("click",".login_sysuser .js_save",function(){ //关联系统账户
            var suName = $("#inputSysUserName").val();
            var suPwd = $("#inputSysUserpwd").val();

            if(suName.indexOf("_") > -1){
                layer.msg("系统帐号没有下划线",{icon:5,offset:'110px'});
                return false;
            }
            if(suPwd.length<6){
                layer.msg("密码不能少于6位",{icon:5,offset:'110px'});
                return false;
            }

            $.ajax({
                url:"${ctx}/user/lksysuser",
                dataType:"json",
                data:{loginName:suName,loginPwd:suPwd},
                type:"post",
                success: function (res) {
                    if (res.status == 0) {
                        layer.closeAll();

                        layer.msg("关联成功",{icon:6,offset:'110px'});

                        $(".login_partyuser form input").val("");

                    }
                    else
                        layer.msg(res.message,{icon:5,offset:'110px'});
                },
                error: function (data, status, e) {
                    layer.closeAll();
                    layer.msg("关联失败",{icon:5,offset:'110px'});
                }
            })
        }).on("change", ".edit_person_pic input[type='file']", function () {
            var ths = $(this);
            $.ajaxFileUpload({
                url: "${ctx}/avatar/upload",
                fileElementId: $(this).attr("id"),
                secureuri: false,
                dataType: "text",
                data: {type: 1},
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

            if(nTab==undefined)
                return false;

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
            type: 1,
            user: 2,
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