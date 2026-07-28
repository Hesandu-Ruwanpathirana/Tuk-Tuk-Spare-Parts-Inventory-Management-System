package com.example.java_cw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @Test
    void getDealerId() {
        Dealer dealer = new Dealer(
                "D001",
                "ABC Motors",
                "0771234567",
                "Colombo"
        );

        assertEquals("D001", dealer.getDealerId());
    }

    @Test
    void getDealerName() {
        Dealer dealer = new Dealer(
                "D001",
                "ABC Motors",
                "0771234567",
                "Colombo"
        );
        assertEquals("ABC Motors",dealer.getDealerName());

    }

    @Test
    void getDealerPhone() {
        Dealer dealer = new Dealer(
                "D001",
                "ABC Motors",
                "0771234567",
                "Colombo"
        );
        assertEquals("0771234567",dealer.getDealerPhone());

    }

    @Test
    void getDealerLocation() {
            Dealer dealer = new Dealer(
                    "D001",
                    "ABC Motors",
                    "0771234567",
                    "Colombo"
            );

            assertEquals("Colombo", dealer.getDealerLocation());
    }

    @Test
    void testToString() {
        Dealer dealer = new Dealer(
                "D001",
                "ABC Motors",
                "0771234567",
                "Colombo"
        );

        String text = dealer.toString();

        assertTrue(text.contains("D001"));
        assertTrue(text.contains("ABC Motors"));
        assertTrue(text.contains("0771234567"));
        assertTrue(text.contains("Colombo"));
    }
}