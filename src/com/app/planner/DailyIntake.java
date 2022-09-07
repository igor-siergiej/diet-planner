package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DailyIntake {
    private HashMap<String, TargetNutrients> targetNutrients = new HashMap<>();
    private HashMap<String, TargetNutrients> maximumDoses = new HashMap<>();
    private final String filePath = "./data/";
    private final String men19to50FilePath = filePath + "men19to50.json";
    private final String women19to50FilePath = filePath + "women19to50.json";
    private final String maximumDosesFilePath = filePath + "maximumDoses.json";
    private final String men14to18FilePath = filePath + "men14to18.json";
    private final String men50plusFilePath = filePath + "men50plus.json";
    private final String women14to18FilePath = filePath + "women14to18.json";
    private final String women50plusFilePath = filePath + "women50plus.json";
    private final String pregnantFilePath = filePath + "pregnant.json";
    private final String breastfeedingFilePath = filePath + "breastfeeding.json";

    public DailyIntake() {
    }

    public HashMap<String, TargetNutrients> getTargetNutrients() {
        return targetNutrients;
    }

    public HashMap<String, TargetNutrients> getMaximumDoses() {
        return maximumDoses;
    }

    public HashMap<String, TargetNutrients> loadNutrientsFromFile(String fileName) {
        ArrayList<TargetNutrients> rdiArrayList = new ArrayList<>();
        HashMap<String, TargetNutrients> targetNutrientsHashMap = new HashMap<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader(fileName));
            TargetNutrients[] rdiFromJson = gson.fromJson(reader, TargetNutrients[].class);
            List<TargetNutrients> rdiList = Arrays.asList(rdiFromJson);
            rdiArrayList.addAll(rdiList);
            for (TargetNutrients targetNutrients : rdiArrayList) {
                targetNutrientsHashMap.put(targetNutrients.getNutrientName(), targetNutrients);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return targetNutrientsHashMap;
    }

    public void setTargetNutrients(int age, String sex, boolean pregnant, boolean breastFeeding) {
        if (sex.equals("male")) {
            setNutrientsDependingOnAge(age, men14to18FilePath, men19to50FilePath, men50plusFilePath);
        } else {
            if (pregnant) {
                targetNutrients = loadNutrientsFromFile(pregnantFilePath);
            } else if (breastFeeding) {
                targetNutrients = loadNutrientsFromFile(breastfeedingFilePath);
            } else {
                setNutrientsDependingOnAge(age, women14to18FilePath, women19to50FilePath, women50plusFilePath);
            }
        }
    }

    private void setNutrientsDependingOnAge(int age, String women14to18FilePath, String women19to50FilePath, String women50plusFilePath) {
        if (age >= 14 && age <= 18) {
            targetNutrients = loadNutrientsFromFile(women14to18FilePath);
        } else if (age >= 19 && age <= 50) {
            targetNutrients = loadNutrientsFromFile(women19to50FilePath);
        } else if (age >= 51) {
            targetNutrients = loadNutrientsFromFile(women50plusFilePath);
        } else {
            targetNutrients = null;
        }
    }

    public void setMaximumDoses() {
        maximumDoses = loadNutrientsFromFile(maximumDosesFilePath);
    }

    public float getMaximumDose(String nutrientName) {
        float maximumDose = 0;
        if (maximumDoses.get(nutrientName) == null) { //if maxDose not found in hashmap then there is no maxDoge
            maximumDose = 99999999;
        } else if (maximumDoses.get(nutrientName).getValue() == 0.0) { // if maximum does = 0 means that the target dose is maximum dose
            maximumDose = targetNutrients.get(nutrientName).getValue(); //set the maximum dose to be the same as target dose
        } else {
            maximumDose = maximumDoses.get(nutrientName).getValue(); // set the maximum dose
        }
        return maximumDose;
    }

    public float getTargetDose(String nutrientName) {
        return targetNutrients.get(nutrientName).getValue();
    }

    public String getDoseUnit(String nutrientName) {
        return targetNutrients.get(nutrientName).getUnit();
    }
}
