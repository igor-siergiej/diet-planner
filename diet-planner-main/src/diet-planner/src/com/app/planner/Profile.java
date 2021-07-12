package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class Profile {
    private String profileName;
    private Diary diary;

    public Profile(String profileName, Diary diary) {
        this.profileName = profileName;
        this.diary = diary;
    }

    public Profile() {
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileName='" + profileName + '\'' +
                ", diary=" + diary +
                '}';
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

    public void saveToFile(File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter(file);
        gson.toJson(this,writer);
        writer.flush();
        writer.close();
    }

    public void loadFromFile(File file) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = new JsonReader(new FileReader(file));
        Profile profile = gson.fromJson(reader,Profile.class);
        this.setDiary(profile.getDiary());
        this.setProfileName(profile.getProfileName());
    }
}

