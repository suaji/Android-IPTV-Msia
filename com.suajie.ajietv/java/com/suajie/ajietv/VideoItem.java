package com.suajie.ajietv;

public class VideoItem {
    private String title;
    private String url;
    private String type;
    private String thumbnail;

    public VideoItem(String title, String url, String type, String thumbnail) {
        this.title = title;
        this.url = url;
        this.type = type;
        this.thumbnail = thumbnail;
    }

    // Getters
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getType() { return type; }
    public String getThumbnail() { return thumbnail; }
}