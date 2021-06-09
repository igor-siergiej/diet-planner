package com.app.planner.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Meal {
    private String mealName;
    private ArrayList<Food> Foods = new ArrayList<>();
    private int[] portions = new int[MAX_FOOD_COUNT];

    private static final int MAX_FOOD_COUNT = 30;

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void addFoods(ArrayList<Food> foodArrayList) {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < foodArrayList.size(); i++) {
            System.out.println(i + foodArrayList.get(i).toString());
        }
        System.out.println("Enter which food to add from the above list");
        int num = in.nextInt();
        Foods.add(foodArrayList.get(num));
        System.out.println("Enter the portion of the food");
        int portion = in.nextInt();
        portions[Foods.size() -1] = portion;
        System.out.println(Foods.toString());
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", Foods=" + Foods +
                ", portions=" + Arrays.toString(portions) +
                '}';
    }

    public String getMealName() {
        return mealName;
    }

    public ArrayList<Food> getFoods() {
        for (int i = 0; i < Foods.size(); i++) {
            for (int j = 0; j < Foods.get(i).getNutrients().size(); j++) {
                Foods.get(i).getNutrients().get(j).setNutrientValue(Foods.get(i).getNutrients().get(j).getNutrientValue() / 100 * portions[i]);
            }
        }
        return Foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        Foods = foods;
    }

    public int[] getPortions() {
        return portions;
    }

    public void setPortions(int[] portions) {
        this.portions = portions;
    }
}
