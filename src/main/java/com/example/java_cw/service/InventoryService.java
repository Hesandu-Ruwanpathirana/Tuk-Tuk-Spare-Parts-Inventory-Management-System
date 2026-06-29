package com.example.java_cw.service;

import com.example.java_cw.model.Part;
import com.example.java_cw.parser.InventoryParser;
import com.example.java_cw.util.AuditLogger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    public List<Part> parts;
    public String filePath;
    public String auditLogPath;
    public int lowStockThreshold;

    public InventoryService(String filePath, String auditLogPath, int lowStock ) {
        this.filePath = filePath;
        this.auditLogPath = auditLogPath;
        this.lowStockThreshold = lowStock;
        this.parts = new ArrayList<>();
    }

    public void loadParts() {
        parts = InventoryParser.parseParts(filePath);

    }
    public void addPart(Part part) {
        parts.add(part);
        saveToFile();
        AuditLogger.log(auditLogPath,"ADD",part.partId,"Qty: " + part.quantity);

    }
    public void deletePart(String partId) {
        Part toDelete = null;

        for (int i = 0; i < parts.size() ; i++) {
            if(parts.get(i).partId.equals(partId)) {
                toDelete = parts.get(i);
                break;

            }
        }
        if(toDelete != null) {
            parts.remove(toDelete);
            saveToFile();
            AuditLogger.log(auditLogPath, "DELETE" , partId, "-");

        }

    }
    public void updatePart(String partId, Part updatedPart) {
        for (int i = 0; i < parts.size(); i ++) {
            if(parts.get(i).partId.equals(partId)) {
                    parts.set(i,updatedPart);
                    saveToFile();
                    return;
            }

        }
    }
    public List<Part> searchParts(String keyword, String category, double minPrice, double maxPrice) {
        List<Part> results = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);

            boolean matchesKeyword = keyword.isEmpty() || part.partName.toLowerCase().contains(keyword.toLowerCase()) ||
                    part.brand.toLowerCase().contains(keyword.toLowerCase()) || part.partId.toLowerCase().contains(keyword.toLowerCase());

            boolean matchesCategory = category.isEmpty() || part.category.toLowerCase().equals(category.toLowerCase());

            boolean matchesPrice = part.price >= minPrice && (maxPrice == 0 || part.price <= maxPrice);

            if (matchesKeyword && matchesCategory && matchesPrice) {
                results.add(part);
            }
        }
        return results;

    }
    public List<Part> getLowStockParts() {
        List<Part> lowStock = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {
            if(parts.get(i).quantity <= lowStockThreshold) {
                lowStock.add(parts.get(i));
            }
        }
        return lowStock;
    }
    public List<Part> getSortedParts() {
        List<Part> sorted = new ArrayList<>(parts);

        for (int i = 0; i < sorted.size()  -1; i++) {
            for (int j = 0; j < sorted.size()-i-1; j++) {
                Part a = sorted.get(j);
                Part b = sorted.get(j+1);

                int categoryCompare = a.category.toLowerCase() .compareTo(b.category.toLowerCase());

                if (categoryCompare > 0) {
                    sorted.set(j,b);
                    sorted.set(j+1,a);
                }else if (categoryCompare == 0) {
                    if (a.partId.compareTo(b.partId) > 0) {
                        sorted.set(j,b);
                        sorted.set(j+1,a);

                    }
                }
            }
        }
        return sorted;
    }
    public void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false));

            for (int i = 0; i < parts.size() ; i++) {
                writer.write(parts.get(i).toString());
                writer.newLine();

            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
    public double getTotalValue() {
        double total = 0;

        for (int i = 0; i < parts.size(); i++) {
            total += parts.get(i).price * parts.get(i).quantity;

        }
        return total;
    }
    public int getTotalCount() {
        return parts.size();
    }

}
