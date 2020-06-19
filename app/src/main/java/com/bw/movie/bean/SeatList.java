package com.bw.movie.bean;

/**
 * 座位信息Bean类
 * 李易泽
 * 20200618
 */
public class SeatList {
    private String row;
    private String seat;
    private int status;
    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }
    public String getSeat() {
        return seat;
    }
    public void setSeat(String seat) {
        this.seat = seat;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
