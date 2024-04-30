package com.example.hyperkicks;

public class CartModel {
    private String cartid;
    private String productName;
    private String productImageUrl;
    private String productPrice;
    private String productQty;
    private String productsize;
    private String sellerUid;

    public boolean is_selected;

    private String ordernumber;



    public CartModel() {
    }

    public CartModel(String cartid, String productName, String productImageUrl, String productPrice, String productQty, String productsize, String sellerUid, String ordernumber) {
        this.cartid = cartid;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productsize = productsize;
        this.sellerUid = sellerUid;
        this.ordernumber = ordernumber;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductsize() {
        return productsize;
    }

    public void setProductsize(String productsize) {
        this.productsize = productsize;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }
}
