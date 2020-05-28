package com.bw.movie.bean;

/**
 * 热门电影列表
 * 李易泽
 * 20200525
 */
public class HotMovieList {
    private String director;
    private String horizontalImage;
    private String imageUrl;
    private long movieId;
    private String name;
    private double score;
    private String starring;
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getHorizontalImage() {
        return horizontalImage;
    }
    public void setHorizontalImage(String horizontalImage) {
        this.horizontalImage = horizontalImage;
    }
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
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public String getStarring() {
        return starring;
    }
    public void setStarring(String starring) {
        this.starring = starring;
    }
}
