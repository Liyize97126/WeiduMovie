package com.bw.movie.bean;

/**
 * 处理单个对象的根数据Bean类
 * 李易泽
 * 20200528
 */
public class DataBean<T> {
    private T result;
    private String message;
    private String status;
    private String headPath;
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
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
    public String getHeadPath() {
        return headPath;
    }
    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }
}
