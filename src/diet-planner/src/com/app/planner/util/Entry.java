package com.app.planner.util;

import java.time.LocalDateTime;

public class Entry {
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
                ", timeEaten=" + timeEaten +
                ", entryType=" + entryType +
                '}';
    }
}
