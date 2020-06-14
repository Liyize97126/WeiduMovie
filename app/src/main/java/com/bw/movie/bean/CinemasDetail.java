package com.bw.movie.bean;

/**
 * 影院详情数据类
 * 李易泽
 * 20200612
 */
public class CinemasDetail {
    private String address;
    private String businessHoursContent;
    private int commentTotal;
    private int distance;
    private int followCinema;
    private long id;
    private String label;
    private String logo;
    private String name;
    private String phone;
    private String vehicleRoute;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getBusinessHoursContent() {
        return businessHoursContent;
    }
    public void setBusinessHoursContent(String businessHoursContent) {
        this.businessHoursContent = businessHoursContent;
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
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getVehicleRoute() {
        return vehicleRoute;
    }
    public void setVehicleRoute(String vehicleRoute) {
        this.vehicleRoute = vehicleRoute;
    }
}
