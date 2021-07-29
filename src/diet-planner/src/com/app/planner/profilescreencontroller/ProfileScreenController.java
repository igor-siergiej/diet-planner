package com.app.planner.profilescreencontroller;

import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.calendarcontroller.CalendarController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import java.io.IOException;

public class ProfileScreenController {
    private Profile profile;

    @FXML
    private Label profileNameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label sexLabel;

    @FXML
    private Label breastfeedingLabel;

    @FXML
    private Label pregnantLabel;

    public void setProfile(Profile profile) {
        this.profile = profile;
        profileNameLabel.setText(profile.getProfileName());
    }

    public void loadLabels() {
        sexLabel.setText(profile.getSex());
        ageLabel.setText(String.valueOf(profile.getAge()));


        if (profile.isBreastFeeding()) {
            breastfeedingLabel.setText("Yes");
        } else {
            breastfeedingLabel.setText("No");
        }

        if (profile.isPregnant()) {
            pregnantLabel.setText("Yes");
        } else {
            pregnantLabel.setText("No");
        }
    }

    public void goToCalendarButton(ActionEvent event) {
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

    public void saveProfile() {
        profile.saveToFile(Main.chooseFile("save"));
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

    public void goToViewNutrientsScreen(ActionEvent event) {
        ViewNutrientsController viewNutrientsController = goToScreenWithProfile(event,"viewnutrientscontroller/viewNutrientsScreen.fxml").getController();
        viewNutrientsController.setProfile(profile);
    }

    public void goToProfileDetailsScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/profileDetailsScreen.fxml").getController();
        profileScreenController.setProfile(profile);
        profileScreenController.loadLabels();
    }

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/profileScreen.fxml").getController();
        profileScreenController.setProfile(profile);
    }

    public void goToMainScreen(ActionEvent event) {
        goToScreen(event,"mainscreencontroller/mainScreen.fxml");
    }
}
