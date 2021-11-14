package com.app.planner.editgoalscontroller;

import com.app.planner.*;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import com.app.planner.viewnutrientscontroller.ViewNutrientsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditGoalsController {
    private Profile profile;

    @FXML
    private Pane mainPane;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField proteinTextField;

    @FXML
    private TextField carbsTextField;

    @FXML
    private TextField fatTextField;

    @FXML
    private Label caloriesLabel;

    @FXML
    private PieChart macroPieChart;

    public void initialize(Profile profile) {
        this.profile = profile;
        ArrayList<TargetNutrients> targetNutrientsList = profile.getDailyIntake().getTargetNutrients();
        proteinTextField.setText(String.valueOf(ViewNutrientsController.searchTargetNutrientsList("Protein", targetNutrientsList)));
        carbsTextField.setText(String.valueOf(ViewNutrientsController.searchTargetNutrientsList("Carbohydrates", targetNutrientsList)));
        fatTextField.setText(String.valueOf(ViewNutrientsController.searchTargetNutrientsList("Fat", targetNutrientsList)));
        setListeners();
        calculateCalories();
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

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event, "profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialize(profile);
    }

    public void setMacros(ActionEvent event) {
        ArrayList<TargetNutrients> targetNutrientsList = profile.getDailyIntake().getTargetNutrients();

        getTargetNutrient("Protein", targetNutrientsList).setValue(Float.parseFloat(proteinTextField.getText()));
        getTargetNutrient("Carbohydrates", targetNutrientsList).setValue(Float.parseFloat(carbsTextField.getText()));
        getTargetNutrient("Fat", targetNutrientsList).setValue(Float.parseFloat(fatTextField.getText()));

        goToProfileScreen(event);
    }

    public TargetNutrients getTargetNutrient(String nutrientName, ArrayList<TargetNutrients> targetNutrientsArrayList) {
        TargetNutrients targetNutrients = null;
        for (TargetNutrients nutrients : targetNutrientsArrayList) {
            if (nutrientName.equals(nutrients.getNutrientName())) {
                targetNutrients = nutrients;
            }
        }
        return targetNutrients;
    }

    private void calculateCalories() {
        if (proteinTextField.getText().equals("") || fatTextField.getText().equals("") || proteinTextField.getText().equals("")) {
            return;
        } //possibly unneeded if the textFields default to 0 -- JAVAFX BUG?????????

        float protein = Float.parseFloat(proteinTextField.getText());
        float fat = Float.parseFloat(fatTextField.getText());
        float carbs = Float.parseFloat(carbsTextField.getText());

        float total = protein + fat + carbs;

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Fat " + String.format("%.1f", fat / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbs / total * 100) + "%", carbs),
                        new PieChart.Data("Protein " + String.format("%.1f", protein / total * 100) + "%", protein));

        macroPieChart.setData(pieChartData);
        macroPieChart.setLegendVisible(false);

        float proteinCalories = protein * 4;
        float fatCalories = fat * 7;
        float carbsCalories = carbs * 4;
        caloriesLabel.setText(String.valueOf(proteinCalories + fatCalories + carbsCalories));
    }

    public void setListeners() {
        proteinTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            proteinTextField.setText(InputValidation.ageValidation(newValue));//gram validation?
            calculateCalories();
        });

        fatTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            fatTextField.setText(InputValidation.ageValidation(newValue));//gram validation?
            calculateCalories();
        });

        carbsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            carbsTextField.setText(InputValidation.ageValidation(newValue));//gram validation?
            calculateCalories();
        });
    }
}
