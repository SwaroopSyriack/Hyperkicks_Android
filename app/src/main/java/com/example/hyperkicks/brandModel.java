package com.example.hyperkicks;

import java.io.Serializable;

public class brandModel implements Serializable {
    private String id, imageUrl;
    private String brandname;

    public brandModel(){

    }

    public brandModel(String id, String imageUrl, String brandname) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.brandname = brandname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }
}