package com.bw.movie.bean;

import com.stx.xhb.androidx.entity.SimpleBannerInfo;

/**
 * Banner数据类
 * 李易泽
 * 20200529
 */
public class BannerData extends SimpleBannerInfo {
    private String imageUrl;
    private String jumpUrl;
    private int rank;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getJumpUrl() {
        return jumpUrl;
    }
    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    @Override
    public Object getXBannerUrl() {
        return null;
    }
}
