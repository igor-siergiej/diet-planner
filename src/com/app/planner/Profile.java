package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Profile {
    private String username;
    private String password;
    private String profileName;
    private int age;
    private String sex;
    private boolean pregnant;
    private boolean breastFeeding;
    private Diary diary;
    private Option options;
    private DailyIntake dailyIntake;

    // stack for undo and redo

    public Profile(String username, String password, String profileName, int age, String sex, boolean pregnant, boolean breastFeeding, Diary diary, Option options) {
        this.username = username;
        this.password = password;
        this.profileName = profileName;
        this.age = age;
        this.sex = sex;
        this.pregnant = pregnant;
        this.breastFeeding = breastFeeding;
        this.diary = diary;
        this.options = options;

        dailyIntake = new DailyIntake();
        dailyIntake.setMaximumDoses();
        dailyIntake.setTargetNutrients(age, sex, pregnant, breastFeeding);
    }

    public Profile() {
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profileName='" + profileName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", pregnant=" + pregnant +
                ", breastFeeding=" + breastFeeding +
                ", diary=" + diary +
                ", options=" + options +
                ", dailyIntake=" + dailyIntake +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public boolean isBreastFeeding() {
        return breastFeeding;
    }

    public void setBreastFeeding(boolean breastFeeding) {
        this.breastFeeding = breastFeeding;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Option getOptions() {
        return options;
    }

    public void setOptions(Option options) {
        this.options = options;
    }

    public DailyIntake getDailyIntake() {
        return dailyIntake;
    }

    public void setDailyIntake(DailyIntake dailyIntake) {
        this.dailyIntake = dailyIntake;
    }

    public void saveToFile(File file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(File file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader(file));
            Profile profile = gson.fromJson(reader, Profile.class);
            this.setDiary(profile.getDiary());
            this.setProfileName(profile.getProfileName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadFromString(String loadString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Profile profile = gson.fromJson(loadString, Profile.class);
        this.setProfileName(profile.getProfileName());
        this.setDiary(profile.getDiary());
    }

    public String saveToString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String returnString = gson.toJson(this);
        return returnString;
    }

    public float getNutrientValueForCurrentDay(String nutrientName) {
        float value = 0;
        ArrayList<Nutrient> nutrients = new ArrayList<>();
        for (Entry entry : getDiary().getEntriesDay(LocalDate.now())) {
            for (Food food : entry.getMeal().getFoods()) {
                nutrients.addAll(food.getNutrients());
            }
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

