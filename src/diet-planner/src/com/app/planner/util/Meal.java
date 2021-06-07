package com.app.planner.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Meal {
    private String mealName;
    private ArrayList<Food> Foods = new ArrayList<>();
    private int[] foodPortion;

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
        System.out.println(Foods.toString());
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", Foods=" + Foods +
                '}';
    }
}
