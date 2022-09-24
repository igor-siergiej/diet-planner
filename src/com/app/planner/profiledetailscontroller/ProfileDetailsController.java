package com.app.planner.profiledetailscontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.StringValidation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProfileDetailsController extends BaseScreenController {

    @FXML
    private Pane mainPane;

    @FXML
    private ToggleGroup menuBarToggleGroup;

    @FXML
    private ToggleButton profileDetailsToggleButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private ToggleButton detailsToggleButton;

    @FXML
    private ToggleGroup profileDetailsToggleGroup;

    @FXML
    private TextField detailsEmailTextField;

    @FXML
    private TextField detailsProfileNameTextField;

    @FXML
    private TextField detailsHeightTextField;

    @FXML
    private TextField detailsWeightTextField;

    @FXML
    private TextField editEmailTextField;

    @FXML
    private TextField editProfileNameTextField;

    @FXML
    private TextField editHeightTextField;

    @FXML
    private TextField editWeightTextField;

    @FXML
    private RadioButton detailsSedentary;

    @FXML
    private RadioButton detailsLittleExercise;

    @FXML
    private RadioButton detailsModerateExercise;

    @FXML
    private RadioButton detailsDailyExercise;

    @FXML
    private RadioButton detailsIntenseExercise;

    @FXML
    private RadioButton detailsVeryIntensiveExercise;

    @FXML
    private RadioButton editSedentary;

    @FXML
    private RadioButton editLittleExercise;

    @FXML
    private RadioButton editModerateExercise;

    @FXML
    private RadioButton editDailyExercise;

    @FXML
    private RadioButton editIntenseExercise;

    @FXML
    private RadioButton editVeryIntensiveExercise;

    @FXML
    private VBox detailsVBox;

    @FXML
    private VBox editVBox;

    @FXML
    private CheckBox detailsPregnantCheckBox;

    @FXML
    private CheckBox detailsBreastfeedingCheckBox;

    @FXML
    private CheckBox editPregnantCheckBox;

    @FXML
    private CheckBox editBreastfeedingCheckBox;

    @FXML
    public void initialize() {
        super.initialize(mainPane, menuBarToggleGroup, profileDetailsToggleButton, undoButton, redoButton);
        goToDetailsVBox();
        setToggleGroupHandler(profileDetailsToggleGroup);
        detailsToggleButton.setSelected(true);
        if (profile.getSex().equals("Male")) { // could switch this to remove the entire HBox instead
            editBreastfeedingCheckBox.setDisable(true);
            editPregnantCheckBox.setDisable(true);
            detailsBreastfeedingCheckBox.setDisable(true);
            detailsPregnantCheckBox.setDisable(true);
        }

        // adding listeners to prevent user from typing in invalid input to integer and float textFields
        editHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            editHeightTextField.setText(StringValidation.integerValidation(newValue, StringValidation.MAX_HEIGHT_DIGITS));
        });

        editWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            editWeightTextField.setText(StringValidation.floatValidation(newValue, StringValidation.MAX_WEIGHT_DIGITS));
        });
    }

    public void goToDetailsVBox() {
        detailsVBox.setVisible(true);
        editVBox.setVisible(false);
        setLabels(detailsEmailTextField, detailsProfileNameTextField, detailsHeightTextField, detailsWeightTextField, detailsBreastfeedingCheckBox, detailsPregnantCheckBox);
        setActivityLevel(detailsSedentary, detailsLittleExercise, detailsModerateExercise, detailsDailyExercise, detailsIntenseExercise, detailsVeryIntensiveExercise);
    }

    public void goToEditVBox() {
        editVBox.setVisible(true);
        detailsVBox.setVisible(false);
        setLabels(editEmailTextField, editProfileNameTextField, editHeightTextField, editWeightTextField, editBreastfeedingCheckBox, editPregnantCheckBox);
        setActivityLevel(editSedentary, editLittleExercise, editModerateExercise, editDailyExercise, editIntenseExercise, editVeryIntensiveExercise);
    }

    public void setLabels(TextField emailtextField, TextField profileNameTextField, TextField heightTextField, TextField weightTextField, CheckBox breastfeedingCheckBox, CheckBox pregnantCheckBox) {
        emailtextField.setText(profile.getEmail());
        profileNameTextField.setText(profile.getProfileName());
        heightTextField.setText(Integer.toString(profile.getHeight()));
        weightTextField.setText(Double.toString(profile.getWeight()));
        if (profile.isBreastFeeding()) {
            breastfeedingCheckBox.setSelected(true);
        }
        if (profile.isPregnant()) {
            pregnantCheckBox.setSelected(true);
        }
    }

    public void resetEditField() {
        setLabels(editEmailTextField, editProfileNameTextField, editHeightTextField, editWeightTextField, editBreastfeedingCheckBox, editPregnantCheckBox);
        setActivityLevel(editSedentary, editLittleExercise, editModerateExercise, editDailyExercise, editIntenseExercise, editVeryIntensiveExercise);
    }

    public void setActivityLevel(RadioButton sedentary, RadioButton littleExercise, RadioButton moderateExercise, RadioButton dailyExercise, RadioButton intenseExercise, RadioButton veryIntenseExercise) {
        switch (profile.getActivityLevel()) {
            case SEDENTARY -> sedentary.setSelected(true);
            case LITTLE_EXERCISE -> littleExercise.setSelected(true);
            case MODERATE_EXERCISE -> moderateExercise.setSelected(true);
            case DAILY_EXERCISE -> dailyExercise.setSelected(true);
            case INTENSE_EXERCISE -> intenseExercise.setSelected(true);
            case VERY_INTENSIVE_EXERCISE -> veryIntenseExercise.setSelected(true);
        }
    }
}
