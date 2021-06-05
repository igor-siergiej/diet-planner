package com.app.planner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GsonStorage {
    public void saveObjectToJson() {


        

        
    }

    public void fileChooser() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = jfc.showOpenDialog(null);


        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    public List<Food> initialiseData(File dataFile) {
        List<Food> foodsList = null;
        try {
            // reads the entire array in the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader("data.json"));
            Food[] foods = gson.fromJson(reader,Food[].class);
            foodsList = Arrays.asList(foods);
            
        } catch (FileNotFoundException e) {
            System.out.println("WARNING: History File Not Found.");
        }
        return foodsList;
    }

    public void printSortedSearch(List<Food> foodsList, String searchWord) {
        List<Food> resultList = foodSearch(foodsList,searchWord);
        resultList.sort(new FoodComparator(searchWord));
        Collections.reverse(resultList);
        for (Food food:resultList) {
            System.out.println(food.toString());
        }
    }

    public List<Food> foodSearch(List<Food> searchList,String searchFood) {
        searchFood = searchFood.toLowerCase();
        List<String> searchFoodList = Arrays.asList(searchFood.split(" "));
        System.out.println(searchFoodList.toString());
        List<Food> returnList = new ArrayList<>();

        for (Food food: searchList) {
            List<String> foodNameList = Arrays.asList(food.getFoodName().toLowerCase().split(", "));
            if (foodNameList.containsAll(searchFoodList)) {
                returnList.add(food);
            }
        }
        return returnList;
    }




}



