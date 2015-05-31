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
    <!-- start light_box -->
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/jquery.fancybox.css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/fwslider.css" media="all">

    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/blue.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/resource/css/fwslider.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/allinone_carousel.css" />

    <link rel="stylesheet" type="text/css" href="${ctx}/resource/css/datepicker.css" />

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
            </div>
            <jsp:include page="../shared/_header.jsp">
                <jsp:param value="1" name="type"/>
                <jsp:param value="null" name="cur"/>
            </jsp:include>
            <div class="clearfix"></div>
        </div>
    </div>
</div>

<div class="container">
    <div class="col-md-8">
        <div class="portfolio_main">
            <ul id="filters" class="clearfix">
                <li>
                    <span class="filter active" data-filter="member">所有成员</span>
                </li>
                <li>
                    <span class="filter" data-filter="news">新闻通知</span>
                </li>
                <li>
                    <span class="filter" data-filter="result">文档展示</span>
                </li>
                <li>
                    <span class="filter" data-filter="talk ">讨论区</span>
                </li>
            </ul>
            <div id="portfoliolist">
                <div class="portfolio logo1" data-cat="logo">
                    <div class="portfolio-wrapper">
                        <div class="col-md-7 pull-right m-info">
                            <h4>姓名</h4>
                            <p>个人描述信息</p>
                        </div>
                        <img src="${ctx}/resource/avatar/default/user.png" alt="" class="img-responsive"/>
                        <h5>联系方式</h5>
                        <hr/>
                        <p>邮箱</p>

                        <p>手机</p>

                        <p>微信</p>

                        <p>QQ</p>
                        <div class="label">
                            <div class="label-text">
                                <span><input type="submit" value="邀请" /></span>
                            </div>
                            <div class="label-bg"></div>
                        </div>
                    </div>
                </div>
                <div class="portfolio logo1" data-cat="logo">
                    <div class="portfolio-wrapper">
                        <div class="col-md-7 pull-right m-info">
                            <h4>姓名</h4>
                            <p>个人描述信息</p>
                        </div>
                        <img src="${ctx}/resource/avatar/default/user.png" alt="" class="img-responsive"/>
                        <h5>联系方式</h5>
                        <hr/>
                        <p>邮箱</p>

                        <p>手机</p>

                        <p>微信</p>

                        <p>QQ</p>
                        <div class="label">
                            <div class="label-text">
                                <span><input type="submit" value="邀请" /></span>
                            </div>
                            <div class="label-bg"></div>
                        </div>
                    </div>
                </div>
                <div class="portfolio logo1" data-cat="logo">
                    <div class="portfolio-wrapper">
                        <div class="col-md-7 pull-right m-info">
                            <h4>姓名</h4>
                            <p>个人描述信息</p>
                        </div>
                        <img src="${ctx}/resource/avatar/default/user.png" alt="" class="img-responsive"/>
                        <h5>联系方式</h5>
                        <hr/>
                        <p>邮箱</p>

                        <p>手机</p>

                        <p>微信</p>

                        <p>QQ</p>
                        <div class="label">
                            <div class="label-text">
                                <span><input type="submit" value="邀请" /></span>
                            </div>
                            <div class="label-bg"></div>
                        </div>
                    </div>
                </div>
                <div class="portfolio logo1" data-cat="logo">
                    <div class="portfolio-wrapper">
                        <div class="col-md-7 pull-right m-info">
                            <h4>姓名</h4>
                            <p>个人描述信息</p>
                        </div>
                        <img src="${ctx}/resource/avatar/default/user.png" alt="" class="img-responsive"/>
                        <h5>联系方式</h5>
                        <hr/>
                        <p>邮箱</p>

                        <p>手机</p>

                        <p>微信</p>

                        <p>QQ</p>
                        <div class="label">
                            <div class="label-text">
                                <span><input type="submit" value="邀请" /></span>
                            </div>
                            <div class="label-bg"></div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="clearfix"></div>
            <ul class="pagination">
                <li><a href="#">上一页</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li class="cau_hide"><a href="#">4</a></li>
                <li class="cau_hide"><a href="#">5</a></li>
                <li class="cau_hide"><a href="#">6</a></li>
                <li class="cau_hide"><a href="#">7</a></li>
                <li><a href="#">下一页</a></li>
            </ul>
        </div>
    </div>
    <div class="col-md-4 blog_right">
        <ul class="widgets">
            <li class="upcoming-widget">
                <h4>已建团队</h4>

                <div class="container">
                    <ul class="slides">
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                        <li><a href="#"><span class="icon"><img src="images/clock.png" alt=""/></span> <span
                                class="right">´´½¨ÈË£ºÄ³Ä³Ä³</span> <strong>¶þ¼¶¹¤³ÌÊµ¼ù</strong></a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>

</div>

<jsp:include page="../shared/_footer.jsp" />


<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/bootstrap.min.js"></script>


<script type="text/javascript" src="${ctx}/resource/js/menu.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/fliplightbox.min.js"></script>
<script type="text/javascript">
    $('body').flipLightBox()
</script>

<script type="text/javascript" src="${ctx}/resource/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.mixitup.min.js"></script>
<script type="text/javascript">
    $(function() {

        var filterList = {

            init : function() {

                // MixItUp plugin
                // http://mixitup.io
                $('#portfoliolist').mixitup({
                    targetSelector : '.portfolio',
                    filterSelector : '.filter',
                    effects : ['fade'],
                    easing : 'snap',
                    // call the hover effect
                    onMixEnd : filterList.hoverEffect()
                });

            },

            hoverEffect : function() {

                // Simple parallax effect
                $('#portfoliolist .portfolio').hover(function() {
                    $(this).find('.label').stop().animate({
                        bottom : 0
                    }, 200, 'easeOutQuad');
                    $(this).find('img').stop().animate({
                        top : -30
                    }, 500, 'easeOutQuad');
                }, function() {
                    $(this).find('.label').stop().animate({
                        bottom : -40
                    }, 200, 'easeInQuad');
                    $(this).find('img').stop().animate({
                        top : 0
                    }, 300, 'easeOutQuad');
                });

            }
        };

        // Run the show!
        filterList.init();

    });
</script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.fancybox-1.2.1.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("div.fancyDemo a").fancybox();
    });
</script>

<script type="text/javascript" src="${ctx}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/css3-mediaqueries.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/fwslider.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/allinone_carousel.js"></script>
<script>
    jQuery(function() {

        jQuery('#allinone_carousel_charming').allinone_carousel({
            skin: 'charming',
            width: 990,
            height: 454,
            responsive:true,
            autoPlay: 3,
            resizeImages:true,
            autoHideBottomNav:false,
            showElementTitle:false,
            verticalAdjustment:50,
            showPreviewThumbs:false,
            //easing:'easeOutBounce',
            numberOfVisibleItems:5,
            nextPrevMarginTop:23,
            playMovieMarginTop:0,
            bottomNavMarginBottom:-10
        });
    });
</script>

<script type="text/javascript">


    $(function () {


    });
</script>

</body>
</html>