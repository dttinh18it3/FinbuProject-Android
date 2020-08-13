package com.example.finbuproject.models;

public class NewsModel {
    private int News_ID;
    private int User_ID;
    private String News_Amount_Like;
    private String News_Detail;
    private String News_Image;
    private String News_time_upload;
    private boolean isSelfLike;

    public NewsModel() {
    }

    public String getNews_Amount_Like() {
        return News_Amount_Like;
    }

    public void setNews_Amount_Like(String news_Amount_Like) {
        News_Amount_Like = news_Amount_Like;
    }

    public boolean isSelfLike() {
        return isSelfLike;
    }

    public void setSelfLike(boolean selfLike) {
        isSelfLike = selfLike;
    }

    public String getNews_Image() {
        return News_Image;
    }

    public void setNews_Image(String News_Image) {
        this.News_Image = News_Image;
    }



    public int getNews_ID() {
        return News_ID;
    }

    public void setNews_ID(int news_ID) {
        News_ID = news_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getNews_Detail() {
        return News_Detail;
    }

    public void setNews_Detail(String news_Detail) {
        News_Detail = news_Detail;
    }

    public String getNews_time_upload() {
        return News_time_upload;
    }

    public void setNews_time_upload(String news_time_upload) {
        News_time_upload = news_time_upload;
    }
}
