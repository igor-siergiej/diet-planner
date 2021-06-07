package com.app.planner;

import com.app.planner.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainscreencontroller/mainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/app/planner/util/style.css");
        stage.setScene(scene);
        stage.show();
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

    public ArrayList<Food> initialiseData(File dataFile) {
        ArrayList<Food> returnList = new ArrayList<>();
        try {
            // reads the entire array in the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader("data.json"));
            Food[] foods = gson.fromJson(reader,Food[].class);
            List<Food> foodsList = Arrays.asList(foods);
            returnList.addAll(foodsList);

        } catch (FileNotFoundException e) {
            System.out.println("WARNING: History File Not Found.");
        }
        return returnList;
    }

    public ArrayList<Food> sortedFoodSearch(ArrayList<Food> foodsList, String searchWord) {
        ArrayList<Food> resultList = foodSearch(foodsList,searchWord);
        resultList.sort(new FoodComparator(searchWord));
        Collections.reverse(resultList);
        return resultList;
    }

    public ArrayList<Food> foodSearch(ArrayList<Food> searchList,String searchFood) {
        searchFood = searchFood.toLowerCase();
        List<String> searchFoodList = Arrays.asList(searchFood.split(" "));
        System.out.println(searchFoodList.toString());
        ArrayList<Food> returnList = new ArrayList<>();

        for (Food food: searchList) {
            List<String> foodNameList = Arrays.asList(food.getFoodName().toLowerCase().split(", "));
            if (foodNameList.containsAll(searchFoodList)) {
                returnList.add(food);
                System.out.println("i");
            }
        }
        return returnList;
    }

    public static void main(String[] args) {
        Main main = new Main();
        DatabaseConnection db = new DatabaseConnection();
        db.testConnection();
        ArrayList<Food> data = main.initialiseData(new File("data.json"));

        Meal meal = new Meal();
        meal.setMealName("ciabatta");
        meal.addFoods(main.sortedFoodSearch(data,"tomatoes"));


        Entry entry = new Entry(meal,LocalDateTime.now(),EntryType.BREAKFAST);
        Diary diary = new Diary();
        diary.addEntry(entry);


        Profile profile1 = new Profile("profile1",diary);
        System.out.println(profile1.toString());

        launch(args);
    }
}
