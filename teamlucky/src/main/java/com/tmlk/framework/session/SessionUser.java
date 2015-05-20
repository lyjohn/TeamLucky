package com.tmlk.framework.session;

/**
 * Created by laiguoqiang on 15/5/20.
 */
public class SessionUser {

    /*
    Session用户的类型  1：系统用户 2：活动用户
     */
    private int userType;

    private Object user;

    private String userId;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
