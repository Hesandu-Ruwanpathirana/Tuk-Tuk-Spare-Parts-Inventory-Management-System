package com.example.java_cw.parser;


import com.example.java_cw.model.Dealer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DealerParser {
    public static List<Dealer> parseDealers (String filePath) {
        List<Dealer> dealers = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty()) {
                    continue;
                }
                String[] fields = line.split("[|,;]",-1);

                for(int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }
                if (fields.length < 4) {
                    continue;

                }

                try {
                    String dealerId = fields[0];
                    String dealerName = fields[1];

                    String dealerPhone = "";
                    dealerPhone = fields.length > 2 ? dealerPhone = fields[2] : "";

                    String dealerLocation = "";
                    dealerLocation = fields.length > 3 ? dealerLocation = fields[3] : "";

                    Dealer dealer = new Dealer(dealerId,dealerName,dealerPhone,dealerLocation);
                    dealers.add(dealer);

                    } catch (Exception e) {
                    System.out.println("Skipping invalid line: " + line);

                }
            }
            reader.close();


        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return dealers;

    }


}
