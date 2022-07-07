package com.app.planner.addentrycontroller;

import com.app.planner.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDateTime;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

public class AddEntryController extends BaseScreenController {
    private Entry entry;
    private ArrayList<Food> dataset = Main.initialiseData();

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox searchVBox;

    @FXML
    private VBox foodVBox;

    @FXML
    private TextField mealNameTextField;

    @FXML
    private ComboBox mealTypeComboBox;

    @FXML
    private Button addEntryButton;

    @FXML
    private ToggleButton addEntryToggleButton;

    @FXML
    private ToggleGroup menuBarToggleGroup;

    @FXML
    private PieChart caloriePieChart;

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
    private Pane mainPane;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    public void initialize() {
        super.initialize(mainPane,menuBarToggleGroup,addEntryToggleButton,undoButton,redoButton);
        setTextFieldEventHandler();
        setAddEntryButtonDisable();
        populateMealTypeComboBox();
    }

    public void addEntry(ActionEvent event) {
        Meal meal = getMeal();
        meal.setMealName(mealNameTextField.getText());
        entry = new Entry(meal, LocalDateTime.now(), (EntryType) mealTypeComboBox.getValue());
        profile.getDiary().addEntry(entry);

        goToProfileScreen(event);
    }

    public void setTextFieldEventHandler() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchVBox.getChildren().clear();
            searchTextField.setText(StringValidation.stringValidation(newValue));
            populateSearchResult();
        });

        mealNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            mealNameTextField.setText(StringValidation.stringValidation(newValue));
        });
    }

    public void setFoodPortionEventHandler(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(StringValidation.portionValidation(newValue));//letter causes a crash???
            updateNutrients();
        });
    }

    public void populateMealTypeComboBox() {
        mealTypeComboBox.setItems(observableArrayList(EntryType.values()));
    }

    public void setAddEntryButtonDisable() {
        addEntryButton.disableProperty().bind(mealNameTextField.textProperty().isEmpty().or((mealTypeComboBox.valueProperty().isNull())).or(Bindings.isEmpty(foodVBox.getChildren())));
        //disabling the button until the form is filled out
    }

    public void populateSearchResult() {
        ArrayList<Food> searchedFoods = Main.sortedFoodSearch(dataset, searchTextField.getText());
        for (Food food : searchedFoods) {
            Button button = new Button();
            button.setWrapText(true);
            button.setText(food.getFoodName());
            button.setMinHeight(80);
            button.setOnMouseClicked(event -> {
                addToFoodVBox(food);
            });
            searchVBox.getChildren().add(button);
        }
    }

    public void updateNutrients() {
        showNutrients(getMeal().getFoods());
    }

    public Meal getMeal() {
        ArrayList<Food> dataset = Main.initialiseData();
        Meal meal = new Meal();
        for (Node node : foodVBox.getChildren()) {
            if (node instanceof HBox) {
                String labelText = "";
                Integer portion = 0;
                for (Node node1 : ((HBox) node).getChildren()) {
                    if (node1 instanceof Label) {
                        Label label = (Label) node1;
                        labelText = label.getText();
                    } else if (node1 instanceof TextField) {
                        TextField textField = (TextField) node1;
                        if (textField.getText().equals("")) {
                            textField.setText("0");
                        }
                        portion = Integer.valueOf(textField.getText());
                    }
                }
                meal.addFood(Main.exactFoodSearch(dataset, labelText), portion);
            }
        }
        return meal;
    }

    public void removeFood(String foodName) {
        Node removeNode = null;
        for (Node node : foodVBox.getChildren()) {
            if (node instanceof HBox) {
                for (Node node1 : ((HBox) node).getChildren()) {
                    if (node1 instanceof Label) {
                        Label label = (Label) node1;
                        if (label.getText().equals(foodName)) {
                            removeNode = node;
                        }
                    }
                }
            }
        }
        foodVBox.getChildren().remove(removeNode);
    }

    public void addToFoodVBox(Food food) {
        HBox hbox = new HBox();

        Label label = new Label();
        label.setId("fakeButton");
        label.setText(food.getFoodName());
        label.setWrapText(true);
        label.setMinHeight(70);
        label.setFont(new Font(15));

        TextField portionTextField = new TextField();
        portionTextField.setPrefWidth(250);

        setFoodPortionEventHandler(portionTextField);

        Button button = new Button();
        button.setText("X");
        button.setAlignment(Pos.CENTER_RIGHT);
        button.setOnMouseClicked(event -> {
            removeFood(food.getFoodName());
            updateNutrients();
        });

        hbox.setSpacing(5);
        hbox.getChildren().add(label);
        hbox.getChildren().add(portionTextField);
        hbox.getChildren().add(button);

        foodVBox.getChildren().add(hbox);
    }

    public static void updateMacroUI(float calories, float fat, float carbs, float protein, Label carbsLabel, Label fatLabel, Label proteinLabel, Label calorieLabel,
                                     ProgressBar calorieProgressBar, ProgressBar carbsProgressBar, ProgressBar proteinProgressBar, ProgressBar fatProgressBar, PieChart caloriePieChart) {
        HashMap<String, TargetNutrients> targetNutrients = profile.getDailyIntake().getTargetNutrients();
        // get goals of current profile profile
        float carbsGoal = targetNutrients.get("Carbohydrates").getValue();
        float fatGoal = targetNutrients.get("Fat").getValue();
        float proteinGoal = targetNutrients.get("Protein").getValue();
        float calorieGoal = targetNutrients.get("Energy (kcal)").getValue();

        float total = carbs + fat + protein;

        HashMap<String, TargetNutrients> maximumDoses = profile.getDailyIntake().getMaximumDoses();

        float carbsMaxDose = maximumDoses.get("Carbohydrates").getValue(); // max dose = 0 therefore max dose is target dose
        float fatMaxDose = maximumDoses.get("Fat").getValue();
        float proteinMaxDose = maximumDoses.get("Protein").getValue();

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

        // set piechart values
        ObservableList<PieChart.Data> pieChartData =
                observableArrayList( // s = visual name, v = value where percentage will be calculated automatically
                        new PieChart.Data("Fat " + String.format("%.1f", fat / total * 100) + "%", fat),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", carbs / total * 100) + "%", carbs),
                        new PieChart.Data("Protein " + String.format("%.1f", protein / total * 100) + "%", protein));

        caloriePieChart.setData(pieChartData);
        caloriePieChart.setLegendVisible(false);
    }

    public void showNutrients(ArrayList<Food> foods) {
        ArrayList<Nutrient> nutrients = new ArrayList<>();
        for (Food food : foods) {
            nutrients.addAll(food.getNutrients());
        }
        // hashmap of all of the nutrients combined key = nutrient name, v = nutrient float
        ArrayList<Nutrient> combinedList = Main.combineNutrientList(nutrients);
        HashMap<String, Float> nutrientList = new HashMap<>();
        for (Nutrient nutrient : combinedList) {
            nutrientList.put(nutrient.getNutrientName(), nutrient.getNutrientValue());
        }

        float calorie = nutrientList.get("Energy (kcal)");
        float fat = nutrientList.get("Fat");
        float carbs = nutrientList.get("Carbohydrates");
        float protein = nutrientList.get("Protein");

        updateMacroUI(calorie, fat, carbs, protein, carbsLabel,
                fatLabel, proteinLabel, calorieLabel, calorieProgressBar, carbsProgressBar, proteinProgressBar, fatProgressBar, caloriePieChart);

    }
}
