package com.app.planner.profilescreencontroller;

import com.app.planner.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class ProfileScreenController {

    private Profile profile;

    @FXML
    private TextArea profileDataTextArea;

    public void getProfile(Profile profile) {
        this.profile = profile;
    }

    public void showProfileData() {
        profileDataTextArea.setText(profile.toString());
    }


}
