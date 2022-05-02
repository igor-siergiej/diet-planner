package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("startscreencontroller/StartScreen.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("com/app/planner/style.css");
            stage.getIcons().add(new Image("com/app/planner/img/icon.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FXMLLoader goToScreen(ActionEvent event, String fxmlFilePath) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(Main.class.getResource("/com/app/planner/" + fxmlFilePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setWindow(event, root);
        return loader;
    }

    private static void setWindow(ActionEvent event, Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/com/app/planner/style.css");
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    public static ArrayList<Food> initialiseData() { //this will load the json file with the dataset to an arraylist
        ArrayList<Food> returnList = new ArrayList<>();
        // reads the entire array in the file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream in = Main.class.getResourceAsStream("/data.json");
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        Food[] foods = gson.fromJson(reader, Food[].class);
        List<Food> foodsList = Arrays.asList(foods);
        returnList.addAll(foodsList);
        return returnList;
    }

    public static File chooseLoadFile(Pane pane) {  //used to pick file to save or load from
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) pane.getScene().getWindow(); // getting the window of the pane
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.JSON")
        );

        File file = fileChooser.showOpenDialog(stage);//setting the window to be locked.
        return file;
    }

    public static File chooseSaveFile(Pane pane) {

        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) pane.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.JSON")
        );

        File file = fileChooser.showSaveDialog(stage);
        return file;
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
        ArrayList<Food> resultList = foodSearch(foodsList, searchWord);
        resultList.sort(new FoodComparator(searchWord));
        Collections.reverse(resultList);
        return resultList;
    }

    public static Food exactFoodSearch(ArrayList<Food> searchList, String searchFood) {
        Food returnFood = null;
        for (Food food : searchList) {
            if (food.getFoodName().equals(searchFood)) {
                returnFood = food;
            }
        }
        return returnFood;
    }

    public static ArrayList<Food> foodSearch(ArrayList<Food> searchList, String searchFood) {
        searchFood = searchFood.toLowerCase();
        List<String> searchFoodList = Arrays.asList(searchFood.split(" "));
        ArrayList<Food> returnList = new ArrayList<>();

        for (Food food : searchList) {
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