/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.tmlk.framework.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by laiguoqiang on 15/5/13.
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 4593794859773668358L;

    @Override
    public void init() throws ServletException {

        // 设置wac
        WebContext.init(this.getServletContext());

    }
}
