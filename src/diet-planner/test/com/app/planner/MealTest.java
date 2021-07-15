package com.app.planner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealTest {

    private Meal meal;

    @BeforeEach
    public void setUp() {
        meal = new Meal();
        ArrayList<Food> returnList = new ArrayList<>();
        // reads the testData.json file which contains the first 3 foods from the database
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputStream in = getClass().getResourceAsStream("testData/testData.json");
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        Food[] foods = gson.fromJson(reader,Food[].class);
        List<Food> foodsList = Arrays.asList(foods);
        returnList.addAll(foodsList);
        meal.addFoods(returnList,new int[]{200,300,100}); //These are the testing portions
        System.out.println(meal.toString());
    }

    @Test
    public void testGetFoods() {
        assertEquals(3,meal.getFoods().size(),"Size of the foods should be 3");
        assertEquals(53,meal.getFoods().get(0).getNutrients().size(),"Size of the foods should be 53");
    }

    @Test
    public void testNutrientValues() {
        assertEquals(153.4 ,meal.getFoods().get(0).getNutrients().get(0).getNutrientValue(),0.0001);
        //this tests the amount of water in 200 grams of Ackee
        assertEquals(0.78 ,meal.getFoods().get(1).getNutrients().get(1).getNutrientValue(),0.0001);
        //this tests the amount of nitrogen in 300 grams of Agar
        assertEquals(0.2 ,meal.getFoods().get(2).getNutrients().get(2).getNutrientValue(),0.0001);
        //this tests the amount of protein in 100 grams of Agar (soaked and drained)
    }
}
