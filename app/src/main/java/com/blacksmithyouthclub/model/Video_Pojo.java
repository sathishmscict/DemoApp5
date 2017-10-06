package com.blacksmithyouthclub.model;

/**
 * Created by pc on 9/29/2017.
 */

public class Video_Pojo {

    private String VideoId,VideoName,VideoUrl,VideoImg;
    public Video_Pojo(String VideoId,String VideoName,String VideoUrl,String VideoImg) {
        this.VideoId=VideoId;
        this.VideoName=VideoName;
        this.VideoUrl=VideoUrl;
        this.VideoImg=VideoImg;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getVideoImg() {
        return VideoImg;
    }

    public void setVideoImg(String videoImg) {
        VideoImg = videoImg;
    }
}
