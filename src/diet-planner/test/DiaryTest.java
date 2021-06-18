import com.app.planner.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiaryTest {

    private Diary diary = new Diary();

    @Before
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
    }

    @Test
    public void testGetEntriesDay() {
        assertEquals("Size of arrayList should be 2",2,diary.getEntriesDay(16).size());
        assertEquals("Size of arrayList should be 1",1,diary.getEntriesDay(17).size());
    }

    @Test
    public void testGetEntriesWeek() {
        assertEquals("Size of arrayList should be 4",4,diary.getEntriesWeek(15).size());
        assertEquals("Size of arrayList should be 2",2,diary.getEntriesWeek(1).size());
    }
}
