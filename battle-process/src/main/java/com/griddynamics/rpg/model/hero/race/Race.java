package com.griddynamics.rpg.model.hero.race;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.AttributesType;

import java.util.Map;

/**
 * Interface describes Race features
 */

public interface Race {

    /**
     * method applying racial bonuses
     *
     * @param attributes hero's attributes
     */
    default void applyRacialBonuses(Attributes attributes){}
    /**
     * method applying racial penalties
     *
     * @param attributes hero's attributes
     */
    default void applyRacialPenalties(Attributes attributes){}
    /**
     * calculates cost for adding 1 point to attribute
     *
     * @param attribute attribute
     * @return cost
     */
    double getRaceAttributeCost(AttributesType attribute);

    RaceType getRaceType();

    Map<AttributesType, Double> getAttributesCostMap();
}