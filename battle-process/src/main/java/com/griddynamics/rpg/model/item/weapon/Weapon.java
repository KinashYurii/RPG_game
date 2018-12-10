package com.griddynamics.rpg.model.item.weapon;

import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;

import java.util.List;
import java.util.Set;

public interface Weapon extends BackpackPlacable {
    List<Attack> getAttack(Boolean prepared);

    /**
     * adding attributes or stats from weapon to hero
     *  @param hero         hero
     *
     */
    default void applyItemBonuses(Hero hero) {
    }

    /**
     * taking away attributes from hero, defined in weapon
     *
     * @param hero hero
     */
    default void discardItemBonuses(Hero hero) {
    }

    WeaponType getWeaponType();

    void reset();

    Set<RaceType> getRaceTypes();

    Set<SpecializationType> getSpecializationTypes();

    default int multiply() {
        return 1;
    }

    default int additionalDamage() {
        return 0;
    }

    /**
     * calculating a weapon bonus
     *
     * @return bonus
     */
    default double getWeaponBonus() {
        return 0;
    }

    /**
     * getting prepared damage
     *
     * @return Prepared damage
     */
    default int preparedDamage() {
        return 0;
    }

    /**
     * calculating damage
     *
     * @return damage
     */
    default int calculateDamage() {
        return 0;
    }

}
