package com.example.java_cw;

import com.example.java_cw.model.Cart;
import com.example.java_cw.model.Part;
import com.example.java_cw.service.DealerService;
import com.example.java_cw.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class MainController implements Initializable {

    @FXML private TabPane mainTabPane;
    @FXML private Label lowStockLabel;
    private InventoryService inventoryService;
    private DealerService dealerService;
    private Cart cart;


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

//        setupTableColumns();


//        setupCategoryFilter();
//
//        refreshTable();
//
        refreshLowStock();


        System.out.println("Loaded " + inventoryService.getTotalCount() + " parts");
        System.out.println("Loaded " + dealerService.getTotalDealers() + " dealers");
    }
    public void refreshLowStock() {
        List<Part> lowStock = inventoryService.getLowStockParts();

        if (lowStock.isEmpty()) {
            lowStockLabel.setText("All parts are fully stocked.");
        }else{
            String message = "";
            for (int i = 0; i < lowStock.size(); i++) {
                Part p = lowStock.get(i);
                message += p.partId + " - " + p.partName + ", " +
                        " (Qty: " + p.quantity + ")";
                if (i < lowStock.size() - 1) {
                    message += ",  ";
                }
            }
            lowStockLabel.setText(message);
        }
    }

}