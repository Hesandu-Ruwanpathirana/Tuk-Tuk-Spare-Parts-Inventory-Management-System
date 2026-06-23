package com.example.java_cw.model;

public class Dealer {

    public String dealerId;
    public String dealerName;
    public String dealerPhone;
    public String dealerLocation;

    public Dealer(String dealerId, String dealerName, String dealerPhone, String dealerLocation) {
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.dealerPhone = dealerPhone;
        this.dealerLocation = dealerLocation;

    }

    public String toString() {
        return dealerId + " | " + dealerName + " | " + dealerPhone + " | " + dealerLocation;
    }
}