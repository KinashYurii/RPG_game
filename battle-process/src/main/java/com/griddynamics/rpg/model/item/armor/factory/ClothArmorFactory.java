package com.griddynamics.rpg.model.item.armor.factory;

import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.factory.ClothChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.factory.ClothHeadArmor;

public class ClothArmorFactory implements ArmorFactory {
    @Override
    public Head createHead(int level) {
        return new ClothHeadArmor(level);
    }

    @Override
    public ChestArmor createChestArmor(int level) {
        return new ClothChestArmor(level);
    }
}
