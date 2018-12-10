package com.griddynamics.rpg.model.hero.race;

import com.griddynamics.rpg.model.hero.AttributesType;
import lombok.Getter;

import java.util.Map;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT;
import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_ATTRIBUTE_COST_PER_POINT;
import static com.griddynamics.rpg.util.constants.RaceConstants.*;

public class Human implements Race {
    @Getter
    private final Map<AttributesType, Double> attributesCostMap = Map.of(
            AttributesType.WILLPOWER, DEFAULT_ATTRIBUTE_COST_PER_POINT * HUMAN_WILLPOWER_COST_MULTIPLIER,
            AttributesType.AGILITY, DEFAULT_ATTRIBUTE_COST_PER_POINT * HUMAN_AGILITY_COST_MULTIPLIER,
            AttributesType.DEXTERITY, DEFAULT_ATTRIBUTE_COST_PER_POINT * HUMAN_DEXTERITY_COST_MULTIPLIER,
            AttributesType.STRENGTH, DEFAULT_ATTRIBUTE_COST_PER_POINT * HUMAN_STRENGTH_COST_MULTIPLIER,
            AttributesType.INTELLIGENCE, DEFAULT_ATTRIBUTE_COST_PER_POINT * HUMAN_INTELLIGENCE_COST_MULTIPLIER,
            AttributesType.CHARISMA, DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT * HUMAN_CHARISMA_COST_MULTIPLIER,
            AttributesType.STAMINA, DEFAULT_ATTRIBUTE_COST_PER_POINT * HUMAN_STAMINA_COST_MULTIPLIER
    );
    /**
     * calculates cost for adding 1 point to attribute
     *
     * @param attribute attribute
     * @return cost
     */
    @Override
    public double getRaceAttributeCost(AttributesType attribute) {
        return attributesCostMap.get(attribute);
    }

    @Override
    public RaceType getRaceType() {
        return RaceType.HUMAN;
    }
}
