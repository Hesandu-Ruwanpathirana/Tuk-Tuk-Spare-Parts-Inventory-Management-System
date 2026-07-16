package com.example.java_cw;

import com.example.java_cw.model.Cart;
import com.example.java_cw.model.Part;
import com.example.java_cw.service.DealerService;
import com.example.java_cw.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;


import java.util.Optional;

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
    @FXML
    private TextField thresholdField;

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

        setupTableColumns();

        setupCategoryFilter();

        refreshTable();

        refreshLowStock();


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
                message += p.getPartId() + " - " + p.getPartName() + " (Qty: " + p.getQuantity() + ")";
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
        List<Part> sortedResults = inventoryService.sortParts(results);

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
                new SimpleStringProperty(data.getValue().getPartId()));

        colPartName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getPartName()));

        colBrand.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getBrand()));

        colPrice.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrice()).asObject());

        colQuantity.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        colCategory.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCategory()));

        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDateAdded()));
    }
    @FXML
    public void onAddPartClicked() {
        showPartDialog(null);
    }

    @FXML
    public void onEditPartClicked() {
        Part selected = inventoryTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("You should pick a part from the table first and then you can edit it.");
            return;

        }
        showPartDialog(selected);
    }

    @FXML
    public void onDeletePartClicked() {
        Part selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a part from the table first.");
            return;
        }
        inventoryService.deletePart(selected.getPartId());
        refreshTable();
        refreshLowStock();
        showAlert("Part " + selected.getPartId() + " deleted successfully.");
    }
    public void showPartDialog(Part existingPart) {
        Dialog<Part> dialog =  new Dialog<>();

        if (existingPart == null) {
            dialog.setTitle("Add new Part");
            dialog.setHeaderText("Enter details for the new part");

        } else {
            dialog.setTitle("Edit Part");
            dialog.setHeaderText("Enter details for " + existingPart.getPartId());

        }

        ButtonType saveButtonType = new ButtonType("Save",ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField partIdField = new TextField();
        partIdField.setPromptText("eg: P012");

        TextField partNameField = new TextField();
        partNameField.setPromptText("eg: Bajaj Brake Pad");

        TextField brandField = new TextField();
        brandField.setPromptText("eg: Bajaj");

        TextField priceField = new TextField();
        priceField.setPromptText("eg: 1500.00");

        TextField quantityField = new TextField();
        quantityField.setPromptText("eg: 10");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("engine","electrical","brakes","bodywork");
        categoryBox.setPromptText("Select category: ");

        TextField dateField = new TextField();
        dateField.setPromptText("eg: 15-09-2009");

        TextField imageField = new TextField();
        imageField.setPromptText("eg: image.jpg");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #CC2229; -fx-font-weight: bold;");
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(300);

        if (existingPart != null) {
            partIdField.setText(existingPart.getPartId());
            partIdField.setEditable(false);
            partNameField.setText(existingPart.getPartName());
            brandField.setText(existingPart.getBrand());
            priceField.setText(String.valueOf(existingPart.getPrice()));
            quantityField.setText(String.valueOf(existingPart.getQuantity()));
            categoryBox.setValue(existingPart.getCategory());
            dateField.setText(existingPart.getDateAdded());
            imageField.setText(existingPart.getImagePath());

        }

        grid.add(new Label("Part ID:"),0,0);
        grid.add(partIdField,1,0);
        grid.add(new Label("Part Name:"),0,1);
        grid.add(partNameField,1,1);
        grid.add(new Label("Brand:"),0,2);
        grid.add(brandField,1,2);
        grid.add(new Label("Price (Rs.):"),0,3);
        grid.add(priceField,1,3);
        grid.add(new Label("Quantity:"),0,4);
        grid.add(quantityField,1,4);
        grid.add(new Label("Category:"),0,5);
        grid.add(categoryBox,1,5);
        grid.add(new Label("Date Added:"),0,6);
        grid.add(dateField,1,6);
        grid.add(new Label("Image Path:"),0,7);
        grid.add(imageField,1,7);

        dialog.getDialogPane().setContent(grid);

        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(ActionEvent.ACTION,event -> {
            String error = validatePartFields(
                    partIdField.getText().trim(),
                    partNameField.getText().trim(),
                    priceField.getText().trim(),
                    quantityField.getText().trim(),
                    categoryBox.getValue(),
                    dateField.getText().trim(),
                    existingPart

            );
            if (error != null) {
                errorLabel.setText(error);
                event.consume();

            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String partId = partIdField.getText().trim();
                String partName = partNameField.getText().trim();
                String brand = brandField.getText().trim();
                String priceText = priceField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String category = categoryBox.getValue().trim();
                String dateAdded = dateField.getText().trim();
                String imagePath = imageField.getText().trim();

                if (partId.isEmpty() || partName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || category == null) {
                    showAlert("Part ID, Name, Price, Quantity and Category is required");
                    return null;

                }

                if (existingPart == null) {
                    for (int i = 0; i < inventoryService.parts.size(); i++) {
                        if (inventoryService.parts.get(i).getPartId().equalsIgnoreCase(partId)) {
                            showAlert("Part ID '" + partId + "' already exists. Please use a unique ID");
                            return null;
                        }
                    }
                }
                    double price = 0;
                    int quantity = 0;

                    try {
                        price = Double.parseDouble(priceText);

                    } catch (NumberFormatException e) {
                        showAlert("Price must be valid number ");
                        return null;
                    }

                    try {
                        quantity = Integer.parseInt(quantityText);
                    } catch (NumberFormatException e) {
                        showAlert("Quantity must be a valid number ");
                        return null;
                    }
                    if (price <= 0) {
                        showAlert("Price must be greater than zero ");
                        return null;
                    }
                    if (quantity < 0) {
                        showAlert("Quantity cannot be negative ");
                        return null;

                    }
                    return new Part(partId, partName, brand, price, quantity, category.toLowerCase(), dateAdded, imagePath);

                    }
                    return null;
                });

                Optional<Part> result = dialog.showAndWait();

                result.ifPresent(part -> {
                    if (existingPart == null) {
                        inventoryService.addPart(part);
                        showAlert("Part " + part.getPartId() + " added successfully");
                    } else {
                        inventoryService.updatePart(existingPart.getPartId(),part);
                        showAlert("Part " + part.getPartId() + " updated successfully");
                    }
                    refreshTable();
                    refreshLowStock();
                });
        }
        public String validatePartFields(String partId,String partName,String priceText, String quantityText, String category, String dateAdded, Part existingPart) {
            if (partId.isEmpty() || partName.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || category == null) {
                return "Part ID, Name, Price, Quantity and Category is required.";

            }
            if (existingPart == null) {
                for (int i = 0; i < inventoryService.parts.size(); i++) {
                    if(inventoryService.parts.get(i).getPartId().equalsIgnoreCase(partId)) {
                        return "Part ID " + partId + " already exists. Please use a unique ID.";
                    }
                }
            }
            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                return "Price must be a valid number.";

            }
            if (price <= 0) {
                return "Price must be greater than zero.";

            }
            int quantity;
            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException e) {
                return "Quantity must be a valid whole number.";
            }
            if (quantity < 0) {
                return "Quantity cannot be negative";
            }

            if (!dateAdded.isEmpty() && !isValidDate(dateAdded)) {
                return "Date must be in DD-MM-YYYY format and be a real date (e.g. 15-09-2023).";
            }
            return null;
        }

        public boolean isValidDate(String dateText) {
            if (!dateText.matches("\\d{2}-\\d{2}-\\d{4}")) {
                return false;
            }
            return true;
        }

        public void onSetThresholdClicked() {
            String text = thresholdField.getText().trim();

            if (text.isEmpty()) {
                showAlert("Please enter a threshold number.");
                return;
            }
            int threshold;
            try {
                threshold = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                showAlert("Threshold must be a valid number.");
                return;
            }
            if (threshold < 0) {
                System.out.println("Threshold cannot be negative.");
                return;

            }
            inventoryService.lowStockThreshold = threshold;
            refreshLowStock();

            List<Part> lowStockParts = inventoryService.getLowStockParts();
            ObservableList<Part> observableList = FXCollections.observableArrayList(lowStockParts);
            inventoryTable.setItems(observableList);

        }

    }









