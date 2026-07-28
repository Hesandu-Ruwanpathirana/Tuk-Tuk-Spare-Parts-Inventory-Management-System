package com.example.java_cw.model;

public class AuditEntry {
    private String timestamp;
    private String action;
    private String itemCode;
    private String details;

    public AuditEntry(String timestamp, String action, String itemCode, String details) {
        this.timestamp = timestamp;
        this.action = action;
        this.itemCode = itemCode;
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public String getAction() {
        return action;
    }
    public String getItemCode() {
        return itemCode;
    }
    public String getDetails() {
        return details;
    }
}