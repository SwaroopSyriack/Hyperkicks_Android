package com.example.hyperkicks;

import java.io.Serializable;

public class productModel implements Serializable {
    String product_id,name,description,mrp,sp,brand,ImgUrl;

    public productModel(){
        // Constructor to add data to firebase
    }
    public productModel(String product_id, String name, String description, String mrp, String sp, String brand, String imgUrl) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.mrp = mrp;
        this.sp = sp;
        this.brand = brand;
        ImgUrl = imgUrl;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
