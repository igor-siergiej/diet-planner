package com.app.planner.profilescreencontroller;

import com.app.planner.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;


public class ProfileScreenController {

    private Profile profile;

    @FXML
    private TextArea profileDataTextArea;

    public void goToCalendarScreen(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/com/app/planner/calendarcontroller/calendar.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        mainScreenScene.getStylesheets().add("com/app/planner/style.css");
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    public void getProfile(Profile profile) {
        this.profile = profile;
    }

    public void showProfileData() {
        profileDataTextArea.setText(profile.toString());
    }




}
