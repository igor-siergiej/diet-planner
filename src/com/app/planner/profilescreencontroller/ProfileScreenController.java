package com.app.planner.profilescreencontroller;

import com.app.planner.*;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

public class ProfileScreenController extends BaseScreenController {

    private Profile profile;



    @FXML
    private Pane mainPane;

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

    public void initialise(Profile profile) {
        homeButton.setSelected(true);
        this.profile = profile;

        // get goals of current profile profile
        float carbsGoal = ViewNutrientsController.searchTargetNutrientsList("Carbohydrates", profile.getDailyIntake().getTargetNutrients());
        float fatGoal = ViewNutrientsController.searchTargetNutrientsList("Fat", profile.getDailyIntake().getTargetNutrients());
        float proteinGoal = ViewNutrientsController.searchTargetNutrientsList("Protein", profile.getDailyIntake().getTargetNutrients());
        float calorieGoal = ViewNutrientsController.searchTargetNutrientsList("Energy (kcal)", profile.getDailyIntake().getTargetNutrients());

        // get current values for profile macros for current day
        float calories = profile.getNutrientValueForCurrentDay("Energy (kcal)");
        float carbs = profile.getNutrientValueForCurrentDay("Carbohydrates");
        float fat = profile.getNutrientValueForCurrentDay("Fat");
        float protein = profile.getNutrientValueForCurrentDay("Protein");

        float total = carbsGoal + fatGoal + proteinGoal;

        // set piechart values
        // TODO should this be goal values or actual values for the day
        ObservableList<PieChart.Data> pieChartData =
                observableArrayList( //name = visual, (need to calculate percentage manually, value = just value, percentage will be calculated automatically
                        new PieChart.Data("Fat " + String.format("%.1f", fatGoal / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbsGoal / total * 100) + "%", carbs),
                        new PieChart.Data("Protein " + String.format("%.1f", proteinGoal / total * 100) + "%", protein));

        caloriePieChart.setData(pieChartData);
        caloriePieChart.setLegendVisible(false);

        carbsLabel.setText(carbs + "/" + carbsGoal);
        fatLabel.setText(fat + "/" + fatGoal);
        proteinLabel.setText(protein + "/" + proteinGoal);
        calorieLabel.setText(calories + "/" +calorieGoal);

        // set progressbar progress
        float percentOfCalories = calories / calorieGoal;
        calorieProgressBar.setProgress(percentOfCalories);

        float percentOfCarbs = carbs / carbsGoal;
        carbsProgressBar.setProgress(percentOfCarbs);

        float percentOfFat = fat / fatGoal;
        fatProgressBar.setProgress(percentOfFat);

        float percentOfProtein = protein / proteinGoal;
        proteinProgressBar.setProgress(percentOfProtein);

        //this will populate entriesVBox with only 4 of the entries for current day displaying details
        ArrayList<Entry> entries = profile.getDiary().getEntriesDay(LocalDate.now());
        for (int i = 0; i < 4; i++) {
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
    }

   /* public void saveProfileToFile() {
        File file = Main.chooseSaveFile(mainPane);
        if (!(file == null)) {
            profile.saveToFile(file);
            messageLabel.setId("successfullMessage");
            messageLabel.setText("Successfully saved to file");
        } else {
            messageLabel.setId("unsuccessfullMessage");
            messageLabel.setText("Failed to save to file");
        }
    }*/

   /* public void saveProfileToDB() {
        if (profile.getUsername() != null) {
            if (DatabaseConnection.saveProfileToDb(profile.getUsername(), profile)) {
                messageLabel.setId("successfullMessage");
                messageLabel.setText("Successfully saved to DB");
            } else {
                messageLabel.setId("successfullMessage");
                messageLabel.setText("Error in saving to DB");
            }
        } else {
            messageLabel.setId("successfullMessage");
            messageLabel.setText("This profile does not have an account");
        }
    }*/
}
