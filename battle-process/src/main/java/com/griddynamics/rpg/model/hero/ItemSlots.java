package com.griddynamics.rpg.model.hero;

import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.accessory.Accessory;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ItemSlots {
    private ChestArmor chestArmor;
    private Head head;
    private Accessory accessory1;
    private Accessory accessory2;
    private Weapon leftHand;
    private Weapon rightHand;
    private WeaponType weaponType;
    private ArmorType armorType;

    private String choice() {
        String weapon;
        if (leftHand.equals(rightHand)) {
            weapon = "\n -Two hands: " + leftHand;
        } else {
            weapon = "\n -LeftHand: " + leftHand + "\n -RightHand: " + rightHand;
        }
        return weapon;
    }

    private String getIfExists(Object obj) {
        if (Objects.isNull(obj)) {
            return "empty";
        } else {
            return obj.toString();
        }
    }

    @Override
    public String toString() {
        return "ItemSlots: " +
                "\n -ChestArmor: " + chestArmor +
                "\n -Head: " + getIfExists(head) +
                "\n -Accessory1: " + getIfExists(accessory1) +
                "\n -Accessory2: " + getIfExists(accessory2) +
                choice() +
                "\n -Weapon type: " + weaponType +
                "\n -Armor type: " + armorType;
    }
}
