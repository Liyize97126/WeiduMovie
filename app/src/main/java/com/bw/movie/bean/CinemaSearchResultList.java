package com.bw.movie.bean;

/**
 * 影院搜索结果数据Bean类
 * 李易泽
 * 20200613
 */
public class CinemaSearchResultList {
    private String address;
    private int commentTotal;
    private int distance;
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
    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
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
