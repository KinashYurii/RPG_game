package com.griddynamics.rpg.model.item.armor.factory;

import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.factory.LeatherChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.factory.LeatherHeadArmor;

public class LeatherArmorFactory implements ArmorFactory {
    @Override
    public Head createHead(int level) {
        return new LeatherHeadArmor(level);
    }

    @Override
    public ChestArmor createChestArmor(int level) {
        return new LeatherChestArmor(level);
    }
}
