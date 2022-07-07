package com.app.planner.createprofilecontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.Mail;
import com.app.planner.StringValidation;
import com.app.planner.Profile;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class CreateProfileController extends BaseScreenController {

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

    @FXML
    private Button createProfileButton;

    @FXML
    public void initialize() {
        profile = new Profile();
        profile.setEmail("");
        super.mainPane = this.mainPane;
        setAgeTextFieldEventHandler();
        setHeightTextFieldEventHandler();
        setWeightTextFieldEventHandler();
        // setting the user data so that it's easier to get the value of it later
        femaleRadioButton.setUserData("Female");
        maleRadioButton.setUserData("Male");
        // Disable create profile button until a profile name is given
        createProfileButton.disableProperty().bind(Bindings.isEmpty(profileNameTextField.textProperty()));
    }

    public void setAgeTextFieldEventHandler() {
        ageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            ageTextField.setText(StringValidation.integerValidation(newValue, StringValidation.MAX_AGE_DIGITS));
        });
    }

    public void setHeightTextFieldEventHandler() {
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            heightTextField.setText(StringValidation.integerValidation(newValue, StringValidation.MAX_HEIGHT_DIGITS));
        });
    }

    public void setWeightTextFieldEventHandler() {
        weightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            weightTextField.setText(StringValidation.integerValidation(newValue, StringValidation.MAX_WEIGHT_DIGITS));
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
        profileNameTextField.setId("");


        boolean isProfileNameValid = StringValidation.usernameValidation(profileNameTextField.getText()) == StringValidation.RETURN_STRING;
        if (!isProfileNameValid) {
            createErrorNotification(mainPane, "Please enter a valid profile name");
            profileNameTextField.setId("text-field-error");
            return false;
        }

        boolean isAgeValid = StringValidation.ageValidation(ageTextField.getText());
        if (!isTextFieldValid(ageTextField, isAgeValid, "age", "Please enter an age between " +
                StringValidation.MIN_AGE + " and " + StringValidation.MAX_AGE)) {
            return false;
        }

        boolean isHeightValid = StringValidation.heightValidation(heightTextField.getText());
        if (!isTextFieldValid(heightTextField, isHeightValid, "height", "Please enter a height between " +
                StringValidation.MIN_HEIGHT + "cm and " + StringValidation.MAX_HEIGHT + "cm")) {
            return false;
        }

        boolean isWeightValid = StringValidation.weightValidation(weightTextField.getText());
        if (!isTextFieldValid(weightTextField, isWeightValid, "weight", "Please enter a weight between " +
                StringValidation.MIN_WEIGHT + "kg and " + StringValidation.MAX_WEIGHT + "kg")) {
            return false;
        }

        boolean isSexToggleGroupSelected = sexToggleGroup.getSelectedToggle() != null;
        if (!isSexToggleGroupSelected) {
            createErrorNotification(mainPane, "Please select your sex");
            return false;
        }

        boolean isActivityToggleGroupSelected = activityToggleGroup.getSelectedToggle() != null;
        if (!isActivityToggleGroupSelected) {
            createErrorNotification(mainPane, "Please select your activity level");
            return false;
        }

        if (isActivityToggleGroupSelected && isSexToggleGroupSelected && isProfileNameValid &&
                isAgeValid && isHeightValid && isWeightValid) {
            returnBoolean = true;
        }

        return returnBoolean;
    }

    public boolean isTextFieldValid(TextField textField, boolean isValid, String formValue, String text) {
        textField.setId("");
        if (textField.getText().isBlank()) {
            createErrorNotification(mainPane, "Please enter your " + formValue);
            textField.setId("text-field-error");
            return false;
        } else if (!isValid) {
            createErrorNotification(mainPane, text);
            textField.setId("text-field-error");
            return false;
        }
        return true;
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

            profile.initialiseProfile();
            goToProfileScreen(event);
        } else {
            // set error style to every part of the form and concatinate a string for the notification string

        }
    }

    public void testProfile(ActionEvent event) {
        profile.setProfileName("testProfile");
        profile.setAge(21);
        profile.setHeight(175);
        profile.setWeight(75);
        profile.setSex("Male");

        profile.initialiseProfile();
        goToProfileScreen(event);
    }
}
