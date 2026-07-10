package com.example.java_cw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/java_cw/gui-view.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);
        stage.setTitle("Malabe Tuk-Tuk & Three-Wheeler Spares Depot");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}