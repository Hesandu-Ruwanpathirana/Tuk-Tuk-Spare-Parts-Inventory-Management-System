package com.example.java_cw.service;

import com.example.java_cw.model.Dealer;
import com.example.java_cw.parser.DealerParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealerService {
    public List<Dealer> dealers;
    public String filePath;


    public DealerService(String filePath) {
        this.filePath = filePath;
        this.dealers = new ArrayList<>();
    }
    public void loadDealers() {
        dealers = DealerParser.parseDealers(filePath);
    }

    public List<Dealer> getRandomDealers() {
        List<Dealer> selected = new ArrayList<>();
        List<Integer> usedIndexes = new ArrayList<>();
        Random random = new Random();

        while (selected.size() < 4) {
            int index = random.nextInt(dealers.size());

            boolean alreadyUsed = false;
            for (int i = 0; i < usedIndexes.size(); i++) {
                if (usedIndexes.get(i) == index) {
                    alreadyUsed = true;
                    break;

                }
            }
            if(!alreadyUsed) {
                selected.add(dealers.get(index));
                usedIndexes.add(index);
            }

        }
        return sortByLocation(selected);

    }
    public List<Dealer> sortByLocation(List<Dealer> dealersToSort) {
        List<Dealer> sorted = new ArrayList<>(dealersToSort);

        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() -i -1; j++) {
                Dealer a = sorted.get(j);
                Dealer b = sorted.get(j+1);

                if(a.dealerLocation.toLowerCase().compareTo(b.dealerLocation.toLowerCase()) > 0) {
                    sorted.set(j,b);
                    sorted.set(j+1,a);
                }
            }
        }
        return sorted;

    }
    public int getTotalDealers() {
        return dealers.size();
    }
}
