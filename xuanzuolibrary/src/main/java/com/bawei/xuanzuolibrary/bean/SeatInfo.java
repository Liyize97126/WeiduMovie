package com.bawei.xuanzuolibrary.bean;

/**
 * 座位信息
 * 李易泽
 * 20200615
 */
public class SeatInfo {
    private int row;
    private int seat;
    public SeatInfo(int row, int seat) {
        this.row = row;
        this.seat = seat;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getSeat() {
        return seat;
    }
    public void setSeat(int seat) {
        this.seat = seat;
    }
}
