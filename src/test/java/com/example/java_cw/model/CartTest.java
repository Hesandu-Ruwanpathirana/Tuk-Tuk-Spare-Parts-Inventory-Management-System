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
    }

    @Test
    void removeItem() {
    }

    @Test
    void clear() {
    }

    @Test
    void isEmpty() {
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