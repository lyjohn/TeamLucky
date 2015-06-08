<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/core/include.jsp" %>

<div class="hovercard-main clearfix">
    <div class="hovercard-pic">
        <div class="avatar component">
            <img src="/tmlk/avatar/user/2/<c:out value='${user.id}'></c:out>" class="avatar-thumb" alt="">
        </div>
    </div>
    <div class="hovercard-details" data-id="${user.id}">
        <p class="hovercard-title">
            <span class="hovercard-title-link">${user.userName}</span>
            <span class="text-lasttime">
                <c:if test="${empty user.lastLoginTime}">
                    从未登录过
                </c:if>
                <c:if test="${!empty user.lastLoginTime}">
                    <fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                </c:if>
            </span>
        </p>

        <p class="hovercard-subtitle">
            <c:if test="${empty user.userRemark}">
                <span>这家伙空白得像一张白纸.......</span>
            </c:if>
            <c:if test="${!empty user.userRemark}">
                <span>${user.userRemark}</span>
            </c:if>
        </p>
        <c:if test="${!empty user.tel}">
            <div class="hover-info-div clearfix">
                <div class="hover-info-title">电话</div>
                <div class="hover-info-value">
                    <span>${user.tel}</span>
                </div>
            </div>
        </c:if>
        <c:if test="${!empty user.qq}">
            <div class="hover-info-div clearfix">
                <div class="hover-info-title">QQ</div>
                <div class="hover-info-value">
                    <span>${user.qq}</span>
                </div>
            </div>
        </c:if>

        <c:if test="${!empty user.weiXin}">
            <div class="hover-info-div clearfix">
                <div class="hover-info-title">微信</div>
                <div class="hover-info-value">
                    <span>${user.weiXin}</span>
                </div>
            </div>
        </c:if>
        <c:if test="${!empty user.email}">
            <div class="hover-info-div clearfix">
                <div class="hover-info-title">邮箱</div>
                <div class="hover-info-value">
                    <span><a href="mailto:${user.email}">${user.email}</a></span>
                </div>
            </div>
        </c:if>
    </div>
</div>

<c:if test="${user.groupId == 0}">
    <div class="hovercard-action-buttons clearfix">
        <div class="follow-links-container">
            <a data-id="${user.id}" class="button button_inviteuser" href="javascrit:void(0);">邀 请</a>
        </div>
    </div>
</c:if>
