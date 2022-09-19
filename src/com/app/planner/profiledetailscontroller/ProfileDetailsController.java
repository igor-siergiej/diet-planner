package com.app.planner.profiledetailscontroller;

import com.app.planner.BaseScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

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
    public void initialize() {
        super.initialize(mainPane,menuBarToggleGroup,profileDetailsToggleButton,undoButton,redoButton);
        goToDetailsVBox();
        setToggleGroupHandler(profileDetailsToggleGroup);
        detailsToggleButton.setSelected(true);
    }

    public void goToDetailsVBox() {
        detailsVBox.setVisible(true);
        editVBox.setVisible(false);
        setLabels(detailsEmailTextField,detailsProfileNameTextField,detailsHeightTextField,detailsWeightTextField);
        setActivityLevel(detailsSedentary,detailsLittleExercise,detailsModerateExercise,detailsDailyExercise,detailsIntenseExercise,detailsVeryIntensiveExercise);
    }

    public void goToEditVBox() {
        editVBox.setVisible(true);
        detailsVBox.setVisible(false);
        setLabels(editEmailTextField,editProfileNameTextField,editHeightTextField,editWeightTextField);
        setActivityLevel(editSedentary,editLittleExercise,editModerateExercise,editDailyExercise,editIntenseExercise,editVeryIntensiveExercise);
    }

    public void setLabels(TextField emailtextField, TextField profileNameTextField, TextField heightTextField, TextField weightTextField) {
        emailtextField.setText(profile.getEmail());
        profileNameTextField.setText(profile.getProfileName());
        heightTextField.setText(Integer.toString(profile.getHeight()));
        weightTextField.setText(Double.toString(profile.getWeight()));
    }

    public void resetEditField() {
        setLabels(editEmailTextField,editProfileNameTextField,editHeightTextField,editWeightTextField);
        setActivityLevel(editSedentary,editLittleExercise,editModerateExercise,editDailyExercise,editIntenseExercise,editVeryIntensiveExercise);
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
