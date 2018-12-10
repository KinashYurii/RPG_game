package com.griddynamics.rpg.util.constants;

import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.service.hero.AttributesService;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Hero Constants
 */
@UtilityClass
public final class HeroDataConstants {
    public static final int DEFAULT_CHARACTERISTIC = 10;
    public static final int DEFAULT_POINTS_QUANTITY = 30;
    public static final double DEFAULT_ATTRIBUTE_COST_PER_POINT = 2;
    public static final double DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT = 4;
    public static final AttributesService ATTRIBUTES_SERVICE = AttributesService.getInstance();
    public static final Map<Integer, Consumer<Hero>> ADD_ATTRIBUTE_ACTION_MAP = Map.of(
            1, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.WILLPOWER),
            2, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.AGILITY),
            3, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.DEXTERITY),
            4, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.STRENGTH),
            5, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.INTELLIGENCE),
            6, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.CHARISMA),
            7, hero -> ATTRIBUTES_SERVICE.addAttributePoint(hero, AttributesType.STAMINA)
    );
    public static final double HEALTH_COEFFICIENT = 0.65;
    public static final double MANA_COEFFICIENT = 0.4;
    public static final double RAGE_COEFFICIENT = 0.25;
    public static final double ARMOR_COEFFICIENT_LOWER_30_POINTS = 0.12;
    public static final double ARMOR_COEFFICIENT_HIGHER_30_POINTS = 0.8;
    public static final int ARMOR_CALCULATION_THRESHOLD = 30;
    public static final double REGENERATE_HEALTH_COF_PER_ROUND = 0.15;
    public static final double REGENERATE_HEALTH_COF_PER_LOCATION = 2;
    public static final int MAX_HEALTH_PER_LOCATION = 50;
    public static final int STRENGTH_COF_FOR_HEALTH = 4;
    public static final int STAMINA_COF_FOR_HEALTH = 3;
    public static final double REGENERATE_MANA_COEFFICIENT = 0.1;
    public static final int WILLPOWER_COF_FOR_MANA = 3;
    public static final int INTELLECT_COF_FOR_MANA = 3;
    public static final double REGENERATE_RAGE_COEFFICIENT = 0.05;
    public static final int STRENGTH_COF_FOR_RAGE = 4;
    public static final int STAMINA_COF_FOR_RAGE = 2;
    public static final double MAGIC_RESSISTANCE_CALCULATION_COEFFICIENT_1 = 0.91;
    public static final int MAGIC_RESSISTANCE_CALCULATION_STAMINA = 2;
    public static final double MAGIC_RESSISTANCE_CALCULATION_STAMINA_COEFFICIENT = 0.04;
    public static final int MAGIC_RESSISTANCE_CALCULATION_COEFFICIENT_2 = 2;

}
