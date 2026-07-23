package com.example.java_cw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void setPrice() {
            Part part = new Part(
                    "P001", "Brake Pad", "TVS",
                    1000, 10, "brakes",
                    "01-01-2025", "", 5);

            part.setPrice(2000);

            assertEquals(2000, part.getPrice());
    }

    @Test
    void setQuantity() {
        Part part = new Part(
                "P001", "Brake Pad", "TVS",
                1000, 10, "brakes",
                "01-01-2025", "", 5);

        part.setQuantity(25);

        assertEquals(25, part.getQuantity());


    }

    @Test
    void setCategory() {
        Part part = new Part(
                "P001", "Brake Pad", "TVS",
                1000, 10, "brakes",
                "01-01-2025", "", 5);

        part.setCategory("engine");
        assertEquals("engine", part.getCategory());
    }

    @Test
    void setLowStockThreshold() {
        Part part = new Part(
                "P001", "Brake Pad", "TVS",
                1000, 10, "brakes",
                "01-01-2025", "", 5);

        part.setLowStockThreshold(15);

        assertEquals(15, part.getLowStockThreshold());

    }

    @Test
    void testToString() {
    }

    @Test
    void isLowStock() {
    }
}