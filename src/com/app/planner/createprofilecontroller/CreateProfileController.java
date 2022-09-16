package com.app.planner.createprofilecontroller;

import com.app.planner.ActivityLevelType;
import com.app.planner.BaseScreenController;
import com.app.planner.StringValidation;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
    private DatePicker birthDatePicker; //TODO CSS for this

    @FXML
    private RadioButton SEDENTARY;

    @FXML
    private RadioButton LITTLE_EXERCISE;

    @FXML
    private RadioButton MODERATE_EXERCISE;

    @FXML
    private RadioButton DAILY_EXERCISE;

    @FXML
    private RadioButton INTENSE_EXERCISE;

    @FXML
    private RadioButton VERY_INTENSIVE_EXERCISE;

    @FXML
    public void initialize() {
        super.mainPane = this.mainPane;
        //setAgeTextFieldEventHandler();
        setHeightTextFieldEventHandler();
        setWeightTextFieldEventHandler();
        // setting the user data so that it's easier to get the value of it later
        femaleRadioButton.setUserData("Female");
        maleRadioButton.setUserData("Male");
        // Disable create profile button until a profile name is given
        //createProfileButton.disableProperty().bind(Bindings.isEmpty(profileNameTextField.textProperty()));
        initializeBirthdayPicker();

        List<Toggle> toggleList = activityToggleGroup.getToggles();
        ActivityLevelType[] values = ActivityLevelType.values();
        for (int i = 0; i < toggleList.size(); i++) {
            toggleList.get(i).setUserData(values[i]);
        }

        sexToggleGroup.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            femaleRadioButton.setId("blueRadioButton");
            maleRadioButton.setId("blueRadioButton");
        });

        activityToggleGroup.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            SEDENTARY.setId("blueRadioButton");
            LITTLE_EXERCISE.setId("blueRadioButton");
            MODERATE_EXERCISE.setId("blueRadioButton");
            DAILY_EXERCISE.setId("blueRadioButton");
            INTENSE_EXERCISE.setId("blueRadioButton");
            VERY_INTENSIVE_EXERCISE.setId("blueRadioButton");
        });
    }

    private void initializeBirthdayPicker() {
        Callback<DatePicker, DateCell> callB = new Callback<>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.isAfter(today.minus(16, ChronoUnit.YEARS)));
                    } // disabling dates so that user has to be 16
                };
            }
        };
        birthDatePicker.setDayCellFactory(callB);
        birthDatePicker.setShowWeekNumbers(false);
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

    public List<String> getListOfErrorMessages() {
        List<String> returnList = new ArrayList<>();

        boolean isProfileNameValid = StringValidation.usernameValidation(profileNameTextField.getText()) == StringValidation.RETURN_STRING;
        String profileNameErrorMessage = StringValidation.usernameValidation(profileNameTextField.getText());
        if (profileNameTextField.getText().isEmpty()) {
            profileNameErrorMessage = "Please enter a profile name";
            profileNameTextField.setId("text-field-error");
            returnList.add(profileNameErrorMessage);
        } else if (!isProfileNameValid) {
            profileNameTextField.setId("text-field-error");
            returnList.add(profileNameErrorMessage);
        }

        boolean isHeightValid = StringValidation.heightValidation(heightTextField.getText());
        String heightErrorMessage = "Please enter a height between " + StringValidation.MIN_HEIGHT + "cm and " + StringValidation.MAX_HEIGHT + "cm";
        if (heightTextField.getText().isEmpty()) {
            heightErrorMessage = "Please enter your height";
            heightTextField.setId("text-field-error");
            returnList.add(heightErrorMessage);
        } else if (!isHeightValid) {
            heightTextField.setId("text-field-error");
            returnList.add(heightErrorMessage);
        }

        boolean isWeightValid = StringValidation.weightValidation(weightTextField.getText());
        String weightErrorMessage = "Please enter a weight between " + StringValidation.MIN_WEIGHT + "kg and " + StringValidation.MAX_WEIGHT + "kg";
        if (weightTextField.getText().isEmpty()) {
            weightErrorMessage = "Please enter your weight";
            weightTextField.setId("text-field-error");
            returnList.add(weightErrorMessage);
        } else if (!isWeightValid) {
            weightTextField.setId("text-field-error");
            returnList.add(weightErrorMessage);
        }

        boolean isBirthDatePicked = birthDatePicker.getValue() != null;
        if (!isBirthDatePicked) {
            birthDatePicker.setId("text-field-error");
            String birthDateErrorMessage = "Please select your date of birth";
            returnList.add(birthDateErrorMessage);
        }

        boolean isSexToggleGroupSelected = sexToggleGroup.getSelectedToggle() != null;
        if (!isSexToggleGroupSelected) {
            String sexErrorMessage = "Please select your sex";
            maleRadioButton.setId("errorRadioButton");
            femaleRadioButton.setId("errorRadioButton");
            returnList.add(sexErrorMessage);
        }

        boolean isActivityToggleGroupSelected = activityToggleGroup.getSelectedToggle() != null;
        if (!isActivityToggleGroupSelected) {
            String activityLevelErrorMessage = "Please select your activity level";
            SEDENTARY.setId("errorRadioButton");
            LITTLE_EXERCISE.setId("errorRadioButton");
            MODERATE_EXERCISE.setId("errorRadioButton");
            DAILY_EXERCISE.setId("errorRadioButton");
            INTENSE_EXERCISE.setId("errorRadioButton");
            VERY_INTENSIVE_EXERCISE.setId("errorRadioButton");
            returnList.add(activityLevelErrorMessage);
        }

        return returnList;
    }

    public void createNewProfile(ActionEvent event) {
        List<String> listOfErrorMessages = getListOfErrorMessages();
        if (listOfErrorMessages.isEmpty()) {
            profile.setProfileName(profileNameTextField.getText());
            //profile.setAge(Integer.parseInt(ageTextField.getText()));
            profile.setHeight(Integer.parseInt(heightTextField.getText()));
            profile.setWeight(Integer.parseInt(weightTextField.getText()));

            String sex = (String) sexToggleGroup.getSelectedToggle().getUserData();
            profile.setSex(sex);
            if (sex.equals("Female")) {
                if (pregnantCheckBox.isSelected()) {
                    profile.setPregnant(true);
                } else {
                    profile.setPregnant(false);
                }
                if (breastfeedingCheckBox.isSelected()) {
                    profile.setBreastFeeding(true);
                } else {
                    profile.setBreastFeeding(false);
                }
            }
            profile.setActivityLevel((ActivityLevelType) activityToggleGroup.getSelectedToggle().getUserData());
            profile.setBirthDate(birthDatePicker.getValue());
            profile.initialiseProfile();
            goToProfileScreen(event);
        } else {
            StringBuilder sb = new StringBuilder();
            for (String errorMessage : listOfErrorMessages) {
                sb.append(errorMessage);
                sb.append("\n");
            }
            createErrorNotification(mainPane, sb.toString());
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
