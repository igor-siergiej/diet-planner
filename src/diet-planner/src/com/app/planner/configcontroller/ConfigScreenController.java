package com.app.planner.configcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ConfigScreenController {

    public void goToMainScreen(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("../mainscreencontroller/mainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        mainScreenScene.getStylesheets().add("com/app/planner/mainscreencontroller/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();

        System.out.println("works");
    }
}
