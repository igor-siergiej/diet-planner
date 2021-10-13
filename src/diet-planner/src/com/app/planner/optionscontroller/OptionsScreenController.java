package com.app.planner.optionscontroller;

import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.ThemeType;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class OptionsScreenController {
    private Profile profile;

    @FXML
    private ComboBox themeComboBox;

    public void goToProfileScreen(ActionEvent event) { // TODO this settings screen has to be accessed after a profile is created/loaded because option object depends on profile object
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.setProfile(this.profile);
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setTheme() {
        profile.getOptions().setCSSFile(ThemeType.valueOf((String) themeComboBox.getValue()));
    }

    private FXMLLoader goToScreenWithProfile(ActionEvent event, String fxmlFilePath) { //TODO try to optimise this again
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

    private void goToScreen(ActionEvent event, String fxmlFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            Main.setWindow(event, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
