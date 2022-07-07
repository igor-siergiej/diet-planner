package com.app.planner.profiledetailscontroller;

import com.app.planner.BaseScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
    public void initialize() {
        super.initialize(mainPane,menuBarToggleGroup,profileDetailsToggleButton,undoButton,redoButton);
    }
}
