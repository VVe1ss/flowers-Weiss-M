package com.kursowa.flowers.controllers;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DialogController {
    @Autowired
    Data data;

    @FXML
    private Text dialogText;

    @FXML
    void initialize() {
        dialogText.setText(data.getDialogText());
    }
}
