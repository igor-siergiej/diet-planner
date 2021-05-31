package com.app.planner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class GsonStorage {
    public void saveObjectToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            // reads the entire array in the file
            Reader reader = Files.newBufferedReader(Paths.get("test.json"));
            Food burger = gson.fromJson(reader, Food.class);
            System.out.println(burger.toString());

        } catch (FileNotFoundException e) {
            System.out.println("WARNING: History File Not Found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
