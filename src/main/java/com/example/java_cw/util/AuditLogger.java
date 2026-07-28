package com.example.java_cw.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
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

        } catch (IOException e) {
            System.out.println("Error writing to audit log: " + e.getMessage());
        }
    }


}
