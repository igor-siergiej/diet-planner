package com.app.planner.createprofilecontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.InputValidation;
import com.app.planner.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;


public class CreateProfileController extends BaseScreenController {

    Profile profile;

    @FXML
    private CheckBox pregnantCheckBox;

    @FXML
    private CheckBox breastfeedingCheckBox;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField weightTextField;

    public void initialise(String email, String password) {
        this.profile = new Profile();
        this.profile.setEmail(email);
        this.profile.setPassword(password);
        setAgeTextFieldEventHandler();
        setHeightTextFieldEventHandler();
        setWeightTextFieldEventHandler();
    }

    public void setAgeTextFieldEventHandler() {
        ageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            ageTextField.setText(InputValidation.ageValidation(newValue));
        });
    }

    public void setHeightTextFieldEventHandler() {
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            heightTextField.setText(InputValidation.heightValidation(newValue));
        });
    }

    public void setWeightTextFieldEventHandler() {
        weightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            weightTextField.setText(InputValidation.weightValidation(newValue));
        });
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

    public void createNewProfile(ActionEvent event) {
        // get values from UI and add them to profile

        goToProfileScreen(event,this.profile);
    }
}
