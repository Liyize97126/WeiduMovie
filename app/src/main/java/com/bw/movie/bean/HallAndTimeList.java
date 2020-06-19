package com.bw.movie.bean;

/**
 * 影厅数据Bean类
 * 李易泽
 * 20200618
 */
public class HallAndTimeList {
    private String beginTime;
    private String endTime;
    private double fare;
    private int hallId;
    private int id;
    private String screeningHall;
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public double getFare() {
        return fare;
    }
    public void setFare(double fare) {
        this.fare = fare;
    }
    public int getHallId() {
        return hallId;
    }
    public void setHallId(int hallId) {
        this.hallId = hallId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getScreeningHall() {
        return screeningHall;
    }
    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }
}
