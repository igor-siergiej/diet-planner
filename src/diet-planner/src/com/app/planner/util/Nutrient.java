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

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public float getNutrientValue() {
        return nutrientValue;
    }

    public void setNutrientValue(float nutrientValue) {
        this.nutrientValue = nutrientValue;
    }
}
