package com.app.planner;

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
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainscreencontroller/mainScreen.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("com/app/planner/style.css");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Food> initialiseData() { //this will load the json file with the dataset to an arraylist
        ArrayList<Food> returnList = new ArrayList<>();
        // reads the entire array in the file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream in = Main.class.getResourceAsStream("/data.json");
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        Food[] foods = gson.fromJson(reader,Food[].class);
        List<Food> foodsList = Arrays.asList(foods);
        returnList.addAll(foodsList);
        return returnList;
    }

    public static File chooseFile(String windowType) {  //used to pick file to save or load from
        File selectedFile = null;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue;
            if (windowType.equals("save")) {
                returnValue = jfc.showSaveDialog(null);
            } else {
                returnValue = jfc.showOpenDialog(null);
            }

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = jfc.getSelectedFile();
            } else if (returnValue == JFileChooser.CANCEL_OPTION){
                System.out.println("FileChooser cancelled");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return selectedFile;
    }

    public static ArrayList<Nutrient> combineNutrientList(ArrayList<Nutrient> sourceList) { //given a long list of duplicate list of nutrients it will join all of them into one by adding together all the nutrients if they match names
        ArrayList<Nutrient> returnList = new ArrayList<>();

        List<Nutrient> addedUpNutrientArrayList = sourceList.stream().collect(Collectors.groupingBy(nutrient -> nutrient.getNutrientName())).entrySet().stream()
                .map(e -> e.getValue().stream().reduce((f1, f2) -> new Nutrient(f1.getNutrientName(), f1.getNutrientValue() + f2.getNutrientValue())))
                .map(f -> f.get()).collect(Collectors.toList());

        returnList.addAll(addedUpNutrientArrayList);
        return returnList;
    }

    public static ArrayList<Food> sortedFoodSearch(ArrayList<Food> foodsList, String searchWord) { // will list the results of serach from dataset in order of closest to the string searchWord
        ArrayList<Food> resultList = foodSearch(foodsList,searchWord);
        resultList.sort(new FoodComparator(searchWord));
        Collections.reverse(resultList);
        return resultList;
    }

    public static ArrayList<Food> foodSearch(ArrayList<Food> searchList,String searchFood) {
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
        launch(args);
    }
}