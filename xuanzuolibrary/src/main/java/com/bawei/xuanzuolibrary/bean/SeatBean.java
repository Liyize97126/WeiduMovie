package com.bawei.xuanzuolibrary.bean;

/**
 *  座位数据类
 * 李易泽
 * 20200615
 */
public class SeatBean {
    private String row;
    private String seat;
    private int status;
    private boolean isChoose = false;
    public SeatBean() {
    }
    public SeatBean(String row, String seat, int status) {
        this.row = row;
        this.seat = seat;
        this.status = status;
    }
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
    public boolean isChoose() {
        return isChoose;
    }
    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
