package com.example.hyperkicks;

public class orderModel {

    private String Ordernumber;
    private String Customer_fname;
    private String Customer_lname;
    private String customer_place;
    private String customer_address;
    private String item_expences;
    private String delivery_charges;
    private String Ordertracking_no;
    private String Courier;
    private String Ordering_date;
    private String deliverystatus;

    private String userid;


    public orderModel(){
        //For Firebase
    }

    public orderModel(String ordernumber, String customer_fname, String customer_lname, String customer_place, String customer_address, String item_expences, String delivery_charges, String ordertracking_no, String courier, String ordering_date, String deliverystatus, String userid) {
        Ordernumber = ordernumber;
        Customer_fname = customer_fname;
        Customer_lname = customer_lname;
        this.customer_place = customer_place;
        this.customer_address = customer_address;
        this.item_expences = item_expences;
        this.delivery_charges = delivery_charges;
        Ordertracking_no = ordertracking_no;
        Courier = courier;
        Ordering_date = ordering_date;
        this.deliverystatus = deliverystatus;
        this.userid = userid;
    }

    public String getOrdernumber() {
        return Ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        Ordernumber = ordernumber;
    }

    public String getCustomer_fname() {
        return Customer_fname;
    }

    public void setCustomer_fname(String customer_fname) {
        Customer_fname = customer_fname;
    }

    public String getCustomer_lname() {
        return Customer_lname;
    }

    public void setCustomer_lname(String customer_lname) {
        Customer_lname = customer_lname;
    }

    public String getCustomer_place() {
        return customer_place;
    }

    public void setCustomer_place(String customer_place) {
        this.customer_place = customer_place;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getItem_expences() {
        return item_expences;
    }

    public void setItem_expences(String item_expences) {
        this.item_expences = item_expences;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    public String getOrdertracking_no() {
        return Ordertracking_no;
    }

    public void setOrdertracking_no(String ordertracking_no) {
        Ordertracking_no = ordertracking_no;
    }

    public String getCourier() {
        return Courier;
    }

    public void setCourier(String courier) {
        Courier = courier;
    }

    public String getOrdering_date() {
        return Ordering_date;
    }

    public void setOrdering_date(String ordering_date) {
        Ordering_date = ordering_date;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
