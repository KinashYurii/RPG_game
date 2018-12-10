package com.griddynamics.rpg.model.hero.race;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.AttributesType;
import lombok.Getter;

import java.util.Map;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT;
import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_ATTRIBUTE_COST_PER_POINT;
import static com.griddynamics.rpg.util.constants.RaceConstants.*;

public class Elf implements Race {
    @Getter
    private final Map<AttributesType, Double> attributesCostMap = Map.of(
            AttributesType.WILLPOWER, DEFAULT_ATTRIBUTE_COST_PER_POINT * ELF_WILLPOWER_COST_MULTIPLIER,
            AttributesType.AGILITY, DEFAULT_ATTRIBUTE_COST_PER_POINT * ELF_AGILITY_COST_MULTIPLIER,
            AttributesType.DEXTERITY, DEFAULT_ATTRIBUTE_COST_PER_POINT * ELF_DEXTERITY_COST_MULTIPLIER,
            AttributesType.STRENGTH, DEFAULT_ATTRIBUTE_COST_PER_POINT * ELF_STRENGTH_COST_MULTIPLIER,
            AttributesType.INTELLIGENCE, DEFAULT_ATTRIBUTE_COST_PER_POINT * ELF_INTELLIGENCE_COST_MULTIPLIER,
            AttributesType.CHARISMA, DEFAULT_ATTRIBUTE_CHARISMA_COST_PER_POINT * ELF_CHARISMA_COST_MULTIPLIER,
            AttributesType.STAMINA, DEFAULT_ATTRIBUTE_COST_PER_POINT * ELF_STAMINA_COST_MULTIPLIER
    );
    /**
     * method applying racial bonuses
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applyRacialBonuses(Attributes attributes) {
        attributes.setIntelligence(attributes.getIntelligence() + ELF_INTELLIGENCE_BONUS);
        attributes.setAgility(attributes.getAgility() + ELF_AGILITY_BONUS);
        attributes.setDexterity(attributes.getDexterity() + ELF_DEXTERITY_BONUS);
        attributes.setCharisma(attributes.getCharisma() + ELF_CHARISMA_BONUS);
        attributes.setWillpower(attributes.getWillpower() + ELF_WILLPOWER_BONUS);
        attributes.setPreparedBound(4);
    }
    /**
     * method applying racial penalties
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applyRacialPenalties(Attributes attributes) {
        attributes.setStamina(attributes.getStamina() - ELF_STAMINA_PENALTY);
        attributes.setStrength(attributes.getStrength() - ELF_STRENGTH_PENALTY);
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
        return RaceType.ELF;
    }
}
