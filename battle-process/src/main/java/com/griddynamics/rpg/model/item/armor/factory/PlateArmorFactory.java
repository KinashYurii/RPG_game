package com.griddynamics.rpg.model.item.armor.factory;

import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.factory.PlateChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.factory.PlateHeadArmor;

public class PlateArmorFactory implements ArmorFactory {
    @Override
    public Head createHead(int level) {
        return new PlateHeadArmor(level);
    }

    @Override
    public ChestArmor createChestArmor(int level) {
        return new PlateChestArmor(level);
    }
}
