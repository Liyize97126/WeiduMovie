package com.bw.movie.bean;

/**
 * 即将上映列表
 * 李易泽
 * 20200525
 */
public class ComingSoonMovieList {
    private String imageUrl;
    private long movieId;
    private String name;
    private long releaseTime;
    private int wantSeeNum;
    private int whetherReserve;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public long getMovieId() {
        return movieId;
    }
    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getReleaseTime() {
        return releaseTime;
    }
    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }
    public int getWantSeeNum() {
        return wantSeeNum;
    }
    public void setWantSeeNum(int wantSeeNum) {
        this.wantSeeNum = wantSeeNum;
    }
    public int getWhetherReserve() {
        return whetherReserve;
    }
    public void setWhetherReserve(int whetherReserve) {
        this.whetherReserve = whetherReserve;
    }
}
