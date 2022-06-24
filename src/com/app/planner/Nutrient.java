package com.app.planner;

import java.text.DecimalFormat;

public class Nutrient implements Comparable {
    private String nutrientName;
    private float nutrientValue;

    public Nutrient(String nutrientName, float nutrientValue) {
        this.nutrientName = nutrientName;
        this.nutrientValue = nutrientValue;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "\n Nutrient{" +
                "nutrientName='" + nutrientName + '\'' +
                ", nutrientValue=" + df.format(nutrientValue) +
                "}";
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public float getNutrientValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.parseFloat(df.format(nutrientValue));
    }

    public void setNutrientValue(float nutrientValue) {
        this.nutrientValue = nutrientValue;
    }

    @Override
    public int compareTo(Object o) {
        Nutrient nutrient = (Nutrient) o;
        return nutrient.getNutrientName().compareTo(this.getNutrientName());
    }
}


