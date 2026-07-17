package com.example.java_cw;

import com.example.java_cw.model.Cart;
import com.example.java_cw.model.CartItem;
import com.example.java_cw.model.Part;
import com.example.java_cw.service.InventoryService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class PosController implements Initializable{
    @FXML
    private ComboBox<Part> partSelector;
    @FXML
    private TextField quantityField;
    @FXML
    private Label posErrorLabel;
    @FXML
    private TableView<CartItem> cartTable;
    @FXML
    private TableColumn<CartItem,String> colCartPartName;
    @FXML
    private TableColumn<CartItem,Integer> colCartQty;
    @FXML
    private TableColumn<CartItem,Double> colCartPrice;
    @FXML
    private TableColumn<CartItem,Double> colCartSubTotal;
    @FXML
    private Label cartTotalLabel;

    private InventoryService inventoryService;
    private Cart cart;
    private String auditLogPath;
    private Runnable onInventoryChanged;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCartPartName.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getPart().getPartName()));
        colCartQty.setCellValueFactory((d -> new SimpleIntegerProperty(d.getValue().getQuantity()).asObject()));
        colCartPrice.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPart().getPrice()).asObject());
        colCartSubTotal.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getSubTotal()).asObject());


    partSelector.setConverter(new StringConverter<Part>() {
        @Override
        public String toString(Part p) {
            if (p == null) {
                return "";
            }
            return p.getPartId() + " - " + p.getPartName() + "(Stock: " + p.getQuantity() + ")";
        }

        @Override
        public Part fromString(String string) {
            return null;
        }
    });
    }
    public void setServices(InventoryService inventoryService, Cart cart, String auditLogPath, Runnable onInventoryChanged) {
        this.inventoryService = inventoryService;
        this.cart = cart;
        this.auditLogPath = auditLogPath;
        this.onInventoryChanged = onInventoryChanged;

        refreshPartSelector();
        refreshCartTable();
    }

    public void refreshPartSelector() {
        partSelector.setItems(FXCollections.observableArrayList(inventoryService.parts));
    }
    public void refreshCartTable() {
        cartTable.setItems(FXCollections.observableArrayList(cart.getItems()));
        cartTotalLabel.setText("Total: Rs. " + String.format("%.2f", cart.getTotal()));
    }

    @FXML
    public void onAddToCartClicked() {
        posErrorLabel.setText("");

        Part selectedPart = partSelector.getValue();
        if (selectedPart == null) {
            posErrorLabel.setText("Please select a part first.");
            return;
        }
        String quantityText = quantityField.getText().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            posErrorLabel.setText("Quantity must be a valid whole number.");
            return;

        }
        String result = cart.addItem(selectedPart,quantity);
        if (!result.equals("success")) {
            posErrorLabel.setText(result);
            return;
        }

        quantityField.clear();
        refreshCartTable();
    }

























}
