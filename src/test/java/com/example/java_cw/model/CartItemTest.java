package com.example.java_cw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void getPart() {
        Part part = new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5);

        CartItem item = new CartItem(part,2);

        assertEquals(part, item.getPart());
    }

    @Test
    void getQuantity() {
        Part part = new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5);

        CartItem item = new CartItem(part,3);

        assertEquals(3, item.getQuantity());
    }

    @Test
    void setQuantity() {
    }

    @Test
    void getSubTotal() {
    }

    @Test
    void testToString() {
    }
}