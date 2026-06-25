package com.example.java_cw.parser;


import com.example.java_cw.model.Part;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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

                try {
                    String partId = fields[0];
                    String partName = fields[1];

                    String brand = "";
                    brand = fields.length > 2 ? brand = fields[2] : "";

                    double price = cleanPrice(fields[3]);
                    int quantity = Integer.parseInt(fields[4]);
                    String category = fields[5].toLowerCase();

                    String dateAdded = "";
                    dateAdded = fields.length > 6 ? dateAdded = fields[6] : "";

                    String imagePath = "";
                    imagePath = fields.length > 7 ? imagePath = fields[7] : "";

                    Part part = new Part (
                            partId,
                            partName,
                            brand,
                            price,
                            quantity,
                            category,
                            dateAdded,
                            imagePath

                    );
                    parts.add(part);

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid " + line);
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

}
