package com.app.planner.editgoalscontroller;

import com.app.planner.DatabaseConnection;
import com.app.planner.InputValidation;
import com.app.planner.Main;
import com.app.planner.Profile;
import com.app.planner.profilescreencontroller.ProfileScreenController;
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
        setListeners();
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
            if (DatabaseConnection.saveProfileToDb(profile.getUsername(),profile)) {
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
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialize(profile);
    }

    private void calculateCalories() {
        if (proteinTextField.getText().equals("") || fatTextField.getText().equals("") || proteinTextField.getText().equals("")) {
            return;
        }

        float protein = Integer.parseInt(proteinTextField.getText());
        float fat = Integer.parseInt(fatTextField.getText());
        float carbs = Integer.parseInt(carbsTextField.getText());

        float total = protein + fat + carbs;

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Protein " + String.format("%.1f", protein / total * 100) + "%", protein),
                        new PieChart.Data("Fat " + String.format("%.1f", fat / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbs / total * 100) + "%", carbs));

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
