package com.app.planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DailyIntakeTest {

    DailyIntake dailyIntake;

    @BeforeEach
    void setup() {
        dailyIntake = new DailyIntake();
    }

    @Test
    void setTargetNutrientsTestMen14to18() {
        dailyIntake.setTargetNutrients(14,"male", false,false);
        assertEquals(2600, dailyIntake.getTargetNutrients().get("Energy (kcal)").getValue(),"men14to18 daily intake of calories should be 2600");
    }

    //TODO write more tests here for each file

    @Test
    void setTargetNutrientsTestLowerBound() {
        dailyIntake.setTargetNutrients(12,"male", false,false);
        assertNull(dailyIntake.getTargetNutrients());

        dailyIntake.setTargetNutrients(12,"female", false,false);
        assertNull(dailyIntake.getTargetNutrients());
    }
}

