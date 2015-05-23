package com.tmlk.framework.session;

/**
 * Created by laiguoqiang on 15/5/20.
 */
public class SessionUser {

    /*
    Session用户的类型  1：系统用户 2：活动用户
     */
    private int userType;

    /*
    Session的Attribute Key, 是系统用户时，为系统用户ID，是活动用户时是活动用户ID  这两个用户ID可能重复吗？UUID应该够大吧
     */
    private String sessionKey;

    private String sysUserId;

    private String sysUserName;

    private String sysUserAvatar;

    private String partyUserId;

    private String partyUserName;

    private String partyUserAvatar;

    private Long partyId;

    private Long groupId;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getSysUserAvatar() {
        return sysUserAvatar;
    }

    public void setSysUserAvatar(String sysUserAvatar) {
        this.sysUserAvatar = sysUserAvatar;
    }

    public String getPartyUserId() {
        return partyUserId;
    }

    public void setPartyUserId(String partyUserId) {
        this.partyUserId = partyUserId;
    }

    public String getPartyUserName() {
        return partyUserName;
    }

    public void setPartyUserName(String partyUserName) {
        this.partyUserName = partyUserName;
    }

    public String getPartyUserAvatar() {
        return partyUserAvatar;
    }

    public void setPartyUserAvatar(String partyUserAvatar) {
        this.partyUserAvatar = partyUserAvatar;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
