package com.app.planner.configcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigScreenController {

    public void goToMainScreen(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/com/app/planner/mainscreencontroller/mainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        mainScreenScene.getStylesheets().add("com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) /2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }
}
