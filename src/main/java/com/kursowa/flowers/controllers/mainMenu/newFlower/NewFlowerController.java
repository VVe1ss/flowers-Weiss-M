package com.kursowa.flowers.controllers.mainMenu.newFlower;

import com.kursowa.flowers.controllers.SceneOpener;
import com.kursowa.flowers.controllers.enums.StageSrc;
import com.kursowa.flowers.models.Flower;
import com.kursowa.flowers.models.FlowerType;
import com.kursowa.flowers.repositories.FlowerRepo;
import com.kursowa.flowers.repositories.FlowerTypeRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewFlowerController extends SceneOpener {

    @Autowired
    private FlowerTypeRepo flowerTypeRepo;

    @Autowired
    private FlowerRepo flowerRepo;

    private List<FlowerType> flowerTypes;
    
    private Flower flower;
    @FXML
    private Button BackButton;

    @FXML
    private Button CreateButton;

    @FXML
    private TextField lengthField;

    @FXML
    private TextField levelOfFreshnessField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    void initialize(){
        flowerTypes = flowerTypeRepo.findAll();
        List<String> names = flowerTypes.stream().map(FlowerType::getName).toList();
        typeChoiceBox.setItems(FXCollections.observableList(names));
        typeChoiceBox.setValue(names.get(0));

        levelOfFreshnessField.textProperty().addListener(doubleInput(levelOfFreshnessField));
        priceField.textProperty().addListener(numberInput(priceField));
        lengthField.textProperty().addListener(numberInput(lengthField));

        if (data.isEditFlower()) {
            data.setEditFlower(false);
            flower = data.getCurrentFlower();
            setFieldValues();
        } else {
            flower = new Flower();
        }
        BackButton.setOnAction(actionEvent -> openNewScene(BackButton, StageSrc.FLOWERS));
        CreateButton.setOnAction(actionEvent -> collectData());
    }

    private void collectData() {
        try {
            if (fieldsBlank())
                throw new RuntimeException("усі поля повинні містити значення");
            if (Double.parseDouble(levelOfFreshnessField.getText())>1)
                throw new RuntimeException("відсоток свіжості повинен бути від 0 до 1");
            flower.setName(nameField.getText());
            flower.setPricePerFlower(Integer.parseInt(priceField.getText()));
            flower.setLength(Integer.parseInt(lengthField.getText()));
            flower.setLevelOfFreshness(Double.parseDouble(levelOfFreshnessField.getText()));
            flower.setType(flowerTypes.stream().filter(flowerType -> flowerType.getName().equals(typeChoiceBox.getValue())).findAny().get());
            flowerRepo.save(flower);
            openNewScene(CreateButton, StageSrc.FLOWERS);
        } catch (Exception e){
            openDialog(e.getMessage());
        }
    }

    private boolean fieldsBlank() {
        return nameField.getText().equals("") || priceField.getText().equals("") || lengthField.getText().equals("") || levelOfFreshnessField.getText().equals("");
    }

    private void setFieldValues() {
        nameField.setText(flower.getName());
        priceField.setText(String.valueOf(flower.getPricePerFlower()));
        lengthField.setText(String.valueOf(flower.getLength()));
        levelOfFreshnessField.setText(String.valueOf(flower.getLevelOfFreshness()));
        typeChoiceBox.setValue(flowerTypes.stream().filter(flowerType -> flowerType.getName().equals(flower.getType().getName())).findAny().get().getName());

    }
}
