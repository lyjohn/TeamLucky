package com.tmlk.framework.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tmlk.framework.session.SessionUser;
import org.springframework.util.StringUtils;

import com.tmlk.framework.servlet.WebContext;
import com.tmlk.framework.util.Constants;

/**
 * Created by laiguoqiang on 15/5/13.
 * 此Filter用于在用户访问时，判断用户时候是非法访问
 * 主要判断用户的Session是否存在，如果存在，则正常访问，如果不存在，则跳转到登录页面
 */

public class CheckLoginFilter implements Filter {

    private static final String INIT_PARAM_DELIMITERS = ",; \t\n";

    private static final String INIT_PARAM_EXCLUDE_URL = "excludeUrl";

    private Set<String> excludeUrlSet = new HashSet<String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        String excludeUrl = filterConfig.getInitParameter(INIT_PARAM_EXCLUDE_URL);
        if (excludeUrl != null) {
            String[] exculdeUrlArray = StringUtils.tokenizeToStringArray(excludeUrl, INIT_PARAM_DELIMITERS);
            if (exculdeUrlArray != null) {
                for (String url : exculdeUrlArray) {
                    excludeUrlSet.add(url.trim());
                }
            }
        }
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        request.setAttribute("ctx", WebContext.contextPath);

        SessionUser user =(SessionUser)session.getAttribute(Constants.SESSION_USER);

        String path = request.getServletPath();

        if (user == null && (!excludeUrlSet.contains(path)) &&(!path.startsWith("/resource/")) &&(!path.startsWith("/errors/")) &&(!path.startsWith("/avatar/")) ) {
            String xReq = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equalsIgnoreCase(xReq)) {
                response.setHeader("sessioninvalid", "true");
            } else {
                response.sendRedirect(WebContext.contextPath + "/errors/error/1");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }

}