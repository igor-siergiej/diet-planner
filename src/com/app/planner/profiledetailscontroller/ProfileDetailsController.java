package com.app.planner.profiledetailscontroller;

import com.app.planner.BaseScreenController;
import com.app.planner.InputValidator;
import com.app.planner.StringValidation;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProfileDetailsController extends BaseScreenController {

    @FXML
    private Pane mainPane;

    @FXML
    private ToggleGroup menuBarToggleGroup;

    @FXML
    private ToggleGroup profileDetailsToggleGroup;

    @FXML
    private ToggleButton profileDetailsToggleButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private ToggleButton editProfileToggleButton;

    @FXML
    private ToggleButton changePasswordToggleButton;

    @FXML
    private ToggleButton goalsToggleButton;

    @FXML
    private ToggleGroup activityToggleGroup;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField profileNameTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private RadioButton sedentaryButton;

    @FXML
    private RadioButton littleExerciseButton;

    @FXML
    private RadioButton moderateExerciseButton;

    @FXML
    private RadioButton dailyExerciseButton;

    @FXML
    private RadioButton intenseExerciseButton;

    @FXML
    private RadioButton veryIntensiveExerciseButton;

    @FXML
    private VBox changePasswordVBox;

    @FXML
    private VBox editVBox;

    @FXML
    private VBox goalsVBox;

    @FXML
    private CheckBox pregnantCheckBox;

    @FXML
    private CheckBox breastfeedingCheckBox;

    @FXML
    private TextField passwordTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField retypePasswordTextField;

    @FXML
    private PasswordField retypePasswordField;

    @FXML
    private RadioButton showPasswordButton;

    @FXML
    private Label passwordMessage;

    @FXML
    private Label retypePasswordMessage;

    @FXML
    private PieChart pieChart;

    @FXML
    private TextField fatTextField;

    @FXML
    private TextField proteinTextField;

    @FXML
    private TextField carbsTextField;

    @FXML
    public void initialize() {
        super.initialize(mainPane, menuBarToggleGroup, profileDetailsToggleButton, undoButton, redoButton);
        // adding listeners to prevent user from typing in invalid input to integer and float textFields

        createFloatTextFormatter(fatTextField, 5);
        createFloatTextFormatter(proteinTextField, 5);
        createFloatTextFormatter(carbsTextField, 5);

        createIntegerTextFormatter(heightTextField, StringValidation.MAX_HEIGHT_DIGITS);
        createFloatTextFormatter(weightTextField, StringValidation.MAX_WEIGHT_DIGITS);

        goToEditVBox(); // selecting the initial screen from the menuBar
        editProfileToggleButton.setSelected(true);
        setToggleGroupHandler(profileDetailsToggleGroup);

        setShowPasswordHandlers(passwordTextField, showPasswordButton, passwordField);
        setShowPasswordHandlers(retypePasswordTextField, showPasswordButton, retypePasswordField);

        InputValidator passwordValidator = new InputValidator();
        passwordValidator.createPasswordValidator(passwordField, passwordTextField, passwordMessage); //validator for password field in change password tab
        passwordValidator.createPasswordValidator(passwordTextField, passwordField, passwordMessage); //validator for password field when the show password radio button is selected

        InputValidator retypePasswordValidator = new InputValidator();
        // new InputValidator is required because both share the same timer, having the same stack on two fields cancels the previous task if typed too fast
        retypePasswordValidator.createRetypePasswordValidator(passwordField, retypePasswordField, retypePasswordMessage, retypePasswordTextField); // same as above just for the retype password field
        retypePasswordValidator.createRetypePasswordValidator(retypePasswordTextField, passwordField, retypePasswordMessage, retypePasswordField);

        if (profile.getSex().equals("Male")) { // could switch this to remove the entire HBox instead?
            breastfeedingCheckBox.setDisable(true);
            pregnantCheckBox.setDisable(true);
        }

        resetMacroFields();
        updatePieChart();

        fatTextField.textProperty().addListener((observable, oldValue, newValue) -> updatePieChart());

        proteinTextField.textProperty().addListener((observable, oldValue, newValue) -> updatePieChart());

        carbsTextField.textProperty().addListener((observable, oldValue, newValue) -> updatePieChart());
    }

    public void goToChangePassword() {
        changePasswordVBox.setVisible(true);
        editVBox.setVisible(false);
        goalsVBox.setVisible(false);
        clearPasswordFields();
        showPasswordButton.setSelected(false);
    }

    public void updatePieChart() {
        float fat = getMacroNutrient(fatTextField);
        float carbs = getMacroNutrient(carbsTextField);
        float protein = getMacroNutrient(proteinTextField);

        setPieChartValues(fat, carbs, protein, pieChart);
    }

    private float getMacroNutrient(TextField textField) {
        float value = 0;
        if (!textField.getText().isBlank()) {
            value = Float.parseFloat(textField.getText());
        }
        return value;
    }

    public void goToEditGoals() {
        goalsVBox.setVisible(true);
        changePasswordVBox.setVisible(false);
        editVBox.setVisible(false);
    }

    public void goToEditVBox() {
        editVBox.setVisible(true);
        changePasswordVBox.setVisible(false);
        goalsVBox.setVisible(false);
        setLabels(emailTextField, profileNameTextField, heightTextField, weightTextField, breastfeedingCheckBox, pregnantCheckBox);
        setActivityLevel(sedentaryButton, littleExerciseButton, moderateExerciseButton, dailyExerciseButton, intenseExerciseButton, veryIntensiveExerciseButton);
    }

    public void setLabels(TextField emailTextField, TextField profileNameTextField, TextField heightTextField, TextField weightTextField, CheckBox breastfeedingCheckBox, CheckBox pregnantCheckBox) {
        emailTextField.setText(profile.getEmail());
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
        setLabels(emailTextField, profileNameTextField, heightTextField, weightTextField, breastfeedingCheckBox, pregnantCheckBox);
        setActivityLevel(sedentaryButton, littleExerciseButton, moderateExerciseButton, dailyExerciseButton, intenseExerciseButton, veryIntensiveExerciseButton);
    }

    public void resetMacroFields() {
        fatTextField.setText(String.valueOf(profile.getDailyIntake().getTargetNutrients().get("Fat").getValue()));
        proteinTextField.setText(String.valueOf(profile.getDailyIntake().getTargetNutrients().get("Protein").getValue()));
        carbsTextField.setText(String.valueOf(profile.getDailyIntake().getTargetNutrients().get("Carbohydrates").getValue()));
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

    public void clearPasswordFields() {
        // this resets the fields as well as styling for said fields
        showPasswordButton.setSelected(false);

        passwordField.clear();
        passwordField.setId("");
        passwordMessage.setText("");
        passwordTextField.setId("");

        retypePasswordField.clear();
        retypePasswordTextField.setId("");
        retypePasswordMessage.setText("");
        retypePasswordField.setId("");
    }
}
