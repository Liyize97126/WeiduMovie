package com.bw.movie.bean;

/**
 * 影院排期查询结果数据Bean类
 * 李易泽
 * 20200615
 */
public class CinemaScheduleList {
    private String director;
    private String imageUrl;
    private long movieId;
    private String name;
    private double score;
    private String starring;
    private String trailerUrl;
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
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
    public String getTrailerUrl() {
        return trailerUrl;
    }
    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }
}
