package com.app.planner;

import java.util.ArrayList;
import java.util.Arrays;

public class Meal {
    private String mealName;
    private ArrayList<Food> foods = new ArrayList<>();
    private int[] portions = new int[MAX_FOOD_COUNT];

    private static final int MAX_FOOD_COUNT = 30;

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    //sets the nutritional values to be scaled with portions
    public void addFoods(ArrayList<Food> foodArrayList,int[] foodPortions) {
        foods = foodArrayList;
        portions = foodPortions;

        for (int i = 0; i < foods.size(); i++) {
            for (int j = 0; j < foods.get(i).getNutrients().size(); j++) {
                foods.get(i).getNutrients().get(j).setNutrientValue(foods.get(i).getNutrients().get(j).getNutrientValue() / 100 * portions[i]);
            }
        }
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", Foods=" + foods +
                ", portions=" + Arrays.toString(portions) +
                '}';
    }

    public String getMealName() {
        return mealName;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public int[] getPortions() {
        return portions;
    }

    public void setPortions(int[] portions) {
        this.portions = portions;
    }
}
