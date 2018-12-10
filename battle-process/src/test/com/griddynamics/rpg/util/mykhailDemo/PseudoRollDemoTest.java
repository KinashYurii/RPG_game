package com.griddynamics.rpg.util.mykhailDemo;

import com.griddynamics.rpg.service.roll.DiceService;
import org.junit.jupiter.api.Test;

import static com.griddynamics.rpg.util.constants.RollConstants.MAX_BOUND;
import static com.griddynamics.rpg.util.constants.RollConstants.MAX_PERCENTAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PseudoRollDemoTest {
    private static int countTrue = 0;

    static double calculateChance(int minBound) {
        double newMinBound = MAX_BOUND - minBound + 1;

        return MAX_PERCENTAGE / MAX_BOUND / MAX_PERCENTAGE * newMinBound;
    }

    static void preparedRollDemo(int minBound) {
        for (int i = 0; i < 100; i++) {
            boolean result = DiceService.getInstance().preparedRoll(minBound);
            if (result) {
                countTrue++;
            }
        }
    }

    @Test
    void mustBeTrueMoreThan_50_Percentage() {
        int minBound = 4;
        double amountOfTrue = calculateChance(minBound);
        int chanceNat = (int) amountOfTrue * 100;
        for (int i = 0; i < 100; i++) {
            preparedRollDemo(minBound);
            assertEquals(true, countTrue > chanceNat);
            countTrue = 0;
        }
    }

}