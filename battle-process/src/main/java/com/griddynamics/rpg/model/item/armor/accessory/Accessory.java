package com.griddynamics.rpg.model.item.armor.accessory;

import com.griddynamics.rpg.model.hero.Hero;

public interface Accessory {

    void applyItemBonuses(Hero hero);

    void discardItemBonuses(Hero hero);
}

