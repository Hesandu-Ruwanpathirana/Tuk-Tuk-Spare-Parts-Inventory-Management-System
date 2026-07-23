package com.example.java_cw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private Part enginePart;
    private Part electricalPart;
    private Part brakePart;

    @BeforeEach
    void setUp() {

        cart = new Cart();

        enginePart = new Part(
                "P001",
                "Engine Oil",
                "Bajaj",
                1000,
                10,
                "engine",
                "01-01-2024",
                "",
                5
        );

        electricalPart = new Part(
                "P002",
                "Battery",
                "TVS",
                2000,
                10,
                "electrical",
                "01-01-2024",
                "",
                5
        );

        brakePart = new Part(
                "P003",
                "Brake Pad",
                "TVS",
                500,
                10,
                "brakes",
                "01-01-2024",
                "",
                5
        );
    }

    @Test
    void addItem() {
            String result = cart.addItem(enginePart, 2);
            assertEquals("success", result);
            assertEquals(1, cart.getTotalItems());
    }

    @Test
    void removeItem() {
        cart.addItem(enginePart, 2);
        cart.removeItem(enginePart.getPartId());
        assertTrue(cart.isEmpty());
    }

    @Test
    void clear() {
        cart.addItem(enginePart, 2);
        cart.addItem(brakePart, 1);

        cart.clear();
        assertTrue(cart.isEmpty());
    }

    @Test
    void isEmpty() {
        assertTrue(cart.isEmpty());
        cart.addItem(enginePart, 1);
        assertFalse(cart.isEmpty());
    }

    @Test
    void getTotal() {
    }

    @Test
    void hasCategory() {
    }

    @Test
    void checkout() {
    }
}