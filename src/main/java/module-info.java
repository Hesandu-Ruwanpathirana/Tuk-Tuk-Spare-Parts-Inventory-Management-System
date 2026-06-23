module com.example.java_cw {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.java_cw to javafx.fxml;
    exports com.example.java_cw;
}