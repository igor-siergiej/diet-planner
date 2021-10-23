package com.app.planner;

public class Macro {
    float carbohydrates;
    float fat;
    float protein;

    public Macro(float carbohydrates, float fat, float protein) {
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }
}
