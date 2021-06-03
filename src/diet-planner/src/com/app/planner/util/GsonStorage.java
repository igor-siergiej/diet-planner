package com.app.planner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GsonStorage {
    public void saveObjectToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // reads the entire array in the file
            JsonReader reader = new JsonReader(new FileReader("data.json"));
            Food[] foods = gson.fromJson(reader,Food[].class);
            List<Food> foodsList = Arrays.asList(foods);
            System.out.println(foodsList.toString());


        } catch (FileNotFoundException e) {
            System.out.println("WARNING: History File Not Found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
