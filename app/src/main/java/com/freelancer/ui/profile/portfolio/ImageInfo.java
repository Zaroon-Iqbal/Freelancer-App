package com.freelancer.ui.profile.portfolio;

public class ImageInfo {
    String uri;
    String path;
    boolean selected = false;

    public ImageInfo(String uri){
        this.uri = uri;
        path = "";
    }

    public ImageInfo(String uri, String path){
        this.uri = uri;
        this.path = path;
    }
}
