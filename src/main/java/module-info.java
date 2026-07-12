module com.example.java_cw {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens com.example.java_cw to javafx.fxml;
    exports com.example.java_cw;
}