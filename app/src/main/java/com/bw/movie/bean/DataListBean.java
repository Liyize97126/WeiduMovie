package com.bw.movie.bean;

import java.util.List;

/**
 * 根数据Bean类
 * 李易泽
 * 20200525
 */
public class DataListBean<T> {
    private List<T> result;
    private String message;
    private String status;
    public List<T> getResult() {
        return result;
    }
    public void setResult(List<T> result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
