package com.example.java_cw.model;

public class Dealer {

    private String dealerId;
    private String dealerName;
    private String dealerPhone;
    private String dealerLocation;

    public Dealer(String dealerId, String dealerName, String dealerPhone, String dealerLocation) {
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.dealerPhone = dealerPhone;
        this.dealerLocation = dealerLocation;

    }

    public String getDealerId() {
        return dealerId;
    }
    public String getDealerName() {
        return dealerName;
    }
    public void setPartName(String partName) {
        if (partName == null || partName.trim().isEmpty()) {


        }
    }
    public String getDealerPhone() {
        return dealerPhone;
    }
    public String getDealerLocation() {
        return dealerLocation;
    }
    public String toString() {
        return dealerId + " | " + dealerName + " | " + dealerPhone + " | " + dealerLocation;
    }
}