package com.example.java_cw;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePopupController {
    @FXML
    private ImageView popupImageView;

    public void setImage(Image image) {
        popupImageView.setImage(image);
    }
}
