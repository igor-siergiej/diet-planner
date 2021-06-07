package com.app.planner.calendarcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarController {

    public void goToConfiguration(ActionEvent event) throws IOException {
        Parent configScreenParent = FXMLLoader.load(getClass().getResource("../configcontroller/configScreen.fxml"));
        Scene configScreenScene = new Scene(configScreenParent);
        configScreenScene.getStylesheets().add("com/app/planner/util/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(configScreenScene);
        window.show();
    }
}
