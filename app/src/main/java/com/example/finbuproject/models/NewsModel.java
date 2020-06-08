package com.example.finbuproject.models;

public class NewsModel {
    private int News_ID;
    private int User_ID;
    private String News_Detail;
//    biến image??
    private String News_time_upload;

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
