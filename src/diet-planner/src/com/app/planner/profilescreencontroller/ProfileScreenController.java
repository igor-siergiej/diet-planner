package com.app.planner.profilescreencontroller;

import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.calendarcontroller.CalendarController;
import com.app.planner.mainscreencontroller.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;


public class ProfileScreenController {

    private Profile profile;

    @FXML
    private TextArea profileDataTextArea;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void showProfileData() {
        profileDataTextArea.setText(profile.toString());
    }

    public void getProfile(ActionEvent event) {
        goToCalendarScreen(event, profile);
    }

    private void goToScreen(ActionEvent event, String fxmlFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            Main.setWindow(event, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader goToScreenWithProfile(ActionEvent event, String fxmlFilePath) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setWindow(event, root);
        return loader;
    }

    private void goToCalendarScreen(ActionEvent event, Profile profile) {
        CalendarController calendarController = goToScreenWithProfile(event,"calendarcontroller/calendar.fxml").getController();
        calendarController.setProfile(profile);
        calendarController.load();
    }

    public void goToMainScreen(ActionEvent event) {
        goToScreen(event,"mainscreencontroller/mainScreen.fxml");
    }
}
