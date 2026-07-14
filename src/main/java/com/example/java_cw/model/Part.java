package com.example.java_cw.model;

public class Part {

    private String partId;
    private String partName;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private String dateAdded;
    private String imagePath;


    public Part(String partId, String partName, String brand, double price, int quantity, String category, String dateAdded, String imagePath) {

        if (partId == null || partId.trim().isEmpty()) {
            throw new IllegalArgumentException("Part ID cannot be empty");
        }
        if (partName == null || partName.trim().isEmpty()) {
            throw new IllegalArgumentException("Part Name cannot be empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
         this.partId = partId;
        this.partName = partName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.dateAdded = dateAdded;
        this.imagePath = imagePath;
    }

    public String getPartId() {
        return partId;
    }
    public String getPartName() {
        return partName;
    }
    public void setPartName(String partName) {
        if (partName == null || partName.trim().isEmpty()) {
            throw new IllegalArgumentException("Part Name cannot be empty");
        }
        this.partName = partName;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category;
    }
    public String getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String toString() {
        return partId + " | " + partName + " | " + brand + " | Rs." + price + " | Qty:" + quantity + " | " + category + " | " + dateAdded + " | " + imagePath;
    }
}