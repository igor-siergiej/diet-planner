package com.app.planner.profilescreencontroller;

import com.app.planner.DatabaseConnection;
import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.addentrycontroller.AddEntryController;
import com.app.planner.calendarcontroller.CalendarController;
import com.app.planner.optionscontroller.OptionsScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ProfileScreenController {
    private Profile profile;

    @FXML
    private Pane mainPane;

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

    @FXML
    private Label messageLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    public void setProfile(Profile profile) {
        this.profile = profile;
        profileNameLabel.setText(profile.getProfileName());
        loadLabels();
    }

    public void loadLabels() {
        sexLabel.setText(profile.getSex());
        ageLabel.setText(String.valueOf(profile.getAge()));
        if (profile.getUsername() == null) {
            usernameLabel.setText("-");
            passwordLabel.setText("-");
        } else {
            usernameLabel.setText(profile.getUsername());
            passwordLabel.setText(profile.getPassword());
        }

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

    public void saveProfileToFile() {
        profile.saveToFile(Main.chooseSaveFile(mainPane));
        messageLabel.setId("successfullMessage");
        messageLabel.setText("Successfully saved to file");
    }

    public void saveProfileToDB() {
        if (profile.getUsername() != null) {
            if (DatabaseConnection.saveProfileToDb(profile.getUsername(),profile)) {
                messageLabel.setId("successfullMessage");
                messageLabel.setText("Successfully saved to DB");
            } else {
                messageLabel.setId("successfullMessage");
                messageLabel.setText("Error in saving to DB");
            }
        } else {
            messageLabel.setId("successfullMessage");
            messageLabel.setText("This profile does not have an account");
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

    public void goToOptionsScreen(ActionEvent event) {
        OptionsScreenController optionsScreenController = goToScreenWithProfile(event,"optionscontroller/OptionsScreen.fxml").getController();
        optionsScreenController.setProfile(profile);
    }

    private void goToCalendarScreen(ActionEvent event, Profile profile) {
        CalendarController calendarController = goToScreenWithProfile(event,"calendarcontroller/CalendarScreen.fxml").getController();
        calendarController.setProfile(profile);
        calendarController.load();
    }

    public void goToViewNutrientsScreen(ActionEvent event) {
        ViewNutrientsController viewNutrientsController = goToScreenWithProfile(event,"viewnutrientscontroller/ViewNutrientsScreen.fxml").getController();
        viewNutrientsController.setProfile(profile);
    }

    public void goToAddEntryScreen(ActionEvent event) {
        AddEntryController addEntryController = goToScreenWithProfile(event,"addentrycontroller/AddEntryScreen.fxml").getController();
        addEntryController.setProfile(profile);
    }

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.setProfile(profile);
    }

    public void goToMainScreen(ActionEvent event) {
        goToScreen(event,"mainscreencontroller/MainScreen.fxml");
    }
}
