package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DailyIntake {
    private ArrayList<TargetNutrients> targetNutrients;
    private ArrayList<TargetNutrients> maximumDoses;
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

    public ArrayList<TargetNutrients> getTargetNutrients() {
        return targetNutrients;
    }

    public ArrayList<TargetNutrients> getMaximumDoses() {
        return maximumDoses;
    }

    public ArrayList<TargetNutrients> loadTargetNutrientsFromFile(String fileName) {
        ArrayList<TargetNutrients> rdiArrayList = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader(fileName));
            TargetNutrients[] rdiFromJson = gson.fromJson(reader, TargetNutrients[].class);
            List<TargetNutrients> rdiList = Arrays.asList(rdiFromJson);
            rdiArrayList.addAll(rdiList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rdiArrayList;
    }

    public void setTargetNutrients(int age, String sex, boolean pregnant, boolean breastFeeding) {
        if (sex.equals("male")) {
            if (age >= 14 && age <= 18) {
                targetNutrients = loadTargetNutrientsFromFile(men14to18FilePath);
            } else if (age >= 19 && age <= 50) {
                targetNutrients = loadTargetNutrientsFromFile(men19to50FilePath);
            } else if (age >= 51) {
                targetNutrients = loadTargetNutrientsFromFile(men50plusFilePath);
            } else {
                targetNutrients = null;
            }
        } else {
            if (pregnant) {
                targetNutrients = loadTargetNutrientsFromFile(pregnantFilePath);
            } else if (breastFeeding) {
                targetNutrients = loadTargetNutrientsFromFile(breastfeedingFilePath);
            } else {
                if (age >= 14 && age <= 18) {
                    targetNutrients = loadTargetNutrientsFromFile(women14to18FilePath);
                } else if (age >= 19 && age <= 50) {
                    targetNutrients = loadTargetNutrientsFromFile(women19to50FilePath);
                } else if (age >= 51) {
                    targetNutrients = loadTargetNutrientsFromFile(women50plusFilePath);
                } else {
                    targetNutrients = null;
                }
            }
        }
    }

    public void setMaximumDoses() {
        maximumDoses = loadTargetNutrientsFromFile(maximumDosesFilePath);
    }
}
