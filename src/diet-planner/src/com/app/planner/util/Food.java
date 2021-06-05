package com.app.planner.util;

import java.util.ArrayList;

public class Food{
    private String foodName;
    private ArrayList<Nutrient> nutrients;

    public Food(String foodName, ArrayList<Nutrient> nutrients) {
        this.foodName = foodName;
        this.nutrients = nutrients;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ",  \n";
    }

    public String getFoodName() {
        return foodName;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }
}

