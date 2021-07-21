package com.app.planner.viewnutrientscontroller;

import com.app.planner.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ViewNutrientsController {
    private Profile profile;
    private ArrayList<RDI> rdiArrayList;

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

    public void setProfile(Profile profile) {
        profile.setAge(20);
        profile.setBreastFeeding(false);
        profile.setPregnant(false);
        profile.setSex("male");
        this.profile = profile;
    }

    public void populateScreen() {
        ArrayList<Entry> entries = profile.getDiary().getAllEntries();
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
        rdiArrayList = loadRDI();

        populateVBox(sugarList,sugarVBox);
        populateVBox(fatList,fatVBox);
        populateVBox(vitaminList,vitaminVBox);
        populateVBox(mineralList,mineralVBox);
        populateVBox(otherList,otherVBox);
        populateVBox(macroList,macroVBox);


        float total = macroList.get(1).getNutrientValue() + macroList.get(2).getNutrientValue() + macroList.get(0).getNutrientValue();

        PieChart.Data slice1 = new PieChart.Data("Fat " + String.format("%.1f", macroList.get(1).getNutrientValue() / total * 100) + "%", macroList.get(1).getNutrientValue() );
        PieChart.Data slice2 = new PieChart.Data("Carbohydrates " + String.format("%.1f", macroList.get(2).getNutrientValue() / total * 100) + "%", macroList.get(2).getNutrientValue() );
        PieChart.Data slice3 = new PieChart.Data("Protein " + String.format("%.1f", macroList.get(0).getNutrientValue() / total * 100) + "%", macroList.get(0).getNutrientValue());

        macroPieChart.getData().add(slice1);
        macroPieChart.getData().add(slice2);
        macroPieChart.getData().add(slice3);
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
                    } else  if (searchRDIListValue(list.get(counter).getNutrientName()) == 0) {
                        progressBar.setProgress(1);
                        progressBar.setId("notFound");
                    } else {
                        float percent = list.get(counter).getNutrientValue()/searchRDIListValue(list.get(counter).getNutrientName());
                        progressBar.setProgress(percent);
                    }
                } else if (childNode.getId().equals("percentLabel")) {
                    Label label = (Label) childNode;
                    if (list.get(counter).getNutrientValue() == 0) {
                        label.setText("N/A");
                    } else  if (searchRDIListValue(list.get(counter).getNutrientName()) == 0) {
                        label.setText("Not Found");
                    } else {
                        String percent = String.format("%.1f", list.get(counter).getNutrientValue()/searchRDIListValue(list.get(counter).getNutrientName()) * 100);
                        label.setText(percent + "%");
                    }
                } else if (childNode.getId().equals("valueLabel")) {
                    Label label = (Label) childNode;
                    String value = String.format("%.1f", list.get(counter).getNutrientValue());
                    label.setText(value + searchRDIListUnit(list.get(counter).getNutrientName()));
                }
            }
            counter++;
        }
    }

    public ArrayList<RDI> loadRDI() {
        ArrayList<RDI> rdiArrayList = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader("./data/men19to50.json"));
            RDI[] rdiFromJson = gson.fromJson(reader,RDI[].class);
            List<RDI> rdiList = Arrays.asList(rdiFromJson);
            rdiArrayList.addAll(rdiList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rdiArrayList;
    }

    public float searchRDIListValue(String nutrientName) {
        float returnValue = 0;
        for (RDI rdi : rdiArrayList) {
            if (nutrientName.contains(rdi.getNutrientName())) {
                returnValue = rdi.getValue();
            }
        }
        return returnValue;
    }

    public String searchRDIListUnit(String nutrientName) {
        String returnValue = "";
        for (RDI rdi : rdiArrayList) {
            if (nutrientName.contains(rdi.getNutrientName())) {
                returnValue = rdi.getUnit();
            }
        }
        return returnValue;
    }

}
