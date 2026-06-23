package com.example.java_cw.model;

public class Part {

    public String partId;
    public String partName;
    public String brand;
    public double price;
    public int quantity;
    public String category;
    public String dateAdded;
    public String imagePath;


    public Part(String partId, String partName, String brand, double price, int quantity, String category, String dateAdded, String imagePath) {
        this.partId = partId;
        this.partName = partName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.dateAdded = dateAdded;
        this.imagePath = imagePath;
    }
    public String toString() {
        return partId + " | " + partName + " | " + brand + " | Rs." + price + " | Qty:" + quantity + " | " + category + " | " + dateAdded + " | " + imagePath;
    }
}