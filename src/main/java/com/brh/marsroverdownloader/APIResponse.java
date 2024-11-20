package com.brh.marsroverdownloader;

import java.time.LocalDate;
import java.util.Date;

public class APIResponse {

    private LocalDate date;
    private String imgURL;
    private String cameraName;

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
