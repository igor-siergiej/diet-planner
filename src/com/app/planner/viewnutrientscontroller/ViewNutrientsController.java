package com.app.planner.viewnutrientscontroller;

import com.app.planner.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ViewNutrientsController extends BaseScreenController {

    @FXML
    private Pane calendarPane;

    @FXML
    private VBox sugarVBox;

    @FXML
    private VBox fatVBox;

    @FXML
    private VBox vitaminVBox;

    @FXML
    private VBox mineralVBox;

    @FXML
    private VBox otherVBox;

    @FXML
    private VBox macroVBox;

    @FXML
    private VBox entryVBox;

    @FXML
    private Pane nutrientPane;

    @FXML
    private ToggleGroup menuBarToggleGroup;

    @FXML
    private ToggleButton viewNutrientsToggleButton;


    public void initialise() {
        viewNutrientsToggleButton.setSelected(true);
        setToggleGroupHandler(menuBarToggleGroup);
        profile.setAge(20);
        profile.setBreastFeeding(false);
        profile.setPregnant(false);
        profile.setSex("male");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setShowWeekNumbers(false);
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();

        calendarPane.getChildren().add(popupContent);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New Value: " + newValue);
        });
    }

    public void populateEntries(ArrayList<Entry> entries) {
        entryVBox.getChildren().clear();
        for (Entry entry : entries) {
            ArrayList<Entry> entryArrayList = new ArrayList<>();
            entryArrayList.add(entry);
            Button button = new Button();
            button.setText(entry.getMeal().getMealName() + " " + entry.getTimeEaten().toLocalDate());
            button.setOnMouseClicked(event -> {
                showNutrients(entryArrayList);
            });
            entryVBox.getChildren().add(button);
        }
    }

    public void populateAllEntries() {
        populateEntries(profile.getDiary().getAllEntries());
    }

    public void showNutrients(ArrayList<Entry> entries) {
        nutrientPane.setVisible(true);
        ArrayList<Nutrient> nutrients = new ArrayList<>();
        for (Entry entry : entries) {
            for (Food food : entry.getMeal().getFoods()) {
                nutrients.addAll(food.getNutrients());
            }
        }
        ArrayList<Nutrient> combinedList = Main.combineNutrientList(nutrients);
        Collections.sort(combinedList);
        Collections.reverse(combinedList);

        ArrayList<String> vitaminNameList = new ArrayList<>();
        String[] vitaminNameStrings = {"Vitamin A", "Carotene", "Vitamin D", "Vitamin E", "Vitamin K", "Vitamin B1", "Vitamin B2", "Vitamin B3", "Tryptophan", "Vitamin B6", "Vitamin B12", "Vitamin B9", "Vitamin B5", "Vitamin B7", "Vitamin C"};
        vitaminNameList.addAll(Arrays.asList(vitaminNameStrings));
        ArrayList<Nutrient> vitaminList = new ArrayList<>();

        ArrayList<String> mineralNameList = new ArrayList<>();
        String[] mineralNameStrings = {"Sodium", "Potassium", "Calcium", "Magnesium", "Phosphorus", "Iron", "Copper", "Zinc", "Chloride", "Manganese", "Selenium", "Iodine"};
        mineralNameList.addAll(Arrays.asList(mineralNameStrings));
        ArrayList<Nutrient> mineralList = new ArrayList<>();

        ArrayList<String> sugarNameList = new ArrayList<>();
        String[] sugarNameStrings = {"Total Sugar", "Glucose", "Galactose", "Fructose", "Sucrose", "Maltose", "Lactose"};
        sugarNameList.addAll(Arrays.asList(sugarNameStrings));
        ArrayList<Nutrient> sugarList = new ArrayList<>();

        ArrayList<String> fatNameList = new ArrayList<>();
        String[] fatNameStrings = {"Trans FA", "Saturated Fat", "Omega 6", "Omega 3", "Mono FA", "Poly FA", "Cholesterol"};
        fatNameList.addAll(Arrays.asList(fatNameStrings));
        ArrayList<Nutrient> fatList = new ArrayList<>();

        ArrayList<String> macroNameList = new ArrayList<>();
        String[] macroNameStrings = {"Protein", "Fat", "Carbohydrates"};
        macroNameList.addAll(Arrays.asList(macroNameStrings));
        ArrayList<Nutrient> macroList = new ArrayList<>();

        ArrayList<String> otherNameList = new ArrayList<>();
        String[] otherNameStrings = {"Water", "Total Nitrogen", "Energy (kcal)", "Energy (kJ)", "Starch", "NSP Fibre", "AOAC Fibre"};
        otherNameList.addAll(Arrays.asList(otherNameStrings));
        ArrayList<Nutrient> otherList = new ArrayList<>();

        for (Nutrient nutrient : combinedList) {
            if (vitaminNameList.contains(nutrient.getNutrientName())) {
                vitaminList.add(nutrient);
            } else if (mineralNameList.contains(nutrient.getNutrientName())) {
                mineralList.add(nutrient);
            } else if (sugarNameList.contains(nutrient.getNutrientName())) {
                sugarList.add(nutrient);
            } else if (fatNameList.contains(nutrient.getNutrientName())) {
                fatList.add(nutrient);
            } else if (otherNameList.contains(nutrient.getNutrientName())) {
                otherList.add(nutrient);
            } else if (macroNameList.contains(nutrient.getNutrientName())) {
                macroList.add(nutrient);
            }
        }

        populateVBox(sugarList, sugarVBox);
        populateVBox(fatList, fatVBox);
        populateVBox(vitaminList, vitaminVBox);
        populateVBox(mineralList, mineralVBox);
        populateVBox(otherList, otherVBox);
        populateVBox(macroList, macroVBox);
    }

    public void searchEntries() { // this should be called on click of the calendar
        /*String comboBoxValue = (String) comboBox.getValue();
        LocalDate date = datePicker.getValue();

        ArrayList<Entry> searchedEntries;
        if (comboBoxValue.equals("Get Day")) {
            searchedEntries = profile.getDiary().getEntriesDay(date);
        } else {
            searchedEntries = profile.getDiary().getEntriesWeek(date);
        }
        populateEntries(searchedEntries);*/
    }

    public void populateVBox(ArrayList<Nutrient> nutrients, VBox vbox) {
        HashMap<String, TargetNutrients> maximumDoses = profile.getDailyIntake().getMaximumDoses();
        HashMap<String, TargetNutrients> targetNutrients = profile.getDailyIntake().getTargetNutrients();
        int counter = 0;
        for (Node node : vbox.getChildren()) { // for every Hbox in the VBox, i.e. for every nutrient

            String nutrientName = nutrients.get(counter).getNutrientName();
            float nutrientValue = nutrients.get(counter).getNutrientValue();
            float nutrientTarget = targetNutrients.get(nutrientName).getValue();
            float percentValueToTarget = nutrientValue / nutrientTarget;
            String nutrientUnits = targetNutrients.get(nutrientName).getUnit();
            float maximumDose;
            if (maximumDoses.get(nutrients.get(counter).getNutrientName()) == null) { //if maxDose not found in hashmap then there is no maxDoge
                maximumDose = 99999999;
            } else if (maximumDoses.get(nutrients.get(counter).getNutrientName()).getValue() == 0.0) { // if maximum does = 0 means that the target dose is maximum dose
                maximumDose = targetNutrients.get(nutrients.get(counter).getNutrientName()).getValue(); //set the maximum dose to be the same as target dose
            } else {
                maximumDose = maximumDoses.get(nutrients.get(counter).getNutrientName()).getValue(); // set the maximum dose
            }
            boolean isOverMaximumDose = nutrientValue > maximumDose;

            HBox hbox = (HBox) node;
            for (Node childNode : hbox.getChildren()) {
                if (childNode.getId().equals("nameLabel")) { // for every name label
                    Label label = (Label) childNode;
                    label.setText(nutrients.get(counter).getNutrientName()); // set the label to be the nutrient name
                } else if (childNode instanceof ProgressBar) { // for every progress bar
                    ProgressBar progressBar = (ProgressBar) childNode;
                    progressBar.setProgress(percentValueToTarget);

                    setLimitProgressBar(progressBar, percentValueToTarget, isOverMaximumDose);

                } else if (childNode.getId().equals("percentLabel")) {
                    Label label = (Label) childNode;
                    String percent = String.format("%.1f", nutrientValue / nutrientTarget * 100);
                    label.setText(percent + "%");
                } else if (childNode.getId().equals("valueLabel")) {
                    Label label = (Label) childNode;
                    String value = String.format("%.1f", nutrients.get(counter).getNutrientValue());
                    label.setText(value + nutrientUnits);
                }
            }
            counter++;
        }

    }
}
