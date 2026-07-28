package com.example.java_cw;

import com.example.java_cw.model.AuditEntry;
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

    }
}
