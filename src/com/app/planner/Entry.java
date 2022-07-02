package com.app.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Entry implements Comparable {
    private Meal meal;
    private LocalDateTime timeEaten;
    private EntryType entryType;

    public Entry(Meal meal, LocalDateTime timeEaten, EntryType entryType) {
        this.meal = meal;
        this.timeEaten = timeEaten;
        this.entryType = entryType;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public LocalDateTime getTimeEaten() {
        return timeEaten;
    }

    public void setTimeEaten(LocalDateTime timeEaten) {
        this.timeEaten = timeEaten;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "meal=" + meal +
                ", timeEaten=" + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(timeEaten) +
                ", entryType=" + entryType +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Entry entry = (Entry) o;
        return entry.getTimeEaten().compareTo(this.getTimeEaten());
    }

    public float getNutrientValueForEntry(String nutrientName) {
        float value = 0;
        ArrayList<Nutrient> nutrients = new ArrayList<>();
        for (Food food : this.getMeal().getFoods()) {
            nutrients.addAll(food.getNutrients());
        }
        ArrayList<Nutrient> combinedList = Main.combineNutrientList(nutrients);
        for (Nutrient nutrient : combinedList) {
            if (nutrient.getNutrientName().equals(nutrientName)) {
                value = nutrient.getNutrientValue();
            }
        }
        return value;
    }
}
