package com.griddynamics.rpg.model.item.armor.factory;

import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;

public interface ArmorFactory {
    Head createHead(int level);

    ChestArmor createChestArmor(int level);
}
