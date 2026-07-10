package com.example.java_cw.parser;

import com.example.java_cw.model.Part;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryParser {
    public static List<Part> parseParts(String filePath) {
        List<Part> parts = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split("[,|;]",-1);

                for (int i = 0; i < fields.length; i++) {
                    fields[i]= fields[i].trim();
                }

                if (fields.length < 6) {
                    continue;
                }

                if (fields.length > 7) {
                    if (fields[6].matches("[a-zA-Z]+ \\d{1,2}") &&
                            fields[7].matches("\\d{4}")) {

                        String mergedDate = fields[6] + " " + fields[7];

                        String[] newFields = new String[fields.length - 1];

                        for (int i = 0; i <= 6; i++) {
                            newFields[i] = fields[i];
                        }

                        newFields[6] = mergedDate;

                        for (int i = 8; i < fields.length; i++) {
                            newFields[i - 1] = fields[i];
                        }

                        fields = newFields;
                    }
                }

                try {
                    String partId = fields[0];
                    String partName = fields[1];

                    String brand = "";
                    brand = fields.length > 2 ? brand = fields[2] : "";

                    double price = cleanPrice(fields[3]);
                    int quantity = Integer.parseInt(fields[4]);
                    String category = fields[5].toLowerCase();

                    String dateAdded = "";
                    if (fields.length > 6) {
                        dateAdded = cleanDate(fields[6]);
                    }

                    String imagePath = "";
                    if(fields.length > 7) {
                        imagePath = cleanImagePath(fields[7]);
                    }

                    Part part = new Part(partId,partName,brand,price,quantity,category,dateAdded,imagePath);
                    parts.add(part);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file " + e.getMessage());
        }
        return parts;
    }

    public static double cleanPrice(String rawPrice) {
        rawPrice = rawPrice.replaceAll("[^0-9.]","");
        rawPrice = rawPrice.replaceAll("^\\.+", "");
        rawPrice = rawPrice.trim();

        try {
            return Double.parseDouble(rawPrice);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static String cleanDate(String rawDate) {
        rawDate = rawDate.trim();

        if (rawDate.isEmpty()) {
            return "";
        }

        if (rawDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] dateParts = rawDate.split("-");
            return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
        }

        if (rawDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            String[] dateParts = rawDate.split("/");
            return dateParts[0] + "-" + dateParts[1] + "-" + dateParts[2];
        }

        if (rawDate.matches("\\d{4}/\\d{2}/\\d{2}")) {
            String[] dateParts = rawDate.split("/");
            return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
        }

        if (rawDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
            String[] dateParts = rawDate.split("-");
            return dateParts[0] + "-" + dateParts[1] + "-" + dateParts[2];
        }

        if (rawDate.matches("\\d{2}-[a-zA-Z]+-\\d{4}")) {
            String[] dateParts = rawDate.split("-");
            String month = convertMonth(dateParts[1]);
            return dateParts[0] + "-" + month + "-" + dateParts[2];
        }

        rawDate = rawDate.replace(",","").trim();
        if(rawDate.matches("[a-zA-Z]+ \\d{2} \\d{4}")) {
            String[] dateParts = rawDate.split(" ");
            String month = convertMonth(dateParts[0]);
            return dateParts[1] + "-" + month + "-" + dateParts[2];
        }

        return rawDate;
    }

    public static String convertMonth(String month) {
        month = month.toLowerCase();
        if (month.equals("jan")) {
            return "01";
        }
        if (month.equals("feb")) {
            return "02";
        }
        if(month.equals("mar")) {
            return "03";
        }
        if (month.equals("apr")) {
            return "04";
        }
        if(month.equals("may")) {
            return "05";
        }
        if (month.equals("jun")) {
            return "06";
        }
        if (month.equals("jul")) {
            return "07";
        }
        if(month.equals("aug")) {
            return "08";
        }
        if(month.equals("sep")) {
            return "09";
        }
        if(month.equals("oct")) {
            return "10";
        }
        if(month.equals("nov")) {
            return "11";
        }
        if (month.equals("dec")) {
            return "12";
        }
        return "00";
    }

    public static String cleanImagePath(String imagePath) {
        imagePath = imagePath.trim();

        if(imagePath.isEmpty()) {
            return "";
        }
        if(imagePath.endsWith(".jpg") || imagePath.endsWith(".jpeg") || imagePath.endsWith(".png")) {
            return imagePath;
        }
        return "";
    }
}