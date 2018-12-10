package com.griddynamics.rpg.util.validators;

import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public final class BattleValidator {

    public static boolean isElf(Hero hero) {
        return hero.getRace().getRaceType().equals(RaceType.ELF);
    }

    public static boolean isRogue(Hero hero) {
        return hero.getSpecialization().getSpecializationType().equals(SpecializationType.ROGUE);
    }

    public static boolean RollElfPreparedLuck() {
        Random random = new Random();
        int result = random.nextInt(6) + 1;
        return result >= 4;
    }

    public static boolean RollRoguePrepared() {
        Random random = new Random();
        int result = random.nextInt(6) + 1;
        return result >= 5;
    }
}
