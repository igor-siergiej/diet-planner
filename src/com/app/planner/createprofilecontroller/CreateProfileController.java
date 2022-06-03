package com.app.planner.createprofilecontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.InputValidation;
import com.app.planner.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.Validator;


public class CreateProfileController extends BaseScreenController {

    Profile profile;

    @FXML
    private Pane mainPane;

    @FXML
    private CheckBox pregnantCheckBox;

    @FXML
    private CheckBox breastfeedingCheckBox;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private ToggleGroup activityToggleGroup;

    @FXML
    private ToggleGroup sexToggleGroup;

    @FXML
    private TextField profileNameTextField;

    public void initialise(String email, String password) {
        this.profile = new Profile();
        this.profile.setEmail(email);
        this.profile.setPassword(password);
        setAgeTextFieldEventHandler();
        setHeightTextFieldEventHandler();
        setWeightTextFieldEventHandler();
        femaleRadioButton.setUserData("Female");
        maleRadioButton.setUserData("Male");
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
        System.out.println(sexToggleGroup.getSelectedToggle().getUserData());
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

    public boolean isFormCompleted() {
        boolean returnBoolean = false;

        boolean isActivityToggleGroupSelected = activityToggleGroup.getSelectedToggle() != null;
        boolean isSexToggleGroupSelected = sexToggleGroup.getSelectedToggle() != null;
        boolean isUsernameValid = InputValidation.usernameValidation(profileNameTextField.getText()) == "valid";
        boolean isAgeValid = !ageTextField.getText().isEmpty();
        boolean isHeightValid = !heightTextField.getText().isEmpty();
        boolean isWeightValid = !weightTextField.getText().isEmpty();

        if (isActivityToggleGroupSelected && isSexToggleGroupSelected && isUsernameValid &&
            isAgeValid && isHeightValid && isWeightValid) {
            returnBoolean = true;
        }
        // split this if into each part so a different notification can pop up
        return returnBoolean;
    }

    public void setErrorStyle() {
        // TODO
    }

    public void createNewProfile(ActionEvent event) {
        if (isFormCompleted()) {
            profile.setProfileName(profileNameTextField.getText());
            profile.setAge(Integer.parseInt(ageTextField.getText()));
            profile.setHeight(Integer.parseInt(heightTextField.getText()));
            profile.setWeight(Integer.parseInt(weightTextField.getText()));
            // TODO if sex is female check for null and get pregnant and breastfeeding checkbox
            profile.setSex((String) sexToggleGroup.getSelectedToggle().getUserData());
            // TODO create activity level enum or something?

            goToProfileScreen(event,this.profile);
        } else {

            //setErrorStyle()

        }

    }
}
