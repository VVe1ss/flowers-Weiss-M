package com.kursowa.flowers.controllers.mainMenu.editBouquet;

import com.kursowa.flowers.comparators.FlowersComparator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class EditBouquet extends SceneOpener {

    @Autowired
    private FlowerRepo flowerRepo;

    @Autowired
    private BouquetRepo bouquetRepo;

    private Bouquet bouquet;

    private List<Flower> inFlowers;
    private List<Flower> outFlowers;
    @FXML
    private Button backButton;

    @FXML
    private TextArea inTextArea;

    @FXML
    private TextArea outTextArea;

    @FXML
    private Button sortButton;

    @FXML
    private Button toInButton;

    @FXML
    private Button toOutButton;

    @FXML
    private TextField idField;

    @FXML
    void initialize(){

        mapFields();

        sortButton.setOnAction(actionEvent -> sortFlowers());

        toInButton.setOnAction(actionEvent -> {
            try {
                Flower temp = outFlowers.stream().filter(flower -> flower.getId() == Long.parseLong(idField.getText())).findFirst().orElse(null);
                if (temp == null)
                    throw new RuntimeException("поза букетом немає квітки з таким id");
                temp.setBouquet(bouquet);
                temp.setPosInBouquet(bouquet.getNextFlowerPos());
                bouquet.setNextFlowerPos(bouquet.getNextFlowerPos()+1);
                flowerRepo.save(temp);
                bouquetRepo.save(bouquet);
                mapFields();
            } catch (Exception e){openDialog(e.getMessage());}
        });

        toOutButton.setOnAction(actionEvent -> {
            try {
                Flower temp = inFlowers.stream().filter(flower -> flower.getId() == Long.parseLong(idField.getText())).findFirst().orElse(null);
                if (temp == null)
                    throw new RuntimeException("в букеті немає квітки з таким id");
                temp.setBouquet(null);
                temp.setPosInBouquet(0);
                flowerRepo.save(temp);
                mapFields();
            } catch (Exception e){openDialog(e.getMessage());}
        });

        backButton.setOnAction(actionEvent -> openNewScene(backButton, StageSrc.BOUQUET));
    }

    private void sortFlowers() {
        Collection<Flower> flowers = new TreeSet<>(new FlowersComparator());
        flowers.addAll(inFlowers);
        List<Flower> flowerList = flowers.stream().toList();
        for (int i = 0; i < flowers.size(); i++) {
            flowerList.get(i).setPosInBouquet(i);
            flowerRepo.save(flowerList.get(i));
        }
        openDialog("відсортовано за рівнем свіжості");
        mapFields();
    }

    private void mapFields() {
        bouquet = bouquetRepo.getBouquetById(1L);
        inFlowers = flowerRepo.getFlowerByBouquetAndLengthBetweenOrderByPosInBouquet(bouquet, 0, Integer.MAX_VALUE);
        outFlowers = flowerRepo.findAllByBouquetIsNull();

        inTextArea.setText(createTextFromList(inFlowers));
        outTextArea.setText(createTextFromList(outFlowers));
    }

    private String createTextFromList(List<Flower> flowers) {
        StringBuilder sb = new StringBuilder();
        flowers.forEach(sb::append);
        return sb.toString();
    }
}
