package com.kursowa.flowers;

import com.kursowa.flowers.controllers.enums.StageSrc;
import com.kursowa.flowers.models.Bouquet;
import com.kursowa.flowers.models.Flower;
import com.kursowa.flowers.models.FlowerType;
import com.kursowa.flowers.repositories.FlowerTypeRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class MyMain extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent root;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(FlowersApplication.class);
        springContext
                .getAutowireCapableBeanFactory()
                .autowireBeanProperties(
                        this,
                        AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
                        true
                );
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(StageSrc.MAIN_MENU.getUrl()));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }


    public static void main(String[] args) {
        launch(FlowersApplication.class, args);
    }

}

@Component
class Smth {
    private final FlowerTypeRepo flowerTypeRepo;

    public Smth(FlowerTypeRepo flowerTypeRepo) {
        this.flowerTypeRepo = flowerTypeRepo;
    }

    public void dosmth(){
        flowerTypeRepo.save(FlowerType.builder().name("Підсніжник").build());
    }
}
