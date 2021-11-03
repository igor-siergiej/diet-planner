package com.app.planner.profilescreencontroller;

import com.app.planner.*;
import com.app.planner.addentrycontroller.AddEntryController;
import com.app.planner.calendarcontroller.CalendarController;
import com.app.planner.editgoalscontroller.EditGoalsController;
import com.app.planner.editprofilecontroller.EditProfileController;
import com.app.planner.optionscontroller.OptionsScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import com.app.planner.viewprofilecontroller.ViewProfileController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static javafx.collections.FXCollections.observableArrayList;

public class ProfileScreenController {
    private Profile profile;

    @FXML
    private Pane mainPane;

    @FXML
    private Label messageLabel;

    @FXML
    private PieChart caloriePieChart;

    @FXML
    private TableView calorieTable;

    @FXML
    private TableColumn carbohydratesColumn;

    @FXML
    private TableColumn fatColumn;

    @FXML
    private TableColumn proteinColumn;

    @FXML
    private ProgressBar calorieProgressBar;

    @FXML
    private Label progressBarLabel;

    @FXML
    private TableView currentCalorieTable;

    @FXML
    private TableColumn currentCarbohydratesColumn;

    @FXML
    private TableColumn currentFatColumn;

    @FXML
    private TableColumn currentProteinColumn;

    @FXML
    private VBox entriesVBox;

    @FXML
    private Label calorieGoalLabel;

    @FXML
    private Label currentCalorieLabel;

    public void initialize(Profile profile) {
        this.profile = profile;

        Float carbs = ViewNutrientsController.searchTargetNutrientsList("Carbohydrates", profile.getDailyIntake().getTargetNutrients());
        Float fat = ViewNutrientsController.searchTargetNutrientsList("Fat", profile.getDailyIntake().getTargetNutrients());
        Float protein = ViewNutrientsController.searchTargetNutrientsList("Protein", profile.getDailyIntake().getTargetNutrients());

        Float total = carbs + fat + protein;

        ObservableList<PieChart.Data> pieChartData =
                observableArrayList( //name = visual, (need to calculate percentage manually, value = just value, percentage will be calculated automatically
                        new PieChart.Data("Fat " + String.format("%.1f", fat / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbs / total * 100) + "%", carbs),
                        new PieChart.Data("Protein " + String.format("%.1f", protein / total * 100) + "%", protein));


        caloriePieChart.setData(pieChartData);
        caloriePieChart.setLegendVisible(false);

        float calories = profile.getNutrientValueForCurrentDay("Energy (kcal)");
        float calorieGoal = ViewNutrientsController.searchTargetNutrientsList("Energy (kcal)", profile.getDailyIntake().getTargetNutrients());

        calorieGoalLabel.setText(String.valueOf(calorieGoal));
        currentCalorieLabel.setText(String.valueOf(calories));

        float percentOfCalories = calories / calorieGoal;
        calorieProgressBar.setProgress(percentOfCalories);
        progressBarLabel.setText(String.format("%.2f", percentOfCalories) + "%");

        preventColumnMoving(calorieTable, carbohydratesColumn, fatColumn, proteinColumn);
        preventColumnMoving(currentCalorieTable, currentCarbohydratesColumn, currentFatColumn, currentProteinColumn);

        // Setting up the calorieTable
        carbohydratesColumn.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));

        ObservableList<Macro> observableList = FXCollections.observableArrayList(
                new Macro(ViewNutrientsController.searchTargetNutrientsList("Carbohydrates", profile.getDailyIntake().getTargetNutrients()), // this will get the target marcos from DailyIntake Object
                        ViewNutrientsController.searchTargetNutrientsList("Fat", profile.getDailyIntake().getTargetNutrients()),
                        ViewNutrientsController.searchTargetNutrientsList("Protein", profile.getDailyIntake().getTargetNutrients()))
        );
        calorieTable.setItems(observableList);

        // Setting up the currentCalorieTable
        currentCarbohydratesColumn.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        currentFatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        currentProteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));

        ObservableList<Macro> currentObservableList;
        currentObservableList = FXCollections.observableArrayList(
                new Macro(profile.getNutrientValueForCurrentDay("Carbohydrates"),
                        profile.getNutrientValueForCurrentDay("Fat"),
                        profile.getNutrientValueForCurrentDay("Protein"))
        );

        currentCalorieTable.setItems(currentObservableList);

        for (Entry entry : profile.getDiary().getEntriesDay(LocalDate.now())) { //this will populate entriesVBox with all the entries for current day displaying details
            Label label = new Label(entry.getMeal().getMealName() + " " + entry.getEntryType().toString());
            Label label1 = new Label(entry.getNutrientValueForEntry("Energy (kcal)") + " Kcal");

            HBox hbox = new HBox();
            hbox.setMinHeight(70);
            hbox.setId("fakeButton");
            hbox.setAlignment(Pos.CENTER);

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            hbox.getChildren().addAll(label, region, label1);
            entriesVBox.getChildren().add(hbox);
        }
    }

    public void preventColumnMoving(TableView table, TableColumn carbColumn, TableColumn fatColumn, TableColumn proteinColumn) {
        table.getColumns().addListener(new ListChangeListener() { // prevents the columns from being swapped, NOTE cannot prevent columns from being dragged
            public boolean suspended;

            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    table.getColumns().setAll(carbColumn, fatColumn, proteinColumn);
                    this.suspended = false;
                }
            }
        });
    }

    public void saveProfileToFile() {
        File file = Main.chooseSaveFile(mainPane);
        if (!(file == null)) {
            profile.saveToFile(file);
            messageLabel.setId("successfullMessage");
            messageLabel.setText("Successfully saved to file");
        } else {
            messageLabel.setId("unsuccessfullMessage");
            messageLabel.setText("Failed to save to file");
        }
    }

    public void saveProfileToDB() {
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
    }

    private FXMLLoader goToScreenWithProfile(ActionEvent event, String fxmlFilePath) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/com/app/planner/" + fxmlFilePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.setWindow(event, root);
        return loader;
    }

    public void goToOptionsScreen(ActionEvent event) {
        OptionsScreenController optionsScreenController = goToScreenWithProfile(event, "optionscontroller/OptionsScreen.fxml").getController();
        optionsScreenController.initialize(profile);
    }

    public void goToCalendarScreen(ActionEvent event) {
        CalendarController calendarController = goToScreenWithProfile(event, "calendarcontroller/CalendarScreen.fxml").getController();
        calendarController.initialize(profile);
        calendarController.load();
    }

    public void goToEditGoalsScreen(ActionEvent event) {
        EditGoalsController editGoalsController = goToScreenWithProfile(event, "editgoalscontroller/EditGoalsScreen.fxml").getController();
        editGoalsController.initialize(profile);
    }

    public void goToEditProfileScreen(ActionEvent event) {
        EditProfileController editProfileController = goToScreenWithProfile(event, "editprofilecontroller/EditProfileScreen.fxml").getController();
        editProfileController.initialize(profile);
    }

    public void goToViewProfileScreen(ActionEvent event) {
        ViewProfileController viewProfileController = goToScreenWithProfile(event, "viewprofilecontroller/ViewProfileScreen.fxml").getController();
        viewProfileController.initialize(profile);
    }

    public void goToViewNutrientsScreen(ActionEvent event) {
        ViewNutrientsController viewNutrientsController = goToScreenWithProfile(event, "viewnutrientscontroller/ViewNutrientsScreen.fxml").getController();
        viewNutrientsController.initialize(profile);
    }

    public void goToAddEntryScreen(ActionEvent event) {
        AddEntryController addEntryController = goToScreenWithProfile(event, "addentrycontroller/AddEntryScreen.fxml").getController();
        addEntryController.initialize(profile);
    }

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialize(profile);
    }

    public void goToMainScreen(ActionEvent event) {
        goToScreenWithProfile(event, "mainscreencontroller/MainScreen.fxml");
    }
}
