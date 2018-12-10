package com.griddynamics.rpg.model.hero.specilization;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.ItemSlots;

/**
 * Interface describes Race features
 */

public interface Specialization {
    /**
     * method applies specialization bonuses
     *

     */
    void applySpecializationBonuses(Attributes attributes, ItemSlots itemSlots);
    /**
     * method applies specialization penalties
     *
     * @param attributes hero's attributes
     */
    void applySpecializationPenalties(Attributes attributes);

    SpecializationType getSpecializationType();
    /**
     * equips hero at the start of the game
     * @param hero hero
     *
     */
    void starterPack(Hero hero);

}

