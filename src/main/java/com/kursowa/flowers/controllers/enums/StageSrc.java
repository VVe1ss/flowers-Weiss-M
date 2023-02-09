package com.kursowa.flowers.controllers.enums;

public enum StageSrc {
    MAIN_MENU("/static/mainMenu.fxml"), FLOWERS("/static/flowers.fxml"), BOUQUET("/static/bouquet.fxml"), NEW_FLOWER("/static/newFlower.fxml"), EDIT_BOUQUET("/static/editBouquet.fxml");

    private final String url;

    StageSrc(String location) {
        this.url = location;
    }

    public String getUrl() {
        return url;
    }
}
