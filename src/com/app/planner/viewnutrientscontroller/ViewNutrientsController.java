package com.app.planner.viewnutrientscontroller;

import com.app.planner.*;
import com.app.planner.profilescreencontroller.ProfileScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ViewNutrientsController extends BaseScreenController{

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
    private PieChart macroPieChart;

    @FXML
    private VBox entryVBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Pane nutrientPane;

    @FXML
    private ComboBox comboBox;

    public void initialise(Profile profile) {
        this.profile = profile;
        profile.setAge(20);
        profile.setBreastFeeding(false);
        profile.setPregnant(false);
        profile.setSex("male");
        datePicker.showWeekNumbersProperty().set(false);
    }

    public void goToProfileScreen(ActionEvent event) {
        ProfileScreenController profileScreenController = goToScreenWithProfile(event,"profilescreencontroller/ProfileScreen.fxml").getController();
        profileScreenController.initialise(profile);
    }

    public void populateEntries(ArrayList<Entry> entries) {
        entryVBox.getChildren().clear();
        for (Entry entry: entries) {
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
            for (Food food :entry.getMeal().getFoods()) {
                nutrients.addAll(food.getNutrients());
            }
        }
        ArrayList<Nutrient> combinedList = Main.combineNutrientList(nutrients);
        Collections.sort(combinedList);
        Collections.reverse(combinedList);

        ArrayList<String> vitaminNameList = new ArrayList<>();
        String[] vitaminNameStrings = {"Vitamin A","Carotene","Vitamin D" ,"Vitamin E","Vitamin K","Vitamin B1","Vitamin B2","Vitamin B3","Tryptophan","Vitamin B6","Vitamin B12","Vitamin B9","Vitamin B5","Vitamin B7","Vitamin C"};
        vitaminNameList.addAll(Arrays.asList(vitaminNameStrings));
        ArrayList<Nutrient> vitaminList = new ArrayList<>();

        ArrayList<String> mineralNameList = new ArrayList<>();
        String[] mineralNameStrings = {"Sodium","Potassium","Calcium","Magnesium","Phosphorus","Iron","Copper","Zinc","Chloride","Manganese","Selenium","Iodine"};
        mineralNameList.addAll(Arrays.asList(mineralNameStrings));
        ArrayList<Nutrient> mineralList = new ArrayList<>();

        ArrayList<String> sugarNameList = new ArrayList<>();
        String[] sugarNameStrings = {"Total Sugar","Glucose","Galactose","Fructose","Sucrose","Maltose","Lactose"};
        sugarNameList.addAll(Arrays.asList(sugarNameStrings));
        ArrayList<Nutrient> sugarList = new ArrayList<>();

        ArrayList<String> fatNameList = new ArrayList<>();
        String[] fatNameStrings = {"Trans FA","Saturated Fat","Omega 6","Omega 3","Mono FA","Poly FA","Cholesterol"};
        fatNameList.addAll(Arrays.asList(fatNameStrings));
        ArrayList<Nutrient> fatList = new ArrayList<>();

        ArrayList<String> macroNameList = new ArrayList<>();
        String[] macroNameStrings = {"Protein","Fat","Carbohydrates"};
        macroNameList.addAll(Arrays.asList(macroNameStrings));
        ArrayList<Nutrient> macroList = new ArrayList<>();

        ArrayList<String> otherNameList = new ArrayList<>();
        String[] otherNameStrings = {"Water","Total Nitrogen","Energy (kcal)","Energy (kJ)","Starch","NSP Fibre","AOAC Fibre"};
        otherNameList.addAll(Arrays.asList(otherNameStrings));
        ArrayList<Nutrient> otherList = new ArrayList<>();

        for (Nutrient nutrient: combinedList) {
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

        populateVBox(sugarList,sugarVBox);
        populateVBox(fatList,fatVBox);
        populateVBox(vitaminList,vitaminVBox);
        populateVBox(mineralList,mineralVBox);
        populateVBox(otherList,otherVBox);
        populateVBox(macroList,macroVBox);

        float total = macroList.get(1).getNutrientValue() + macroList.get(2).getNutrientValue() + macroList.get(0).getNutrientValue();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Fat " + String.format("%.1f", macroList.get(1).getNutrientValue() / total * 100) + "%", macroList.get(1).getNutrientValue()),
                        new PieChart.Data("Carbohydrates " + String.format("%.1f", macroList.get(0).getNutrientValue() / total * 100) + "%", macroList.get(0).getNutrientValue()),
                        new PieChart.Data("Protein " + String.format("%.1f", macroList.get(2).getNutrientValue() / total * 100) + "%", macroList.get(2).getNutrientValue()));
        macroPieChart.setData(pieChartData);
    }

    public void searchEntries() {
        String comboBoxValue = (String) comboBox.getValue();
        LocalDate date = datePicker.getValue();

        ArrayList<Entry> searchedEntries;
        if (comboBoxValue.equals("Get Day")) {
            searchedEntries = profile.getDiary().getEntriesDay(date);
        } else {
            searchedEntries = profile.getDiary().getEntriesWeek(date);
        }
        populateEntries(searchedEntries);
    }

    public void populateVBox(ArrayList<Nutrient> list, VBox vbox) {
        int counter = 0;
        for (Node node: vbox.getChildren()) {
            HBox hbox = (HBox) node;
            for (Node childNode: hbox.getChildren()) {
                if (childNode.getId().equals("nameLabel")) {
                    Label label = (Label) childNode;
                    label.setText(list.get(counter).getNutrientName());
                } else if (childNode instanceof ProgressBar) {
                    ProgressBar progressBar = (ProgressBar) childNode;
                    if (list.get(counter).getNutrientValue() == 0) {
                        progressBar.setProgress(0);
                    } else  if (searchTargetNutrientsList(list.get(counter).getNutrientName(), profile.getDailyIntake().getTargetNutrients()) == 0) {
                        progressBar.setProgress(1);
                        progressBar.setId("notFound");
                    } else {
                        float percentValueToTarget = list.get(counter).getNutrientValue()/searchTargetNutrientsList(list.get(counter).getNutrientName(), profile.getDailyIntake().getTargetNutrients());
                        float maximumDose = searchTargetNutrientsList(list.get(counter).getNutrientName(),profile.getDailyIntake().getMaximumDoses());
                        if (maximumDose == 0) { //0 means that the target dose is maximum dose
                            maximumDose = searchTargetNutrientsList(list.get(counter).getNutrientName(), profile.getDailyIntake().getTargetNutrients());
                        }
                        boolean isOverMaximumDose = list.get(counter).getNutrientValue() > maximumDose;
                        progressBar.setProgress(percentValueToTarget);

                        if (percentValueToTarget >= 1 && isOverMaximumDose == false) { //targetDose hit but not over maximum dose
                            progressBar.setStyle("-fx-accent: green;");
                        } else if (isOverMaximumDose == true ) {
                            progressBar.setStyle("-fx-accent: red"); // if over maximum does
                        } else {
                            progressBar.setStyle("-fx-accent: #007bff;"); // not 100% but not over maximum dose
                        }
                    }
                } else if (childNode.getId().equals("percentLabel")) {
                    Label label = (Label) childNode;
                    if (list.get(counter).getNutrientValue() == 0) {
                        label.setText("0.0%");
                    } else  if (searchTargetNutrientsList(list.get(counter).getNutrientName(), profile.getDailyIntake().getTargetNutrients()) == 0) {
                        label.setText("Not Found");
                    } else {
                        String percent = String.format("%.1f", list.get(counter).getNutrientValue()/searchTargetNutrientsList(list.get(counter).getNutrientName(), profile.getDailyIntake().getTargetNutrients()) * 100);
                        label.setText(percent + "%");
                    }
                } else if (childNode.getId().equals("valueLabel")) {
                    Label label = (Label) childNode;
                    String value = String.format("%.1f", list.get(counter).getNutrientValue());
                    label.setText(value + getTargetNutrientsUnits(list.get(counter).getNutrientName()));
                }
            }
            counter++;
        }
    }

    // 0 = should be the targetdose, 99999999 = not found therefore no maximum dose, anything else actual value
     public static float searchTargetNutrientsList(String nutrientName, ArrayList<TargetNutrients> arrayList) {
        float returnValue = 99999999;
        DecimalFormat df = new DecimalFormat("0.0");
        for (TargetNutrients targetNutrients : arrayList) {
            if (nutrientName.contains(targetNutrients.getNutrientName())) {
                returnValue = targetNutrients.getValue();
            }
        }
        return Float.parseFloat(df.format(returnValue));
    }

    public String getTargetNutrientsUnits(String nutrientName) {
        String returnValue = "";
        for (TargetNutrients rdi : profile.getDailyIntake().getTargetNutrients()) {
            if (nutrientName.contains(rdi.getNutrientName())) {
                returnValue = rdi.getUnit();
            }
        }
        return returnValue;
    }
}
