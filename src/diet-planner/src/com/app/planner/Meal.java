package com.app.planner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meal {
    private String mealName;
    private ArrayList<Food> foods = new ArrayList<>(MAX_FOOD_COUNT);

    private static final int MAX_FOOD_COUNT = 30;

    public Meal(String mealName, ArrayList<Food> foods) {
        this.mealName = mealName;
        this.foods = foods;
    }

    public Meal() {
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    //sets the nutritional values to be scaled with portions
    public void addFoods(ArrayList<Food> foodArrayList, List<Integer> foodPortions) {
        for (int i = 0; i < foodArrayList.size(); i++) {
            for (int j = 0; j < foodArrayList.get(i).getNutrients().size(); j++) {
                foodArrayList.get(i).getNutrients().get(j).setNutrientValue(foodArrayList.get(i).getNutrients().get(j).getNutrientValue() / 100 * foodPortions.get(i));
            }
        }
        foods = foodArrayList;
    }

    public void addFood(Food food, Integer portion) {
        for (Nutrient nutrient : food.getNutrients()) {
            nutrient.setNutrientValue(nutrient.getNutrientValue() / 100 * portion);
        }
        foods.add(food);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", foods=" + foods +
                '}';
    }

    public String getMealName() {
        return mealName;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }
}
