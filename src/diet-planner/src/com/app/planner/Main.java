package com.app.planner;

import com.app.planner.mainscreencontroller.MainScreenController;
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
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainscreencontroller/mainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("com/app/planner/style.css");
        stage.setScene(scene);
        stage.show();
    }

    public ArrayList<Food> initialiseData() {
        ArrayList<Food> returnList = new ArrayList<>();
        // reads the entire array in the file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream in = getClass().getResourceAsStream("/data.json");
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        Food[] foods = gson.fromJson(reader,Food[].class);
        List<Food> foodsList = Arrays.asList(foods);
        returnList.addAll(foodsList);
        return returnList;
    }

    public File chooseFile(String windowType) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        File selectedFile = null;
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue;
        if (windowType.equals("save")) {
             returnValue =  jfc.showSaveDialog(null);
        } else {
            returnValue = jfc.showOpenDialog(null);
        }

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
        } else if (returnValue == JFileChooser.CANCEL_OPTION){
            System.out.println("FileChooser cancelled");
        }
        return selectedFile;
    }

    public ArrayList<Nutrient> combineNutrientList(ArrayList<Nutrient> sourceList) {
        ArrayList<Nutrient> returnList = new ArrayList<>();

        List<Nutrient> addedUpNutrientArrayList = sourceList.stream().collect(Collectors.groupingBy(nutrient -> nutrient.getNutrientName())).entrySet().stream()
                .map(e -> e.getValue().stream().reduce((f1, f2) -> new Nutrient(f1.getNutrientName(), f1.getNutrientValue() + f2.getNutrientValue())))
                .map(f -> f.get()).collect(Collectors.toList());

        returnList.addAll(addedUpNutrientArrayList);
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
        ArrayList<Food> returnList = new ArrayList<>();

        for (Food food: searchList) {
            List<String> foodNameList = Arrays.asList(food.getFoodName().toLowerCase().split(", "));
            if (foodNameList.containsAll(searchFoodList)) {
                returnList.add(food);
            }
        }
        return returnList;
    }

    public static void main(String[] args) {
        DatabaseConnection.login("kaj","test");

        launch(args);
    }
}