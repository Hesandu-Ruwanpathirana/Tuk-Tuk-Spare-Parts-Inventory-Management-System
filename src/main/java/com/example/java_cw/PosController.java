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




}
