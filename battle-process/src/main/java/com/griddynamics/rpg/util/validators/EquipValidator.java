package com.griddynamics.rpg.util.validators;

import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class EquipValidator {

    public static boolean isEquippableWeapon(final Hero hero, final Weapon weapon) {
        return hero.getItemSlots().getWeaponType().equals(weapon.getWeaponType())
                && weapon.getRaceTypes().contains(hero.getRace().getRaceType())
                && weapon.getSpecializationTypes().contains(hero.getSpecialization().getSpecializationType());
    }

    public static boolean isEquippableArmor(final Hero hero, final Head head) {
        return hero.getItemSlots().getArmorType().equals(head.getArmorType())
                && head.getRaceTypes().contains(hero.getRace().getRaceType())
                && head.getSpecializationTypes().contains(hero.getSpecialization().getSpecializationType());
    }

    public static boolean isEquippableArmor(final Hero hero, final ChestArmor chestArmor) {
        return hero.getItemSlots().getArmorType().equals(chestArmor.getArmorType())
                && chestArmor.getRaceTypes().contains(hero.getRace().getRaceType())
                && chestArmor.getSpecializationTypes().contains(hero.getSpecialization().getSpecializationType());
    }

    public static boolean isHeroOneHandWeapon(final Hero hero) {
        return hero.getItemSlots().getWeaponType().equals(WeaponType.ONE_HAND_WEAPON);
    }

    public static boolean isHeroHandsFree(final Hero hero) {
        return hero.getItemSlots().getLeftHand() == null && hero.getItemSlots().getRightHand() == null;
    }

    public static boolean isHeroLeftHandEmpty(final Hero hero) {
        return hero.getItemSlots().getLeftHand() == null;
    }

    public static boolean isHeroRightHandEmpty(final Hero hero) {
        return hero.getItemSlots().getRightHand() == null;
    }

    public static boolean isEquippableAccessory(final Hero hero) {
        return hero.getSpecialization().getSpecializationType() == SpecializationType.SORCERER
                && hero.getItemSlots().getAccessory1() == null
                || hero.getItemSlots().getAccessory2() == null;
    }
}

