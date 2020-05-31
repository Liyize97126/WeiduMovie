package com.bw.movie.bean;

/**
 * 我的界面功能菜单列表数据类
 * 李易泽
 * 20200529
 */
public class MyMenuListBean {
    private int buttonImage;
    private String buttonName;
    public MyMenuListBean(int buttonImage, String buttonName) {
        this.buttonImage = buttonImage;
        this.buttonName = buttonName;
    }
    public int getButtonImage() {
        return buttonImage;
    }
    public void setButtonImage(int buttonImage) {
        this.buttonImage = buttonImage;
    }
    public String getButtonName() {
        return buttonName;
    }
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
