package com.kursowa.flowers.controllers.mainMenu.bouquet;

import com.kursowa.flowers.controllers.SceneOpener;
import com.kursowa.flowers.controllers.enums.StageSrc;
import com.kursowa.flowers.models.Bouquet;
import com.kursowa.flowers.models.Flower;
import com.kursowa.flowers.repositories.BouquetRepo;
import com.kursowa.flowers.repositories.FlowerRepo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BouquetController extends SceneOpener {

    private Bouquet bouquet;

    int start = 0;
    int end = Integer.MAX_VALUE;

    @Autowired
    private BouquetRepo bouquetRepo;

    @Autowired
    private FlowerRepo flowerRepo;

    @FXML
    private Button backButton;

    @FXML
    private TextField bound1;

    @FXML
    private TextField bound2;

    @FXML
    private Button deleteAllStonesButton;

    @FXML
    private Button editButton;

    @FXML
    private Button findButton;

    @FXML
    private Text priceTextField;

    @FXML
    private TextArea flowersTextArea;

    @FXML
    void initialize() {
        if (bouquetRepo.getBouquetById(1L) == null)
            bouquetRepo.save(Bouquet.builder().name("default").build());
        bouquet = bouquetRepo.getBouquetById(1L);

        updateFields();

        bound1.textProperty().addListener(numberInput(bound1));
        bound2.textProperty().addListener(numberInput(bound2));

        findButton.setOnAction(actionEvent -> {
            if (!bound1.getText().isBlank())
                start = Integer.parseInt(bound1.getText());
            else
                start = 0;
            if (!bound2.getText().isBlank())
                end = Integer.parseInt(bound2.getText());
            else
                end = Integer.MAX_VALUE;
            flowersTextArea.setText(getText());
        });

        deleteAllStonesButton.setOnAction(actionEvent -> {
            bouquet.getFlowers().forEach(flower -> {
                flower.setBouquet(null);
                flowerRepo.save(flower);
            });
            bouquet.setNextFlowerPos(0);
            bouquetRepo.save(bouquet);
            updateFields();
        });

        editButton.setOnAction(actionEvent -> openNewScene(editButton, StageSrc.EDIT_BOUQUET));

        backButton.setOnAction(actionEvent -> openNewScene(backButton, StageSrc.MAIN_MENU));
    }

    private void updateFields (){
        flowersTextArea.setText(getText());
        priceTextField.setText("Ціна букету : " + flowerRepo.getFLowersByBouquet(bouquet).stream().mapToInt(Flower::getPricePerFlower).sum()
        );
    }

    private String getText() {
        StringBuilder sb = new StringBuilder();
        flowerRepo.getFlowerByBouquetAndLengthBetweenOrderByPosInBouquet(bouquet, start, end).forEach(sb::append);
        return sb.toString();
    }
}
