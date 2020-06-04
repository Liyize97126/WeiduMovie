package com.bw.movie.bean;

/**
 * 用户信息数据类
 * 李易泽
 * 20200604
 */
public class UserInfo {
    private String email;
    private String headPic;
    private int id;
    private long lastLoginTime;
    private String nickName;
    private int sex;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHeadPic() {
        return headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
}
