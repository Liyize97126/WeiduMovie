package com.bw.movie.bean;

import java.util.List;

/**
 * 电影详情数据类
 * 李易泽
 * 20200528
 */
public class MoviesDetail {
    private int commentNum;
    private String duration;
    private String imageUrl;
    private int movieId;
    private String movieType;
    private String name;
    private String placeOrigin;
    private long releaseTime;
    private double score;
    private String summary;
    private int whetherFollow;
    private List<MovieActorBean> movieActor;
    private List<MovieDirectorBean> movieDirector;
    private List<String> posterList;
    private List<ShortFilmListBean> shortFilmList;
    public int getCommentNum() {
        return commentNum;
    }
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public String getMovieType() {
        return movieType;
    }
    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPlaceOrigin() {
        return placeOrigin;
    }
    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }
    public long getReleaseTime() {
        return releaseTime;
    }
    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public int getWhetherFollow() {
        return whetherFollow;
    }
    public void setWhetherFollow(int whetherFollow) {
        this.whetherFollow = whetherFollow;
    }
    public List<MovieActorBean> getMovieActor() {
        return movieActor;
    }
    public void setMovieActor(List<MovieActorBean> movieActor) {
        this.movieActor = movieActor;
    }
    public List<MovieDirectorBean> getMovieDirector() {
        return movieDirector;
    }
    public void setMovieDirector(List<MovieDirectorBean> movieDirector) {
        this.movieDirector = movieDirector;
    }
    public List<String> getPosterList() {
        return posterList;
    }
    public void setPosterList(List<String> posterList) {
        this.posterList = posterList;
    }
    public List<ShortFilmListBean> getShortFilmList() {
        return shortFilmList;
    }
    public void setShortFilmList(List<ShortFilmListBean> shortFilmList) {
        this.shortFilmList = shortFilmList;
    }
    public static class MovieActorBean {
        private String name;
        private String photo;
        private String role;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPhoto() {
            return photo;
        }
        public void setPhoto(String photo) {
            this.photo = photo;
        }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
    }
    public static class MovieDirectorBean {
        private String name;
        private String photo;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPhoto() {
            return photo;
        }
        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
    public static class ShortFilmListBean {
        private String imageUrl;
        private String videoUrl;
        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        public String getVideoUrl() {
            return videoUrl;
        }
        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
