package com.griddynamics.rpg.util.constants;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.function.Predicate;

@UtilityClass
public final class SpecializationConstants {
    public static final int ARCHER_DEXTERITY_BONUS = 1;
    public static final int ARCHER_AGILITY_PENALTY = 1;

    public static final int ROGUE_DEXTERITY_BONUS = 2;
    public static final int ROGUE_STRENGTH_PENALTY = 1;
    public static final int ROGUE_WILLPOWER_PENALTY = 1;

    public static final int SORCERER_WILLPOWER_BONUS = 1;
    public static final int SORCERER_INTELLIGENCE_BONUS = 1;
    public static final int SORCERER_AGILITY_PENALTY = 2;

    public static final int SWORDSMAN_STAMINA_BONUS = 2;
    public static final int SWORDSMAN_AGILITY_PENALTY = 1;
    public static final int SWORDSMAN_DEXTERITY_PENALTY = 1;

    public static final int WARRIOR_STRENGTH_BONUS = 2;
    public static final int WARRIOR_AGILITY_PENALTY = 1;
    public static final int WARRIOR_DEXTERITY_PENALTY = 1;

    private static final Integer REQUIRED_STAMINA_VALUE = 10;
    private static final Integer REQUIRED_AGILITY_VALUE = 10;
    private static final Integer REQUIRED_INTELLECT_VALUE = 10;
    private static final Integer REQUIRED_STRENGTH_VALUE = 12;
    private static final Integer REQUIRED_DEXTERITY_VALUE = 10;

    public static final Map<SpecializationType, Predicate<Attributes>> SPECIALIZATION_REQUIREMENTS = Map.of(
            SpecializationType.SWORDSMAN, x -> x.getStamina() >= REQUIRED_STAMINA_VALUE,
            SpecializationType.ARCHER, x -> x.getAgility() >= REQUIRED_AGILITY_VALUE,
            SpecializationType.SORCERER, x -> x.getIntelligence() >= REQUIRED_INTELLECT_VALUE,
            SpecializationType.WARRIOR, x -> x.getStrength() >= REQUIRED_STRENGTH_VALUE,
            SpecializationType.ROGUE, x -> x.getDexterity() >= REQUIRED_DEXTERITY_VALUE
                    && x.getAgility() >= REQUIRED_AGILITY_VALUE
    );
}
