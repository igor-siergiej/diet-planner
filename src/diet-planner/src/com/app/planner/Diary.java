package com.app.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Diary {
    private ArrayList<Entry> entries = new ArrayList<>();

    public Diary() {
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public ArrayList<Entry> getEntriesDay(LocalDate localDate) {
        ArrayList<Entry> returnList = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getTimeEaten().getDayOfYear() == localDate.getDayOfYear() && entry.getTimeEaten().getYear() == localDate.getYear()) {
                returnList.add(entry);
            }
        }
        return returnList;
    }

    public ArrayList<Entry> getEntriesWeek(LocalDate localDate) {
        ArrayList<Entry> returnList = new ArrayList<>();
        LocalDate[] days = new LocalDate[] {localDate, localDate.plusDays(1), localDate.plusDays(2), localDate.plusDays(3), localDate.plusDays(4), localDate.plusDays(5), localDate.plusDays(6)};
        for (Entry entry : entries) {
            for (LocalDate day: days) {
                if (entry.getTimeEaten().getDayOfYear() == day.getDayOfYear()) {
                    returnList.add(entry);
                }
            }
        }
        return returnList;
    }

    public void sortEntries() {
        Collections.sort(entries);
    }

    public void reverseSortEntries() {
        Collections.reverse(entries);
    }

    public ArrayList<Entry> getAllEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "entries=" + entries +
                '}';
    }
}
