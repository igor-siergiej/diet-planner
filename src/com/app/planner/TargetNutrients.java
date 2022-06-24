package com.app.planner;

public class TargetNutrients {
    private String nutrientName;
    private String unit;
    private float value;

    public TargetNutrients() {
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RDI{" +
                "nutrientName='" + nutrientName + '\'' +
                ", unit='" + unit + '\'' +
                ", value=" + value +
                '}';
    }
}
