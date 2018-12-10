package com.griddynamics.rpg.model.item.armor.factory;

import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.factory.ChainChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.factory.ChainHeadArmor;

public class ChainArmorFactory implements ArmorFactory {
    @Override
    public Head createHead(int level) {
        return new ChainHeadArmor(level);
    }

    @Override
    public ChestArmor createChestArmor(int level) {
        return new ChainChestArmor(level);
    }
}
