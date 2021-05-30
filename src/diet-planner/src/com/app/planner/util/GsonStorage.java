package com.app.planner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonStorage {
    public void saveObjectToJson() {
        Food pizza = new Food();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(pizza);
        System.out.println(json);

        Food burger = gson.fromJson(json, Food.class);
        System.out.println(burger.toString());
    }
}
