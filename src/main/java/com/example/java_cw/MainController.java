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

    @FXML
    private TabPane mainTabPane;
    @FXML
    private TableView<Part> inventoryTable;
    @FXML
    private TableColumn<Part, String> colPartId;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, String> colBrand;
    @FXML
    private TableColumn<Part, Double> colPrice;
    @FXML
    private TableColumn<Part, Integer> colQuantity;
    @FXML
    private TableColumn<Part, String> colCategory;
    @FXML
    private TableColumn<Part, String> colDate;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> categoryFilter;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;

    @FXML
    private Label totalCountLabel;
    @FXML
    private Label totalValueLabel;
    @FXML
    private Label lowStockLabel;

    private InventoryService inventoryService;
    private DealerService dealerService;
    private Cart cart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String inventoryPath = "src/main/resources/com/example/java_cw/inventory_legacy.txt";
        String dealersPath = "src/main/resources/com/example/java_cw/dealers_legacy.txt";
        String auditPath = "src/main/resources/com/example/java_cw/audit_log.txt";

        inventoryService = new InventoryService(inventoryPath, auditPath, 5);
        dealerService = new DealerService(dealersPath);
        cart = new Cart();

        inventoryService.loadParts();
        dealerService.loadDealers();


        setupCategoryFilter();

        refreshTable();

        refreshLowStock();

        setupTableColumns();


        System.out.println("Loaded " + inventoryService.getTotalCount() + " parts");
        System.out.println("Loaded " + dealerService.getTotalDealers() + " dealers");
    }


    public void refreshLowStock() {
        List<Part> lowStock = inventoryService.getLowStockParts();

        if (lowStock.isEmpty()) {
            lowStockLabel.setText("All parts are sufficiently stocked.");
        } else {
            String message = "";
            for (int i = 0; i < lowStock.size(); i++) {
                Part p = lowStock.get(i);
                message += p.partId + " - " + p.partName + " (Qty: " + p.quantity + ")";
                if (i < lowStock.size() - 1) {
                    message += ",  ";
                }
            }
            lowStockLabel.setText(message);
        }
    }

    @FXML
    public void onSearchClicked() {
        String keyword = searchField.getText().trim();

        String category = "";
        if (categoryFilter.getValue() != null &&
                !categoryFilter.getValue().equals("All Categories")) {
            category = categoryFilter.getValue();
        }

        double minPrice = 0;
        double maxPrice = 999999;

        try {
            if (!minPriceField.getText().trim().isEmpty()) {
                minPrice = Double.parseDouble(minPriceField.getText().trim());
            }
            if (!maxPriceField.getText().trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceField.getText().trim());
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid price entered. Please enter numbers only.");
            return;
        }

        List<Part> results = inventoryService.searchParts(keyword, category, minPrice, maxPrice);
        ObservableList<Part> observableList = FXCollections.observableArrayList(results);
        inventoryTable.setItems(observableList);

        totalCountLabel.setText("Results: " + results.size());
    }

    public void setupCategoryFilter() {
        categoryFilter.getItems().add("All Categories");
        categoryFilter.getItems().add("engine");
        categoryFilter.getItems().add("electrical");
        categoryFilter.getItems().add("brakes");
        categoryFilter.getItems().add("bodywork");
        categoryFilter.setValue("All Categories");
    }

    @FXML
    public void onResetClicked() {
        searchField.clear();
        categoryFilter.setValue("All Categories");
        minPriceField.clear();
        maxPriceField.clear();
        refreshTable();
    }

    public void refreshTable() {
        List<Part> sorted = inventoryService.getSortedParts();
        ObservableList<Part> observableList = FXCollections.observableArrayList(sorted);
        inventoryTable.setItems(observableList);

        totalCountLabel.setText("Total Parts: " + inventoryService.getTotalCount());
        totalValueLabel.setText("Total Value: Rs. " +
                String.format("%.2f", inventoryService.getTotalValue()));
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tuk Tuk Spare Parts");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setupTableColumns() {
        colPartId.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().partId));

        colPartName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().partName));

        colBrand.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().brand));

        colPrice.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().price).asObject());

        colQuantity.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().quantity).asObject());

        colCategory.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().category));

        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().dateAdded));
    }
    @FXML
    public void onAddPartClicked() {
        showAlert("Add Part coming later");
    }

    @FXML
    public void onEditPartClicked() {
        Part selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a part from the table first.");
            return;
        }
        showAlert("Edit Part coming later");
    }

    @FXML
    public void onDeletePartClicked() {
        Part selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a part from the table first.");
            return;
        }
        inventoryService.deletePart(selected.partId);
        refreshTable();
        refreshLowStock();
        showAlert("Part " + selected.partId + " deleted successfully.");
    }


}

