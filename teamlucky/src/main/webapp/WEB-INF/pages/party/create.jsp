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

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/datepicker.css" />
    <title>发起活动 - 校缘派</title>
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
                <jsp:param value="partycreate" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="container">

    <div class="jumbotron">
        <h2>创建活动</h2>

        <form id="partyCreateForm" action="" onsubmit="return false;">
            <div class="form-group has-feedback">
                <label class="control-label" for="partyName">活动名称</label>
                <input type="text" class="form-control" name="partyName" id="partyName" placeholder="活动名称">
                <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
            </div>
            <div class="form-group has-feedback">
                <label class="control-label" for="partyRemark">活动简介</label>
                <textarea class="form-control" rows="3" name="partyRemark" id="partyRemark"
                          placeholder="活动简介"></textarea>
                <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
            </div>
            <div class="form-group has-feedback">
                <label class="control-label" for="partyCode">识别码</label>
                <input type="text" class="form-control" name="partyCode" id="partyCode" placeholder="活动识别码，活动用户的用户名前缀">
                <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
            </div>
            <div class="form-group">
                <label class="control-label" for="partyCode">封面</label>
                <input type="file" class="form-control" name="file" id="partyCover" placeholder="文件路径...">
                <div class="preview-avatar"><img src=""></div>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="public"> 公共活动
                </label>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="group" checked> 是否分组，这个属性本次设定，不能修改
                </label>
            </div>
            <div class="checkbox for-group">
                <label>
                    <input type="checkbox" id="autoBuild" checked> 允许自行组队
                </label>
            </div>
            <div class="form-group for-group has-feedback">
                <label class="control-label" for="memberMin">每组最少人数</label>
                <input type="number" class="form-control" name="memberMin" min="0" id="memberMin" placeholder="每组最少人数">
                <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
            </div>
            <div class="form-group for-group has-feedback">
                <label class="control-label" for="memberMax">每组最多人数</label>
                <input type="number" class="form-control" name="memberMax" min="0" id="memberMax" placeholder="每组最多人数">
                <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
            </div>
            <div class="form-group for-group has-feedback">
                <label class="control-label" for="endTime">分组截止日期</label>
                <input type="text" class="form-control" name="endTime" id="endTime" placeholder="分组截止日期">
                <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
            </div>

            <button id="js-create" type="submit" class="btn btn-info">创建</button>
        </form>

    </div>

</div>

<div style="display: none;" id="photo_popup">
    <div id="edit-photo" style="height: 390px;">
        <div class="photo">
            <img src="" alt="" />
        </div>
        <div class="preview">
            <img src="" alt="" />
        </div>
        <div class="btn-div">
            <a id="js-save" href="#" class="button button--secondary" data-method="post" rel="nofollow">确定</a>
        </div>
    </div>
</div>
<!-- /container -->

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
<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>

<script type="text/javascript" src="${ctx}/resource/plugins/datetime/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/plugins/datetime/locales/bootstrap-datepicker.zh-CN.js"></script>

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script type="text/javascript">
    $(function () {
        $('#endTime').datepicker({
            autoclose: true,
            format: "yyyy-mm-dd",
            language: 'zh-CN',
            startDate: "today"
        });

        var validator = $('#partyCreateForm').validate({
            errorElement: 'span', //错误的html元素
            errorClass: 'help-block', // 错误的class样式
            focusInvalid: false,
            focusCleanup: true, //移进来，把错误消掉
            onsubmit: false,
            onfocusout: function (element) {
                $(element).valid();
            },
            rules: {
                "partyName": {
                    required: true
                },
                "partyRemark": {
                    required: true
                },
                "partyCode": {
                    required: true,
                    alphanumber: true,
                    minlength: 2,
                    remote:{
                        url :"${ctx}/party/checkPartyCode",
                        type:'post',
                        data:{
                            partyCode : function(){
                                return $("input[name='partyCode']").val();
                            }
                        }
                    }
                },
                "memberMin": {
                    required: true,
                    digits: true
                },
                "memberMax": {
                    required: true,
                    digits: true
                },
                "endTime": {
                    date: true
                }
            },
            messages: {
                "partyName": {
                    required: "活动名称不能为空"
                },
                "partyRemark": {
                    required: "活动描述不能为空"
                },
                "partyCode": {
                    required: "活动识别码不能为空，且只能是字母或数字，用于创建活动用户名的前缀.",
                    minlength: "识别码最少是2位有效字母或数字",
                    alphanumber: "活动识别码只能包含字母和数字",
                    remote: "活动识别码已存在，请重填"
                },
                "memberMin": {
                    required: "小组最少人数不能为空，无限制请输入0",
                    digits: "只能填写数字"
                },
                "memberMax": {
                    required: "小组最多人数不能为空，无限制请输入0",
                    digits: "只能填写数字"
                },
                "endTime": {
                    date: "日期格式不正确"
                }
            },
            invalidHandler: function (event, validator) {
                //总体错误，就来执行这
            },
            highlight: function (element) {
                $(element).closest('.form-group').addClass('has-error');
                $(element).closest('.form-group').find(".glyphicon").addClass("glyphicon-remove").removeClass("hide");
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error').addClass("has-success");
                label.closest('.form-group').find(".glyphicon").addClass("glyphicon-ok").removeClass("hide glyphicon-remove");
                label.remove();
            },
            errorPlacement: function (error, element) {
                element.after(error);
            }
        });

        $(document).on("click", "#js-create", function () {
            var isSuc = validator.form();
            if (!isSuc) {
                layer.msg("有错误项，还不能提交", {icon: 5, offset: '110px'});
                return false;
            }

            var pName = $("#partyName").val();
            var pRamark = $("#partyRemark").val();
            var pCode = $("#partyCode").val();
            var pCover = "";
            if($("#partyCover").data("img") != undefined){
                pCover = $("#partyCover").data("img").replace("resource/","");
            }
            var pIsGroup = $("#group").prop("checked");
            var pIsPublic = $("#public").prop("checked");
            var pIsAuto = $("#autoBuild").prop("checked");
            var pMemberMin = $("#memberMin").val();
            var pMemberMax = $("#memberMax").val();
            var pEndTime = $("#endTime").val();
            var postData = {
                partyName: pName,
                partyRemark: pRamark,
                partyCode: pCode,
                partyCover: pCover,
                isGroup: pIsGroup,
                isPublic: pIsPublic,
                isCustomBuild: pIsAuto,
                memberNumMin: pMemberMin,
                memberNumMax: pMemberMax
            };

            if (pEndTime != "")
                postData.buildEndTime = new Date(pEndTime);

            $.ajax({
                url: "${ctx}/party/doCreate",
                data: postData,
                type: "Post",
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    layer.closeAll('loading');
                    if (data.status == 0) {
                        layer.msg("创建成功，马上去设置吧", {icon: 6, offset: '110px'}, function () {
                            window.location.href = "${ctx}/party/conf";
                        })
                    } else {
                        layer.msg(data.message, {icon: 5, offset: '110px'});
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.closeAll('loading');
                    layer.msg("JS错误，Chrome浏览器最佳", {icon: 5, offset: '110px'});

                },
                complete: function (XHR, TS) {

                },
                beforeSend: function (XHR) {
                    layer.load(2);
                }
            });
        }).on("click", "#group", function () { //切换是否分组
            if ($(this).prop("checked")) {
                $(".for-group").slideDown();
            } else {
                $(".for-group input").val("");
                $(".for-group").slideUp();
            }
        }).on("change", "#partyCover", function () {
            var ths = $(this);
            $.ajaxFileUpload({
                url: "${ctx}/avatar/upload",
                fileElementId: $(this).attr("id"),
                secureuri:false,
                dataType: "text",
                data:{type:2,fileId:"test"},
                type: "post",
                success: function (res) {
                    layer.closeAll();

                    var resStr = $(res).text();
                    var bak = JSON.parse(resStr);
                    bak = JSON.parse(bak);
                    if (bak.status == 0) {
                        $("#edit-photo .photo img").attr("src", "${ctx}/" + bak.data);
                        $("#edit-photo .preview img").attr("src", "${ctx}/" + bak.data);
                        $('#edit-photo .photo img').imgAreaSelect({x1: 0, y1: 0, x2: 80, y2: 80, selectionOpacity: 0.2, aspectRatio: '1:1', onSelectChange: preview, zIndex: 10000, persistent: true
                        });
                        $("#edit-photo #js-save").attr({ "scale": "1", "x1": "0", "y1": "0", "x2": "80", "y2": "80" }).data("path",bak.data);
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
        }).on("click","#edit-photo #js-save",function () {
            avatarResize();
            return false;
        });

        //图片加载完成之后
        $("#edit-photo .photo img").load(function () {
            $("#photo_popup").show();

            $img = $("#edit-photo .photo img");
            var imgW = $img.width();
            var imgH = $img.height();
            $("#edit-photo").width(imgW+140);

            layer.open({
                zIndex: 1000,
                type: 1,
                title: false,
                scrollbar: false,
                area: ['500px', '390px'],
                content: $("#edit-photo"),
                success: function(layero, index){
                    setTimeout(function(){
                        var initOffset = $img.offset();

                        var imgW = $img.width();
                        var imgH = $img.height();

                        $(".imgareaselect-selection").parent("div").css({ "top": initOffset.top, "left": initOffset.left });

                        $(".imgareaselect-outer").each(function(indx) {
                            if(indx<2)
                                $(this).css({"top":initOffset.top,"left":initOffset.left});
                            else if(indx == 2)
                                $(this).css({"top":initOffset.top,"left":initOffset.left+80,"width": (imgW - 80), "height": imgH});
                            else
                                $(this).css({"top":initOffset.top+80,"left":initOffset.left,"width": 80, "height": (imgH - 80) });
                        });
                    },500);
                },
                end:function(){
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
        $("#edit-photo #js-save").attr({ "scale": scaleX, "x1": selection.x1, "y1": selection.y1, "x2": selection.x2, "y2": selection.y2 });
    }

    //确定所选区域
    function avatarResize() {
        var obj = $("#edit-photo #js-save");
        //需裁剪的头像 和 当前头像
        var src = obj.data("path");

        var dataToSend = { filePath: src,type:2, x1: obj.attr('x1'), y1: obj.attr('y1'), x2: obj.attr('x2'), y2: obj.attr('y2') };
        $.ajax({
            url: "${ctx}/avatar/cut",
            data: dataToSend,
            dataType: "json",
            type: "POST",
            success: function (res) {
                if (res.status == 0) {
                    layer.closeAll();

                    layer.msg("裁剪图片成功",{icon:6,offset:'110px'});
                    $(".preview-avatar").slideDown();
                    $(".preview-avatar img").attr("src","${ctx}/"+res.data);
                    $("#partyCover").data("img",res.data);
                }
                else
                    layer.msg(res.Msg,{icon:5,offset:'110px'});
            },
            error: function (data, status, e) {
                layer.msg("裁剪图片失败",{icon:5,offset:'110px'});
            },
            complete: function (data, status) {
                layer.closeAll();
            }
        });
    }
</script>

</body>
</html>