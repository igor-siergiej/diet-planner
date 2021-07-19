package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.time.LocalDateTime;
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
            stage.getIcons().add(new Image("com/app/planner/img/icon.png"));
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
        Profile profile = new Profile();
        Diary diary = new Diary();
        ArrayList<Food> data = initialiseData();
        System.out.println(foodSearch(data,"tomatoes"));
        ArrayList<Food> foodsList = new ArrayList<>();
        foodsList.add(foodSearch(data,"tomatoes").get(0));
        foodsList.add(foodSearch(data,"potatoes").get(0));
        Meal meal = new Meal();
        meal.addFoods(foodsList, new int[]{100,100});
        Entry entry2 = new Entry(meal, LocalDateTime.now(),EntryType.DINNER);
        Entry entry1 = new Entry(meal,LocalDateTime.now(),EntryType.SNACK);
        diary.addEntry(entry1);
        diary.addEntry(entry2);
        profile.setDiary(diary);

        System.out.println(profile.toString());
        ArrayList<Entry> entries = profile.getDiary().getAllEntries();
        ArrayList<Nutrient> nutrients = new ArrayList<>();
        for (Entry entry : entries) {
            for (Food food :entry.getMeal().getFoods()) {
                nutrients.addAll(food.getNutrients());
            }
        }
        ArrayList<Nutrient> combinedList = combineNutrientList(nutrients);
        Collections.sort(combinedList);
        System.out.println(combinedList.toString());

        ArrayList<String> vitaminNameList = new ArrayList<>();
        String[] vitaminNameStrings = {"Retinol (µg)","Carotene (µg)","Retinol Equivalent (µg)","Vitamin D (µg)","Vitamin E (mg)","Vitamin K1 (µg)","Thiamin (mg)","Riboflavin (mg)","Niacin (mg)","Tryptophan (mg)","Niacin equivalent (mg)","Vitamin B6 (mg)","Vitamin B12 (µg)","Folate (µg)","Pantothenate (mg)","Biotin (µg)","Vitamin C (mg)"};
        vitaminNameList.addAll(Arrays.asList(vitaminNameStrings));
        ArrayList<Nutrient> vitaminList = new ArrayList<>();

        ArrayList<String> mineralNameList = new ArrayList<>();
        String[] mineralNameStrings = {"Sodium (mg)","Potassium (mg)","Calcium (mg)","Magnesium (mg)","Phosphorus (mg)","Iron (mg)","Copper (mg)","Zinc (mg)","Chloride (mg)","Manganese (mg)","Selenium (µg)","Iodine (µg)"};
        mineralNameList.addAll(Arrays.asList(mineralNameStrings));
        ArrayList<Nutrient> mineralList = new ArrayList<>();

        ArrayList<String> sugarNameList = new ArrayList<>();
        String[] sugarNameStrings = {"Total sugars (g)","Glucose (g)","Galactose (g)","Fructose (g)","Sucrose (g)","Maltose (g)","Lactose (g)"};
        sugarNameList.addAll(Arrays.asList(sugarNameStrings));
        ArrayList<Nutrient> sugarList = new ArrayList<>();

        ArrayList<String> fatNameList = new ArrayList<>();
        String[] fatNameStrings = {"Trans FAs (g)","Saturated Fat (g)","Omega 6 (g)","Omega 3 (g)","Mono FA  (g)","Poly FA (g)","Cholesterol (mg)"};
        fatNameList.addAll(Arrays.asList(fatNameStrings));
        ArrayList<Nutrient> fatList = new ArrayList<>();

        ArrayList<String> otherNameList = new ArrayList<>();
        String[] otherNameStrings = {"Water (g)","Total nitrogen (g)","Protein (g)","Fat (g)","Carbohydrate (g)","Energy (kcal) (kcal)","Energy (kJ) (kJ)","Starch (g)","NSP (g)","AOAC fibre (g)"};
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
            } else {
                System.out.println(nutrient.toString());
            }
        }

        System.out.println(vitaminList);
        System.out.println(mineralList);
        System.out.println(sugarList);
        System.out.println(fatList);
        System.out.println(otherList);

        launch(args);
    }
}