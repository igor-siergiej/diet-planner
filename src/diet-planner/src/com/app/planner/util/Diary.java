package com.app.planner.util;

import java.util.ArrayList;

public class Diary {
    private ArrayList<Entry> entries = new ArrayList<>();

    public Diary() {
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public ArrayList<Entry> getEntriesDay(int day) {
        ArrayList<Entry> returnList = new ArrayList<>();
        return returnList;
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
