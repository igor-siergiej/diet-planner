package com.app.planner;

import java.util.ArrayList;

public class Application {
    public static void main(String[] args) {

        Main main = new Main();
        DatabaseConnection db = new DatabaseConnection();
        db.testConnection();

        Profile profile = new Profile();
        ArrayList<Food> data = main.initialiseData(main.chooseFile());
        System.out.println(data.toString());




        /*
        Meal meal = new Meal();
        meal.setMealName("ciabatta");
        meal.addFoods(main.sortedFoodSearch(data,"tomatoes"));

        Entry entry = new Entry(meal,LocalDateTime.now(),EntryType.BREAKFAST);
        Entry entry1 = new Entry(meal,LocalDateTime.now(),EntryType.BREAKFAST);
        Diary diary = new Diary();
        diary.addEntry(entry);
        diary.addEntry(entry1);

        ArrayList<Nutrient> nutrientArrayList = new ArrayList<>();
        for (Entry entry5: diary.getEntriesDay(LocalDateTime.now().getDayOfMonth())) {
            for (Food food: entry5.getMeal().getFoods()) {
                nutrientArrayList.addAll(food.getNutrients());
            }
        }

        System.out.println(nutrientArrayList.toString());

        System.out.println(main.combineNutrientList(nutrientArrayList).toString());

        Profile profile1 = new Profile("profile1",diary);
        System.out.println(profile1.toString());*/
        Main.main(args);
    }
}
