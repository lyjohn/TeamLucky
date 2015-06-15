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

  <title>创建小组 - 校缘派</title>
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

<div class="container">

  <div class="jumbotron">
    <h2>创建团队/小组</h2>

    <form id="groupCreateForm" action="" onsubmit="return false;">
      <div class="form-group has-feedback">
        <label class="control-label" for="groupName">小组名称</label>
        <input type="text" class="form-control" name="groupName" id="groupName" placeholder="小组名称">
        <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
      </div>
      <div class="form-group has-feedback">
        <label class="control-label" for="groupRemark">小组简介</label>
        <textarea class="form-control" rows="3" name="groupRemark" id="groupRemark"
                  placeholder="活动简介"></textarea>
        <span class="glyphicon form-control-feedback hide" aria-hidden="true"></span>
      </div>
      <div class="form-group">
        <label class="control-label" for="groupCover">封面</label>
        <input type="file" class="form-control" name="file" id="groupCover" placeholder="文件路径...">
        <div class="preview-avatar"><img src=""></div>
      </div>
      <div class="checkbox">
        <label>
          <input type="checkbox" id="autoJoin" checked> 自动加入：允许他人自动加入，不需要创建者审核
        </label>
      </div>
      <div class="checkbox">
        <label>
          <input type="checkbox" id="public" checked> 资源共享：允许其他组成员 查看 本小组内容
        </label>
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

<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>

<script type="text/javascript">
  $(function () {
    var validator = $('#groupCreateForm').validate({
      errorElement: 'span', //错误的html元素
      errorClass: 'help-block', // 错误的class样式
      focusInvalid: false,
      focusCleanup: true, //移进来，把错误消掉
      onsubmit: false,
      onfocusout: function (element) {
        $(element).valid();
      },
      rules: {
        "groupName": {
          required: true
        },
        "groupRemark": {
          required: true
        }
      },
      messages: {
        "groupName": {
          required: "小组名称不能为空"
        },
        "groupRemark": {
          required: "小组简介不能为空"
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

      var pName = $("#groupName").val();
      var pRamark = $("#groupRemark").val();
      var pCover = "";
      if($("#groupCover").data("img") != undefined){
        pCover = $("#groupCover").data("img").replace("resource/","");
      }
      var pIsPublic = $("#public").prop("checked");
      var pIsAuto = $("#autoJoin").prop("checked");

      var postData = {
        groupName: pName,
        groupRemark: pRamark,
        groupCover: pCover,
        isCustomJoin: pIsAuto,
        isSourcePublic: pIsPublic
      };

      $.ajax({
        url: "${ctx}/group/doCreate",
        data: postData,
        type: "Post",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          layer.closeAll('loading');
          if (data.status == 0) {
            layer.msg("创建成功，马上去设置吧", {icon: 6, offset: '110px'}, function () {
              window.location.href = "${ctx}/group/index";
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
    }).on("change", "#groupCover", function () {
      var ths = $(this);
      $.ajaxFileUpload({
        url: "${ctx}/avatar/upload",
        fileElementId: $(this).attr("id"),
        secureuri:false,
        dataType: "text",
        data:{type:3,fileId:"test"},
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

    var dataToSend = { filePath: src,type:3, x1: obj.attr('x1'), y1: obj.attr('y1'), x2: obj.attr('x2'), y2: obj.attr('y2') };
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
          $("#groupCover").data("img",res.data);
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