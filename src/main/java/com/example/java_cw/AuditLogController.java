package com.example.java_cw;

import com.example.java_cw.model.AuditEntry;
import com.example.java_cw.util.AuditLogger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class AuditLogController implements Initializable {
    @FXML private TableView<AuditEntry> auditLogTable;
    @FXML private TableColumn<AuditEntry, String> colLogTimestamp;
    @FXML private TableColumn<AuditEntry, String> colLogAction;
    @FXML private TableColumn<AuditEntry, String> colLogItemCode;
    @FXML private TableColumn<AuditEntry, String> colLogDetails;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colLogTimestamp.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimestamp()));
        colLogAction.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAction()));
        colLogItemCode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemCode()));
        colLogDetails.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));

        refreshAuditLog();
    }
    public void refreshAuditLog() {
        auditLogTable.setItems(FXCollections.observableArrayList(AuditLogger.getEntries()));
    }
}

