package com.kursowa.flowers.controllers;

import com.kursowa.flowers.controllers.enums.StageSrc;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Setter
public class SceneOpener {
    @Autowired
    public ApplicationContext applicationContext;
    @Autowired
    public Data data;

     @SneakyThrows
     public void openNewScene(Node anyNode, StageSrc stageSrc){
         anyNode.getScene().getWindow().hide();
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stageSrc.getUrl()));
         fxmlLoader.setControllerFactory(applicationContext::getBean);
         Parent root = fxmlLoader.load();
         Stage stage = new Stage();
         stage.setScene(new Scene(root, 700, 400));
         stage.setResizable(false);
         stage.show();
    }

    public void openDialog(String text){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/static/dialog.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        data.setDialogText(text);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    protected static ChangeListener<String> numberInput(TextField node){
        return new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    node.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        };
    }

    protected static ChangeListener<String> doubleInput(TextField node){
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-1](\\.[0-9]*)?")) {
                    node.setText(newValue.replaceAll("[^\\d.]", ""));
                    StringBuilder aus = new StringBuilder(newValue);
                    boolean firstPointFound = false;
                    for (int i = 0; i < aus.length(); i++){
                        if(aus.charAt(i) == '.') {
                            if(!firstPointFound)
                                firstPointFound = true;
                            else
                                aus.deleteCharAt(i);
                        }
                    }
                    newValue = aus.toString();
                }
            }
        };
    }
}
