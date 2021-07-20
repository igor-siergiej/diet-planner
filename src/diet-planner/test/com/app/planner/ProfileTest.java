package com.app.planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileTest {

    Profile profile;
    String testDataPath = "./test/com/app/planner/testData";

    @BeforeEach
    public void setUp() {
        profile = new Profile();
        profile.setProfileName("test123");
    }

    @Test
    public void testLoadProfile() throws FileNotFoundException {
        profile.loadFromFile(new File("./test/com/app/planner/testData/profile1.JSON"));
        assertEquals("profile1",profile.getProfileName(),"profile name should be loaded correctly from file");
        assertEquals("Tomatoes, canned, whole contents",profile.getDiary().getAllEntries().get(0).getMeal().getFoods().get(0).getFoodName(),"Food name should be loaded correctly from file");
    }

    @Test
    public void testSaveProfile() throws IOException {
        boolean fileExists = false;
        File test = new File(testDataPath);
        profile.saveToFile(new File(testDataPath + "/profile2.JSON"));
        for (String file : test.list()) {
            if (file.equals("profile2.JSON")) {
                fileExists = true;
            }
        }
        assertEquals(true,fileExists,"saved file should exist in the folder");
        profile.loadFromFile(new File(testDataPath + "/profile2.JSON"));
        assertEquals("test123",profile.getProfileName(),"profile name should be loaded correctly from file");
    }
}