package com.app.planner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws IOException {

        Main main = new Main();
        DatabaseConnection db = new DatabaseConnection();
        db.testConnection();

        Profile profile = new Profile();
        ArrayList<Food> data = main.initialiseData();
        System.out.println(data.toString());

        new File("profiles").mkdirs();

        //profile.loadFromFile(main.chooseFile("load"));
        //System.out.println(profile.toString());





        /*Meal meal = new Meal();
        meal.setMealName("ciabatta");
        meal.addFoods(main.sortedFoodSearch(data,"tomatoes"));

        Entry entry = new Entry(meal, LocalDateTime.now(),EntryType.BREAKFAST);
        Diary diary = new Diary();
        diary.addEntry(entry);
        diary.addEntry(entry);

        ArrayList<Nutrient> nutrientArrayList = new ArrayList<>();
        for (Entry entry5: diary.getEntriesDay(LocalDateTime.now().getDayOfMonth())) {
            for (Food food: entry5.getMeal().getFoods()) {
                nutrientArrayList.addAll(food.getNutrients());
            }
        }

        System.out.println(nutrientArrayList.toString());

        System.out.println(main.combineNutrientList(nutrientArrayList).toString());

        profile.setDiary(diary);
        profile.setProfileName("profile1");
        File file = main.chooseFile("save");
        profile.saveToFile(file);*/

        Main.main(args);
    }
}
