<%@ page import="com.tmlk.framework.session.SessionUser" %>
<%@ page import="com.tmlk.framework.util.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>
<%
    SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(
            Constants.SESSION_USER);

    Boolean canC = (Boolean)request.getSession().getAttribute(Constants.SESSION_AUTOCREATE);
    boolean canCreate = false;
    if(canC != null)
        canCreate = canC;

    //1:活动界面 0:系统界面
    String type = request.getParameter("type");
    //-1无active
    String cur = request.getParameter("cur");
%>
<div class="h_menu">
    <a id="touch-menu" class="mobile-menu" href="#">菜单</a>
    <nav>
        <ul class="menu list-unstyled">
            <% if ("0".equals(type)) { %>
            <li class="js_home"><a href="${ctx}">首页</a></li>
            <% if (sessionUser != null) {
                if (sessionUser.getSysUserId() != null) { %>
            <li class="js_userset"><a href="${ctx}/user/sprofile">个人设置</a></li>
            <li class="js_partycreate"><a href="${ctx}/party/create">发起活动</a></li>
            <% } else {%>
            <li class="js_userset"><a href="${ctx}/user/pprofile">个人设置</a></li>
            <%}
            } else {
            %>
            <li class="hide js_userset"><a href="">个人设置</a></li>
            <li class="hide js_partycreate"><a href="">发起活动</a></li>
            <% } %>
            <li class="js_aboutus"><a href="javascript:void(0);">关于我们</a></li>
            <%if (sessionUser != null) {%>
            <li class="js_logout"><a href="${ctx}/logout">退出登录</a></li>
            <%}else{%>
            <li><a class="hide js_logout" href="${ctx}/logout">退出登录</a></li>
            <%}%>
            <%} else {%>
            <li class="js_acthome"><a href="${ctx}/party/index/<%=sessionUser.getPartyId()%>">活动首页</a></li>
            <li class="js_userset"><a href="${ctx}/user/pprofile">我的名片</a></li>
            <% if(sessionUser.getGroupId() == 0 && canCreate) {%>
            <li class="js_groupcreate"><a href="${ctx}/group/create">创建小组</a></li>
            <% } else if(sessionUser.getGroupId() > 0) {%>
            <li class="js_groupindex"><a href="${ctx}/group/index">我的团队</a></li>
            <% } %>
            <% if(sessionUser.isPartyAdmin()){%>
            <li class="js_partyconf"><a href="${ctx}/party/conf">管理活动</a></li>
            <% } %>
            <li class="js_logout"><a href="${ctx}/logout">退出登录</a></li>
            <%}%>
        </ul>
    </nav>
</div>

<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function(){
        var cur = "<%= cur %>";
        if(cur!="")
            $(".js_"+cur).addClass("activate");

        $(document).on("click",".js_aboutus",function(){
            var pos = $("#footer").position();
            var top = pos.top;
            $("html,body").animate({
                scrollTop: top
            }, 500);
        });
    })
</script>