package com.bw.movie.bean;

import com.bw.movie.base.BaseFragment;

/**
 * 电影详情tab页信息数据类
 * 李易泽
 * 20200530
 */
public class MoviesDetailTabBean {
    private String tabTitle;
    private BaseFragment baseFragment;
    public MoviesDetailTabBean(String tabTitle, BaseFragment baseFragment) {
        this.tabTitle = tabTitle;
        this.baseFragment = baseFragment;
    }
    public String getTabTitle() {
        return tabTitle;
    }
    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }
    public BaseFragment getBaseFragment() {
        return baseFragment;
    }
    public void setBaseFragment(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }
}
