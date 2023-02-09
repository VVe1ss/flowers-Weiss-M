package com.kursowa.flowers.controllers;

import com.kursowa.flowers.models.Bouquet;
import com.kursowa.flowers.models.Flower;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Data {
    Bouquet currentBouquet;
    Flower currentFlower;
    String dialogText;
    boolean editFlower;
    boolean stoneInNecklace;
    boolean addingToNecklace;
}
