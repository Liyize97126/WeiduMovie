package com.bw.movie.bean;

/**
 * 推荐影院、附近影院数据类
 * 李易泽
 * 20200530
 */
public class RecommendCinemasList {
    private String address;
    private int commentTotal;
    private double distance;
    private int followCinema;
    private long id;
    private String logo;
    private String name;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getCommentTotal() {
        return commentTotal;
    }
    public void setCommentTotal(int commentTotal) {
        this.commentTotal = commentTotal;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public int getFollowCinema() {
        return followCinema;
    }
    public void setFollowCinema(int followCinema) {
        this.followCinema = followCinema;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
