package com.griddynamics.rpg.model.item.armor.factory;

import com.griddynamics.rpg.model.backpack.BackpackPlacable;

import java.util.List;

import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.RANDOM;

public class FactoryService {
    public BackpackPlacable getRandomItem() {
        int randomLevel = RANDOM.nextInt(20) + 1;
        return randomArmor(randomLevel);
    }

    private BackpackPlacable randomArmor(int level) {
        int randomTypeOfArmor = RANDOM.nextInt(4);
        int randomTypeOfArmorType = RANDOM.nextInt(2);
        if (randomTypeOfArmorType == 0) {
            return armorList.get(randomTypeOfArmor).createChestArmor(level);
        } else {
            return armorList.get(randomTypeOfArmor).createHead(level);
        }
    }

    private final List<ArmorFactory> armorList = List.of(
            new ClothArmorFactory(),
            new LeatherArmorFactory(),
            new ChainArmorFactory(),
            new PlateArmorFactory()
    );
}
