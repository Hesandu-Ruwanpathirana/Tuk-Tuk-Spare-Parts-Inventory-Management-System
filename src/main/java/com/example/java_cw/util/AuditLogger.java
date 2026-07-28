package com.example.java_cw.util;

import com.example.java_cw.model.AuditEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLogger {

    private static final List<AuditEntry> entries = new ArrayList<>();

    public static void log(String filePath, String action, String itemCode, String quantity) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timeStamp = now.format(formatter);

            String logEntry = timeStamp + " | " + action + " | " + itemCode + " | " + quantity;

            writer.write(logEntry);
            writer.newLine();
            writer.close();

            entries.add(0, new AuditEntry(timeStamp, action, itemCode, quantity));

        } catch (IOException e) {
            System.out.println("Error writing to audit log: " + e.getMessage());
        }
    }
        public static List<AuditEntry> getEntries() {
            return entries;
    }
}




