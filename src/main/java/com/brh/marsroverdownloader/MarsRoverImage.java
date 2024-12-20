package com.brh.marsroverdownloader;

import java.time.LocalDate;

public class MarsRoverImage {

    private LocalDate date;
    private String imgURL;
    private String cameraName;

    private int downloadProgress;

    public MarsRoverImage(LocalDate date, String imgURL, String cameraName) {
        this.date = date;
        this.imgURL = imgURL;
        this.cameraName = cameraName;
    }

    public int getDownloadProgress() {
        return downloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }


}
