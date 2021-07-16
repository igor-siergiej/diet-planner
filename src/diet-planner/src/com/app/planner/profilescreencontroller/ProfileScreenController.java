package com.app.planner.profilescreencontroller;

import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.calendarcontroller.CalendarController;
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

    public void getProfile(ActionEvent event) throws IOException {
        goToCalendarScreen(event,profile);
    }

    private void goToCalendarScreen(ActionEvent event, Profile profile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/planner/calendarcontroller/calendar.fxml"));
            Parent root = loader.load();//if profile is used in the initialize method it will crash because the profile is not loaded yet

            CalendarController calendarController = loader.getController();
            calendarController.setProfile(profile);
            calendarController.load();

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/com/app/planner/style.css");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void showProfileData() {
        profileDataTextArea.setText(profile.toString());
    }

}
