package com.example.java_cw.model;

public class CartItem {
    public Part part;
    public int quantity;

    public CartItem(Part part, int quantity) {
        this.part = part;
        this.quantity = quantity;
    }
    public double getSubTotal() {
        return part.price * quantity;

    }
    public String toString() {
        return part.partName + " x" + quantity + " = Rs." + getSubTotal();
    }
}
