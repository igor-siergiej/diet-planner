package com.app.planner.util;

public class Profile {
    private String profileName;
    private Diary diary;

    public Profile(String profileName, Diary diary) {
        this.profileName = profileName;
        this.diary = diary;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileName='" + profileName + '\'' +
                ", diary=" + diary +
                '}';
    }

    public void saveToFile() {

    }
}
