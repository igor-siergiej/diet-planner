package com.app.planner.profilescreencontroller;

import com.app.planner.*;
import com.app.planner.addentrycontroller.AddEntryController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.collections.FXCollections.observableArrayList;

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

    public void initialise(@NotNull Profile profile) {
        this.profile = profile;
        setToggleGroupHandler(menuBarToggleGroup);
        homeButton.setSelected(true); // since we are in profileScreen set the toggleButton to be selected
        profileLabel.setText(profile.toString());

        // TODO split this up into several methods so it's easier to read

        HashMap<String, TargetNutrients> targetNutrients = profile.getDailyIntake().getTargetNutrients();
        // get goals of current profile profile
        float carbsGoal = targetNutrients.get("Carbohydrates").getValue();
        float fatGoal = targetNutrients.get("Fat").getValue();
        float proteinGoal = targetNutrients.get("Protein").getValue();
        float calorieGoal = targetNutrients.get("Energy (kcal)").getValue();

        HashMap<String, TargetNutrients> maximumDoses = profile.getDailyIntake().getMaximumDoses();

        float carbsMaxDose = maximumDoses.get("Carbohydrates").getValue(); // max dose = 0 therefore max dose is target dose
        float fatMaxDose = maximumDoses.get("Fat").getValue();
        float proteinMaxDose = maximumDoses.get("Protein").getValue();
        //float calorieMaxDose = maximumDoses.get("Energy (kcal)").getValue(); need to check if this is null and therefore there is no max dose
        // TODO need some kind of framework or method to make it easier to determine max doses
        // TODO also need some kind of way to dynamically change max doses to change macros and calorie target

        // get current values for profile macros for current day
        float calories = profile.getNutrientValueForCurrentDay("Energy (kcal)");
        float carbs = profile.getNutrientValueForCurrentDay("Carbohydrates");
        float fat = profile.getNutrientValueForCurrentDay("Fat");
        float protein = profile.getNutrientValueForCurrentDay("Protein");

        float total = carbs + fat + protein;

        // set piechart values
        ObservableList<PieChart.Data> pieChartData =
                observableArrayList( // javadoc :: s = visual name, v = value where percentage will be calculated automatically
                        new PieChart.Data("Fat " + String.format("%.1f", fat / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbs / total * 100) + "%", carbs),
                        new PieChart.Data("Protein " + String.format("%.1f", protein / total * 100) + "%", protein));

        caloriePieChart.setData(pieChartData);
        caloriePieChart.setLegendVisible(false);

        carbsLabel.setText(carbs + "/" + carbsGoal);
        fatLabel.setText(fat + "/" + fatGoal);
        proteinLabel.setText(protein + "/" + proteinGoal);
        calorieLabel.setText(calories + "/" + calorieGoal);

        // set progressbar progress
        float percentOfCalories = calories / calorieGoal;
        calorieProgressBar.setProgress(percentOfCalories);
        //setLimitProgressBar(calorieProgressBar, percentOfCalories, calories > calorieMaxDose);

        float percentOfCarbs = carbs / carbsGoal;
        carbsProgressBar.setProgress(percentOfCarbs);
        setLimitProgressBar(carbsProgressBar, percentOfCarbs, carbs > carbsMaxDose);

        float percentOfFat = fat / fatGoal;
        fatProgressBar.setProgress(percentOfFat);
        setLimitProgressBar(fatProgressBar, percentOfFat, fat > fatMaxDose);

        float percentOfProtein = protein / proteinGoal;
        proteinProgressBar.setProgress(percentOfProtein);
        setLimitProgressBar(proteinProgressBar, percentOfProtein, protein > proteinMaxDose);

        populateEntries();
    }

    public void populateEntries() {
        // this will populate entriesVBox with only 4 of the entries for current day displaying details
        if (!profile.getDiary().getEntriesDay(LocalDate.now()).isEmpty()) {
            ArrayList<Entry> entries = profile.getDiary().getEntriesDay(LocalDate.now());
            for (int i = 0; i < MAX_NUM_ENTRIES; i++) {
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

    public void goToViewNutrientsScreen(ActionEvent event) {
        ViewNutrientsController viewNutrientsController = goToScreen(event, "viewnutrientscontroller/ViewNutrientsScreen.fxml").getController();
        viewNutrientsController.initialise(profile);
    }

    public void goToAddEntryScreen(ActionEvent event) {
        AddEntryController addEntryController = goToScreen(event, "addentrycontroller/AddEntryScreen.fxml").getController();
        addEntryController.initialise(profile);
        // should the fxmlFilePath be hardcoded values instead?
    }
}
