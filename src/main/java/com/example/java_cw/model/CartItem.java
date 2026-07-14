package com.example.java_cw.model;

public class CartItem {
    private Part part;
    private int quantity;

    public CartItem(Part part, int quantity) {
        if (part == null) {
            throw new IllegalArgumentException("Part cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.part = part;
        this.quantity = quantity;
    }
    public Part getPart() {
        return part;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return part.getPrice() * quantity;

    }
    public String toString() {
        return part.getPartName() + " x" + quantity + " = Rs." + getSubTotal();
    }
}
