package com.example.java_cw.service;

import com.example.java_cw.model.Part;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    @Test
    void addPart() {
        InventoryService service = new InventoryService("test.txt", "audit.txt");

        Part part = new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5);

        service.addPart(part);

        assertEquals(1, service.parts.size());
        assertEquals("P001", service.parts.get(0).getPartId());
    }

    @Test
    void deletePart() {
        InventoryService service = new InventoryService("test.txt", "audit.txt");

        Part part = new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5);

        service.addPart(part);

        service.deletePart("P001");

        assertEquals(0, service.parts.size());
    }

    @Test
    void updatePart() {
        InventoryService service = new InventoryService("test.txt","audit.txt");

        Part original = new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5);

        service.addPart(original);

        Part updated = new Part(
                "P001","Premium Brake Pad","TVS",
                1500,15,"brakes",
                "01-01-2025","",5);

        service.updatePart("P001", updated);

        assertEquals("Premium Brake Pad",
                service.parts.get(0).getPartName());

        assertEquals(1500,
                service.parts.get(0).getPrice());
    }

    @Test
    void searchParts() {
        InventoryService service = new InventoryService("test.txt","audit.txt");

        service.addPart(new Part(
                "P001","Brake Pad","TVS",
                1000,10,"brakes",
                "01-01-2025","",5));

        List<Part> results =
                service.searchParts("Brake","",0,0);

        assertEquals(1, results.size());
        assertEquals("Brake Pad",
                results.get(0).getPartName());

    }

    @Test
    void getLowStockParts() {
    }

    @Test
    void sortParts() {
    }

    @Test
    void getTotalValue() {
    }

    @Test
    void getTotalCount() {
    }
}