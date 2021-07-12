package com.app.planner;

import java.util.ArrayList;
import java.util.Collections;

public class Diary {
    private ArrayList<Entry> entries = new ArrayList<>();

    public Diary() {
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public ArrayList<Entry> getEntriesDay(int day) {
        ArrayList<Entry> returnList = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getTimeEaten().getDayOfMonth() == day) {
                returnList.add(entry);
            }
        }
        return returnList;
    }

    public ArrayList<Entry> getEntriesWeek(int day) {
        ArrayList<Entry> returnList = new ArrayList<>();
        int[] intArray = new int[] {day,day+1,day+2,day+3,day+4,day+5,day+6};
        for (Entry entry : entries) {
            for (int element: intArray) {
                if (entry.getTimeEaten().getDayOfMonth() == element) {
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
