package com.app.planner.util;

public class Nutrient {
    private String nutrientName;
    private float nutrientValue;

    public Nutrient(String nutrientName, float nutrientValue) {
        this.nutrientName = nutrientName;
        this.nutrientValue = nutrientValue;
    }

    @Override
    public String toString() {
        return "\n Nutrient{" +
                "nutrientName='" + nutrientName + '\'' +
                ", nutrientValue=" + nutrientValue +
                "}";
    }
}
