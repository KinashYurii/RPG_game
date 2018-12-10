package com.griddynamics.rpg.model.hero.race;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.AttributesType;
import lombok.Getter;

import java.util.Map;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT;
import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_ATTRIBUTE_COST_PER_POINT;
import static com.griddynamics.rpg.util.constants.RaceConstants.*;

public class Dwarf implements Race {
    @Getter
    private final Map<AttributesType, Double> attributesCostMap = Map.of(
            AttributesType.WILLPOWER, DEFAULT_ATTRIBUTE_COST_PER_POINT * DWARF_WILLPOWER_COST_MULTIPLIER,
            AttributesType.AGILITY, DEFAULT_ATTRIBUTE_COST_PER_POINT * DWARF_AGILITY_COST_MULTIPLIER,
            AttributesType.DEXTERITY, DEFAULT_ATTRIBUTE_COST_PER_POINT * DWARF_DEXTERITY_COST_MULTIPLIER,
            AttributesType.STRENGTH, DEFAULT_ATTRIBUTE_COST_PER_POINT * DWARF_STRENGTH_COST_MULTIPLIER,
            AttributesType.INTELLIGENCE, DEFAULT_ATTRIBUTE_COST_PER_POINT * DWARF_INTELLIGENCE_COST_MULTIPLIER,
            AttributesType.CHARISMA, DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT * DWARF_CHARISMA_COST_MULTIPLIER,
            AttributesType.STAMINA, DEFAULT_ATTRIBUTE_COST_PER_POINT * DWARF_STAMINA_COST_MULTIPLIER
    );
    /**
     * method applying racial bonuses
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applyRacialBonuses(Attributes attributes) {
        attributes.setStamina(attributes.getStamina() + DWARF_STAMINA_BONUS);
        attributes.setStrength(attributes.getStrength() + DWARF_STRENGTH_BONUS);
        attributes.setDexterity(attributes.getDexterity() + DWARF_DEXTERITY_BONUS);
        attributes.setWillpower(attributes.getWillpower() + DWARF_WILLPOWER_BONUS);
    }
    /**
     * method applying racial penalties
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applyRacialPenalties(Attributes attributes) {
        attributes.setIntelligence(attributes.getIntelligence() - DWARF_INTELLIGENCE_PENALTY);
        attributes.setAgility(attributes.getAgility() - DWARF_AGILITY_PENALTY);
        attributes.setCharisma(attributes.getCharisma() - DWARF_CHARISMA_PENALTY);
    }
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
        return RaceType.DWARF;
    }
}
