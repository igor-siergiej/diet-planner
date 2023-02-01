package com.app.planner.profilescreencontroller;

import com.app.planner.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProfileScreenController extends BaseScreenController {

    static final int MAX_NUM_ENTRIES = 4;

    @FXML
    private PieChart caloriePieChart;

    @FXML
    private VBox entriesVBox;

    @FXML
    private ProgressBar calorieProgressBar;

    @FXML
    private ProgressBar carbsProgressBar;

    @FXML
    private ProgressBar fatProgressBar;

    @FXML
    private ProgressBar proteinProgressBar;

    @FXML
    private Label calorieLabel;

    @FXML
    private Label carbsLabel;

    @FXML
    private Label fatLabel;

    @FXML
    private Label proteinLabel;

    @FXML
    private ToggleButton homeButton;

    @FXML
    private Label profileLabel;

    @FXML
    private ToggleGroup menuBarToggleGroup;

    @FXML
    private Pane mainPane;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    public void initialize() {
        super.initialize(mainPane,menuBarToggleGroup,homeButton,undoButton,redoButton);
        profileLabel.setText(profile.getProfileName());
        loadUI();
        /*if (!profile.getDiary().getAllEntries().isEmpty()) {

        } else {

        }*/

        // TODO need some kind of framework or method to make it easier to determine max doses
        // TODO also need some kind of way to dynamically change max doses to change macros and calorie target
    }

    public void loadUI() {
        // get current values for profile macros for current day
        float calories = profile.getNutrientValueForCurrentDay("Energy (kcal)");
        float carbs = profile.getNutrientValueForCurrentDay("Carbohydrates");
        float fat = profile.getNutrientValueForCurrentDay("Fat");
        float protein = profile.getNutrientValueForCurrentDay("Protein");

        // load up the progressbars and pie chart
        updateMacroUI(calories,fat,carbs,protein,carbsLabel,fatLabel,proteinLabel,calorieLabel,calorieProgressBar,carbsProgressBar
                ,proteinProgressBar,fatProgressBar,caloriePieChart);

        populateEntries();
    }

    public void populateEntries() {
        // this will populate entriesVBox with only 4 of the entries for current day displaying details
        if (!profile.getDiary().getEntriesDay(LocalDate.now()).isEmpty()) {
            ArrayList<Entry> entries = profile.getDiary().getEntriesDay(LocalDate.now());
            for (int i = 0; i < MAX_NUM_ENTRIES && i < entries.size(); i++) {
                Label entryName = new Label(entries.get(i).getMeal().getMealName() + " " + entries.get(i).getEntryType().toString());
                Label calorie = new Label(entries.get(i).getNutrientValueForEntry("Energy (kcal)") + " Kcal");

                HBox hbox = new HBox();
                hbox.setMinHeight(70);
                hbox.setId("fakeButton"); // set better CSS to this
                hbox.setAlignment(Pos.CENTER);

                Region region = new Region();
                HBox.setHgrow(region, Priority.ALWAYS);

                hbox.getChildren().addAll(entryName, region, calorie);
                entriesVBox.getChildren().add(hbox);
            }
        } else {
            return;
        }
    }
}
