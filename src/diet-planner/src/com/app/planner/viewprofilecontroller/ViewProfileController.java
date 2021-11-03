package com.app.planner.viewprofilecontroller;

import com.app.planner.DatabaseConnection;
import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.addentrycontroller.AddEntryController;
import com.app.planner.calendarcontroller.CalendarController;
import com.app.planner.editprofilecontroller.EditProfileController;
import com.app.planner.optionscontroller.OptionsScreenController;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class ViewProfileController {
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

    public void initialize(Profile profile) {
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

    public void saveProfileToFile() {
        File file = Main.chooseSaveFile(mainPane);
        if (!(file == null)) {
            profile.saveToFile(file);
            messageLabel.setId("successfullMessage");
            messageLabel.setText("Successfully saved to file");
        } else {
            messageLabel.setId("unsuccessfullMessage");
            messageLabel.setText("Failed to save to file");
        }
    }

    public void saveProfileToDB() {
        if (profile.getUsername() != null) {
            if (DatabaseConnection.saveProfileToDb(profile.getUsername(), profile)) {
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

    public void goToEditProfileScreen(ActionEvent event) {
        EditProfileController editProfileController = goToScreenWithProfile(event, "editprofilecontroller/EditProfileScreen.fxml").getController();
        editProfileController.initialize(profile);
    }

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialize(profile);
    }

}
