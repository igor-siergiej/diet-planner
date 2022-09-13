package com.app.planner.profiledetailscontroller;

import com.app.planner.BaseScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

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
    private TextField emailTextField;

    @FXML
    private TextField profileNameTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private TextField weightTextField;



    @FXML
    public void initialize() {
        super.initialize(mainPane,menuBarToggleGroup,profileDetailsToggleButton,undoButton,redoButton);
        setLabels();
        setToggleGroupHandler(profileDetailsToggleGroup);
        detailsToggleButton.setSelected(true);
    }

    public void setLabels() {
        emailTextField.setText(profile.getEmail());
        profileNameTextField.setText(profile.getProfileName());
        heightTextField.setText(Integer.toString(profile.getHeight()));

    }
}
