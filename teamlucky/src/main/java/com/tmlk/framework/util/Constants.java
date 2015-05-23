package com.tmlk.framework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LaiGuoqiang on 2015/4/18.
 */
public class Constants {

    public static final String SESSION_USER = "session_user";


    public static final int DEFAULT_PAGE_SIZE = 20;

    //Integer 表示Map的Value 是需要存储在数据库的logAction字段,String 表示Map的Key 是注解的Description
    //这儿做统一的管理，不要把编号重复了 代码中 不会用到这个
    public static final Map<Integer,String> SYS_LOG_MAP = new HashMap<Integer,String>();

    static {
        //用户模块
        SYS_LOG_MAP.put(101,"登录系统");
        SYS_LOG_MAP.put(102,"退出系统");
        SYS_LOG_MAP.put(103,"注册系统用户");

        SYS_LOG_MAP.put(201,"创建活动");
        SYS_LOG_MAP.put(201,"创建活动用户");

        SYS_LOG_MAP.put(301,"创建活动小组");
    }

    public static final Map<Integer,String> LOGIN_RESULT_MAP = new HashMap<Integer,String>();
    static {
        LOGIN_RESULT_MAP.put(1,"系统用户登录成功");
        LOGIN_RESULT_MAP.put(2,"活动用户登录成功");
        LOGIN_RESULT_MAP.put(3,"系统用户不存在");
        LOGIN_RESULT_MAP.put(4,"活动用户不存在");
        LOGIN_RESULT_MAP.put(5,"登录密码不正确");
        LOGIN_RESULT_MAP.put(6,"活动用户被禁用，请联系活动组织者");
    }
}
