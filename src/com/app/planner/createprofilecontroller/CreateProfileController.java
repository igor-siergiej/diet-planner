package com.app.planner.createprofilecontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.Profile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.awt.event.ActionEvent;

public class CreateProfileController extends BaseScreenController {

    Profile profile;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private CheckBox pregnantCheckBox;

    @FXML
    private CheckBox breastfeedingCheckBox;

    @FXML
    private RadioButton femaleRadioButton;

    public void initialiseCredentials(String email, String password) {
        this.profile = new Profile();
        this.profile.setEmail(email);
        this.profile.setPassword(password);
    }

    public void handleFemaleCheckBoxes() {
        if (femaleRadioButton.isSelected()) {
            pregnantCheckBox.setDisable(false);
            breastfeedingCheckBox.setDisable(false);
        } else {
            pregnantCheckBox.setSelected(false);
            breastfeedingCheckBox.setSelected(false);
            pregnantCheckBox.setDisable(true);
            breastfeedingCheckBox.setDisable(true);
        }
    }
}
