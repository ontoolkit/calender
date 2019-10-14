package com.project.srmcalender.Pojo;

public class GetEventDetails {

    private String id ;
    private String imageurl;
    private String content;

    public GetEventDetails(String id, String imageurl, String content) {
        this.id = id;
        this.imageurl = imageurl;
        this.content = content;
    }

    public GetEventDetails() {
    }

    public String getId() {
        return id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getContent() {
        return content;
    }
}
