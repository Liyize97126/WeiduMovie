package com.bw.movie.bean;

/**
 * 登录信息数据类
 * 李易泽
 * 20200604
 */
public class LoginInfo {
    private String sessionId;
    private int userId;
    private UserInfo userInfo;
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
