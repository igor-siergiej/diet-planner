package com.app.planner.profiledetailscontroller;

import com.app.planner.BaseScreenController;
import javafx.fxml.FXML;
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

    public void initialise() {
        super.mainPane = mainPane;
        setToggleGroupHandler(menuBarToggleGroup);
        profileDetailsToggleButton.setSelected(true); // need to select the toggle button of which screen we are on
    }
}
