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
        Part part = new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5);

        CartItem item = new CartItem(part,2);
        item.setQuantity(5);
        assertEquals(5, item.getQuantity());
    }

    @Test
    void getSubTotal() {
            Part part = new Part(
                    "P001","Brake Pad","TVS",
                    1000,10,"brakes",
                    "01-01-2025","",5);

            CartItem item = new CartItem(part,3);
            assertEquals(3000, item.getSubTotal());
    }

    @Test
    void testToString() {
            Part part = new Part(
                    "P001","Brake Pad","TVS",
                    10000,10,"brakes",
                    "01-01-2025","",5);

            CartItem item = new CartItem(part,2);

            String text = item.toString();

            assertTrue(text.contains("Brake Pad"));
            assertTrue(text.contains("Rs.20000.0"));
    }
}