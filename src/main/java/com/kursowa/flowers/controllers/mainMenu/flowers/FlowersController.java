package com.kursowa.flowers.controllers.mainMenu.flowers;

import com.kursowa.flowers.controllers.SceneOpener;
import com.kursowa.flowers.controllers.enums.StageSrc;
import com.kursowa.flowers.models.Flower;
import com.kursowa.flowers.repositories.FlowerRepo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlowersController extends SceneOpener {

    @Autowired
    private FlowerRepo flowerRepo;

    private List<Flower> flowers;

    @FXML
    private Button backButton;

    @FXML
    private Button editDeleteButton;

    @FXML
    private TextArea flowersTextArea;

    @FXML
    private TextField idField;

    @FXML
    private Button newFlowerButton;

    @FXML
    void initialize(){
        backButton.setOnAction(actionEvent -> openNewScene(backButton, StageSrc.MAIN_MENU));

        idField.textProperty().addListener(numberInput(idField));

        updateData();

        editDeleteButton.setOnAction(actionEvent -> {
            try {
                if (idField.getText() == null || idField.getText().equals(""))
                    throw new RuntimeException("поле id не може бути пустим");
                if (flowers.stream().noneMatch(flower -> flower.getId() == Long.parseLong(idField.getText())))
                    throw new RuntimeException("квітки з таким id немає");
                data.setCurrentFlower(flowerRepo.getFlowerById(Long.parseLong(idField.getText())));
                data.setEditFlower(true);
                openNewScene(editDeleteButton, StageSrc.NEW_FLOWER);
            } catch (Exception e){
                openDialog(e.getMessage());
            }
        });

        newFlowerButton.setOnAction(actionEvent -> openNewScene(newFlowerButton, StageSrc.NEW_FLOWER));
    }

    private void updateData() {
        flowers = flowerRepo.findAll();
        flowersTextArea.setText(createText(flowers));
    }

    private String createText(List<Flower> flowers) {
        StringBuilder sb = new StringBuilder();
        flowers.forEach(flower -> sb.append(flower.toString()));
        return sb.toString();
    }


}
