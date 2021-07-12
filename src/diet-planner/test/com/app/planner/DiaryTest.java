package com.app.planner;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiaryTest {

    private Diary diary = new Diary();

    @BeforeEach
    public void setUp() {
        LocalDateTime localDateTime = LocalDateTime.parse("2021-06-15T06:30:00");
        LocalDateTime localDateTime1 = LocalDateTime.parse("2021-06-16T06:30:00");
        LocalDateTime localDateTime2 = LocalDateTime.parse("2021-06-16T06:30:00");
        LocalDateTime localDateTime3 = LocalDateTime.parse("2021-06-17T06:30:00");
        LocalDateTime localDateTime4 = LocalDateTime.parse("2021-06-01T06:30:00");
        LocalDateTime localDateTime5 = LocalDateTime.parse("2021-06-02T06:30:00");
        Entry entry = new Entry(new Meal(),localDateTime, EntryType.BREAKFAST);
        Entry entry1 = new Entry(new Meal(),localDateTime1, EntryType.BREAKFAST);
        Entry entry2 = new Entry(new Meal(),localDateTime2, EntryType.BREAKFAST);
        Entry entry3 = new Entry(new Meal(),localDateTime3, EntryType.BREAKFAST);
        Entry entry4 = new Entry(new Meal(),localDateTime4, EntryType.BREAKFAST);
        Entry entry5 = new Entry(new Meal(),localDateTime5, EntryType.BREAKFAST);
        diary.addEntry(entry);
        diary.addEntry(entry1);
        diary.addEntry(entry2);
        diary.addEntry(entry3);
        diary.addEntry(entry4);
        diary.addEntry(entry5);
        diary.sortEntries();
    }

    @Test
    public void testGetEntriesDay() {
        assertEquals(2,diary.getEntriesDay(16).size(),"Size of arrayList should be 2");
        assertEquals(1,diary.getEntriesDay(17).size(),"Size of arrayList should be 1");
    }

    @Test
    public void testGetEntriesWeek() {
        assertEquals(4,diary.getEntriesWeek(15).size(),"Size of arrayList should be 4");
        assertEquals(2,diary.getEntriesWeek(1).size(),"Size of arrayList should be 2");
    }

    @Test
    public void testEntriesSort() {
        assertEquals(diary.getAllEntries().get(0).getTimeEaten(), LocalDateTime.parse("2021-06-17T06:30:00"), "Entries should be sorted");
    }

    @Nested
    class TestNest{

        @BeforeEach
        public void init() {
            diary.reverseSortEntries();
        }

        @Test
        public void testEntriesReverseSort() {
            assertEquals(diary.getAllEntries().get(0).getTimeEaten(), LocalDateTime.parse("2021-06-01T06:30:00"), "Entries should be reversed");
        }



    }


}
