package com.example.hyperkicks;

public class sliderModel {

    public sliderModel(){
        // This constructor is used for Firebase
    }

    private String imageURL;

    public sliderModel(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}