package com.bw.movie.bean;

import java.util.List;

/**
 * 电影评论接口内数据类
 * 李易泽
 * 20200604
 */
public class MovieDetailCommentsList {
    private String commentContent;
    private String commentHeadPic;
    private int commentId;
    private long commentTime;
    private int commentUserId;
    private String commentUserName;
    private int greatNum;
    private int isGreat;
    private int replyNum;
    private int score;
    private List<String> replyHeadPic;
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
    public int getIsGreat() {
        return isGreat;
    }
    public void setIsGreat(int isGreat) {
        this.isGreat = isGreat;
    }
    public int getReplyNum() {
        return replyNum;
    }
    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public List<String> getReplyHeadPic() {
        return replyHeadPic;
    }
    public void setReplyHeadPic(List<String> replyHeadPic) {
        this.replyHeadPic = replyHeadPic;
    }
}
