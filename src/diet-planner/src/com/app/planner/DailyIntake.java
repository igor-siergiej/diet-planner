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
    private final String men19to50FilePath = "./data/men19to50.json";
    private final String women19to50FilePath = "./data/women19to50.json";
    private final String maximumDosesFilePath = "./data/maximumDoses.json";

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
    public void setTargetNutrients(int age, String sex, boolean pregnant, boolean breastFeeding) { // TODO expand this for the rest of profile data
        if (sex.equals("male")) {
            targetNutrients = loadTargetNutrientsFromFile(men19to50FilePath);
        } else {
            targetNutrients = loadTargetNutrientsFromFile(women19to50FilePath);
        }
    }

    public void setMaximumDoses() {
        maximumDoses = loadTargetNutrientsFromFile(maximumDosesFilePath);
    }
}
