package com.tmlk.framework.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Locale;

/**
 * Created by laiguoqiang on 15/5/13.
 */
public class WebContext {
    private static WebApplicationContext wac;

    public static String contextPath;

    private static final Locale locale = Locale.getDefault();

    public static void init(ServletContext sc) {
        wac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        String cp = sc.getContextPath();
        if (cp.startsWith("/")) {
            contextPath = cp;
        } else {
            contextPath = "/" + cp;
        }
    }

    public static WebApplicationContext getWac() {
        return wac;
    }
}
