package com.example.java_cw.model;

import com.example.java_cw.service.InventoryService;
import com.example.java_cw.util.AuditLogger;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public String addItem(Part part, int quantity) {
        if (quantity <= 0) {
            return "Quantity must be greater than zero";
        }

        int alreadyInCart = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPart().getPartId().equals(part.getPartId())) {
                alreadyInCart = items.get(i).getQuantity();
                break;
            }
        }

        int totalRequested = alreadyInCart + quantity;
        if (totalRequested > part.getQuantity()) {
            return "Not enough stock. Available: " + part.getQuantity() + " (you already have " + alreadyInCart + " in your cart)";
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPart().getPartId().equals(part.getPartId())) {
                items.get(i).setQuantity(items.get(i).getQuantity() + quantity);
                return "success";
            }
        }

        items.add(new CartItem(part, quantity));
        return "success";
    }

    public void removeItem(String partId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getPart().getPartId().equals(partId)) {
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

            if (item.getQuantity() >= 3) {
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
            if (items.get(i).getPart().getCategory().toLowerCase().equals(category)) {
                return true;
            }
        }
        return false;
    }

    public String checkout(InventoryService inventoryService, String auditLogPath) {
        if (items.isEmpty()) {
            return "Cart is Empty";
        }

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            String partId = item.getPart().getPartId();
            int quantityBought = item.getQuantity();

            for (int j = 0; j < inventoryService.parts.size(); j++) {
                Part inventoryPart = inventoryService.parts.get(j);

                if(inventoryPart.getPartId().equals(partId)) {
                    int newQuantity = inventoryPart.getQuantity() - quantityBought;
                    inventoryPart.setQuantity(newQuantity);
                    break;
                }
            }
            AuditLogger.log(
                    auditLogPath, "CHECKOUT", item.getPart().getPartId(), "Qty: " + item.getQuantity());
        }

        inventoryService.saveToFile();
        items.clear();
        return "Checkout successful";
    }
    public String getDiscountSummary() {
        double rawTotal = 0;
        boolean bulkApplied = false;

        for (CartItem item : items) {
            rawTotal += item.getSubTotal();

            if (item.getQuantity() >= 3) {
                bulkApplied = true;
            }
        }

        boolean synergyApplied =
                hasCategory("engine") &&
                        hasCategory("electrical");

        double finalTotal = getTotal();

        if (!bulkApplied && !synergyApplied) {
            return "No discounts applied";
        }

        StringBuilder sb = new StringBuilder();

        if (bulkApplied) {
            sb.append("Bulk Discount (5%)\n");
        }

        if (synergyApplied) {
            sb.append("Synergy Discount (10%)\n");
        }

        sb.append(String.format("Saved: Rs. %.2f",
                rawTotal - finalTotal));

        return sb.toString();
    }

    public List<CartItem> getItems() {

        return items;
    }

    public int getTotalItems() {

        return items.size();
    }
}