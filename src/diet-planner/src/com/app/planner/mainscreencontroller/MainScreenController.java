package com.app.planner.mainscreencontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    public void goToConfiguration(ActionEvent event) throws IOException {
        Parent configScreenParent = FXMLLoader.load(getClass().getResource("../configcontroller/configScreen.fxml"));
        Scene configScreenScene = new Scene(configScreenParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(configScreenScene);
        window.show();

        System.out.println("works");
    }
}
