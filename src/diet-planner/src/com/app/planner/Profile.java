package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public void saveToFile(File file, Boolean overwrite) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonReader reader = new JsonReader(new FileReader(file));
        Profile[] profiles = gson.fromJson(reader,Profile[].class);
        ArrayList<Profile> profileArrayList = new ArrayList<>();
        Collections.addAll(profileArrayList,profiles);

        for (Profile profile : profileArrayList) {
            if (profile.getProfileName().equals(this.getProfileName()) && overwrite) {
                profileArrayList.set(profileArrayList.indexOf(profile),this);
            } else {
                profileArrayList.add(this);
            }
        }
        gson.toJson(profileArrayList,new FileWriter(file,false));
    }
}

