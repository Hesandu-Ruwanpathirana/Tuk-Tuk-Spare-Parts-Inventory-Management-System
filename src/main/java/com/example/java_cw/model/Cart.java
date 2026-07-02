package com.example.java_cw.model;

import com.example.java_cw.service.InventoryService;
import com.example.java_cw.util.AuditLogger;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    public List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public String addItem(Part part, int quantity) {
        if (quantity <= 0) {
            return "Quantity must be greater than zero";
        }

        if (quantity > part.quantity) {
            return "Not enough stock. Available: " + part.quantity;
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).part.partId.equals(part.partId)) {
                items.get(i).quantity += quantity;
                return "success";
            }
        }

        items.add(new CartItem(part, quantity));
        return "success";
    }

    public void removeItem(String partId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).part.partId.equals(partId)) {
                items.remove(i);
                return;
            }
        }
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotal() {
        double total = 0;

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            double subTotal = item.getSubTotal();

            if (item.quantity >= 3) {
                subTotal = subTotal * 0.95;
            }

            total += subTotal;
        }

        if (hasCategory("engine") && hasCategory("electrical")) {
            total = total * 0.90;
        }

        return total;
    }

    public boolean hasCategory(String category) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).part.category.toLowerCase().equals(category)) {
                return true;
            }
        }
        return false;
    }

    public String checkout(InventoryService inventoryService, String auditLogPath) {
        if (items.isEmpty()) {
            return "Cart is empty";
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);

            item.part.quantity -= item.quantity;

            AuditLogger.log(
                    auditLogPath, "CHECKOUT", item.part.partId, "Qty: " + item.quantity);
        }

        inventoryService.saveToFile();
        items.clear();
        return "Checkout successful";
    }

    public int getTotalItems() {
        return items.size();
    }
}