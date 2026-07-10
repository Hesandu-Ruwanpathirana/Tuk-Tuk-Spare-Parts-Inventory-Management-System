package com.example.java_cw;

import com.example.java_cw.model.Cart;
import com.example.java_cw.service.DealerService;
import com.example.java_cw.service.InventoryService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TabPane mainTabPane;

    private InventoryService inventoryService;
    private DealerService dealerService;
    private Cart cart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set up file paths
        String inventoryPath = "src/main/resources/com/example/java_cw/inventory_legacy.txt";
        String dealersPath = "src/main/resources/com/example/java_cw/dealers_legacy.txt";
        String auditPath = "src/main/resources/com/example/java_cw/audit_log.txt";

        // initialize services
        inventoryService = new InventoryService(inventoryPath, auditPath, 5);
        dealerService = new DealerService(dealersPath);
        cart = new Cart();

        // load data
        inventoryService.loadParts();
        dealerService.loadDealers();

        System.out.println("Loaded " + inventoryService.getTotalCount() + " parts");
        System.out.println("Loaded " + dealerService.getTotalDealers() + " dealers");
    }
}