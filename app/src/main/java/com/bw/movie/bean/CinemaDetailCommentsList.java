package com.bw.movie.bean;

import java.util.List;

/**
 * 影院评论数据Bean类
 * 李易泽
 * 20200612
 */
public class CinemaDetailCommentsList {
    private String commentContent;
    private String commentHeadPic;
    private int commentId;
    private long commentTime;
    private int commentUserId;
    private String commentUserName;
    private int greatNum;
    private int hotComment;
    private int isGreat;
    private List<String> greatHeadPic;
    public String getCommentContent() {
        return commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public String getCommentHeadPic() {
        return commentHeadPic;
    }
    public void setCommentHeadPic(String commentHeadPic) {
        this.commentHeadPic = commentHeadPic;
    }
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
    public long getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }
    public int getCommentUserId() {
        return commentUserId;
    }
    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }
    public String getCommentUserName() {
        return commentUserName;
    }
    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
    public int getGreatNum() {
        return greatNum;
    }
    public void setGreatNum(int greatNum) {
        this.greatNum = greatNum;
    }
    public int getHotComment() {
        return hotComment;
    }
    public void setHotComment(int hotComment) {
        this.hotComment = hotComment;
    }
    public int getIsGreat() {
        return isGreat;
    }
    public void setIsGreat(int isGreat) {
        this.isGreat = isGreat;
    }
    public List<String> getGreatHeadPic() {
        return greatHeadPic;
    }
    public void setGreatHeadPic(List<String> greatHeadPic) {
        this.greatHeadPic = greatHeadPic;
    }
}
