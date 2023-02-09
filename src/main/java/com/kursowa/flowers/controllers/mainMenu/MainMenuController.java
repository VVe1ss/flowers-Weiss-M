package com.kursowa.flowers.controllers.mainMenu;

import com.kursowa.flowers.controllers.SceneOpener;
import com.kursowa.flowers.controllers.enums.StageSrc;
import com.kursowa.flowers.models.Bouquet;
import com.kursowa.flowers.repositories.BouquetRepo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainMenuController extends SceneOpener {
    @FXML
    private Button bouquetButton;

    @FXML
    private Button flowersButton;

    @FXML
    void initialize(){
        flowersButton.setOnAction(actionEvent -> openNewScene(flowersButton, StageSrc.FLOWERS));

        bouquetButton.setOnAction(actionEvent -> {
            openNewScene(bouquetButton, StageSrc.BOUQUET);
        });
    }

}
