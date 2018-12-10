package com.griddynamics.rpg.model.hero.specilization;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.ItemSlots;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.RobeOfMagic;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.LeatherHat;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.item.weapon.sorcerer.ApparentStaff;
import com.griddynamics.rpg.service.hero.HeroService;

import static com.griddynamics.rpg.util.constants.SpecializationConstants.*;

public class Sorcerer implements Specialization {
    /**
     * method applies specialization bonuses
     */
    @Override
    public void applySpecializationBonuses(Attributes attributes, ItemSlots itemSlots) {
        attributes.setWillpower(attributes.getWillpower() + SORCERER_WILLPOWER_BONUS);
        attributes.setIntelligence(attributes.getIntelligence() + SORCERER_INTELLIGENCE_BONUS);
        itemSlots.setArmorType(ArmorType.CLOTH);
        itemSlots.setWeaponType(WeaponType.TWO_HAND_WEAPON);
    }
    /**
     * method applies specialization penalties
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applySpecializationPenalties(Attributes attributes) {
        attributes.setAgility(attributes.getAgility() - SORCERER_AGILITY_PENALTY);
    }

    @Override
    public SpecializationType getSpecializationType() {
        return SpecializationType.SORCERER;
    }
    /**
     * equips hero at the start of the game
     *  @param hero hero
     *
     */
    @Override
    public void starterPack(Hero hero) {
        ChestArmor robeOfMagic = new RobeOfMagic();
        Head leatherHat = new LeatherHat();
        Weapon apparentStaff = new ApparentStaff();
        HeroService.getInstance().equip(hero, apparentStaff);
        HeroService.getInstance().equip(hero, robeOfMagic);
        HeroService.getInstance().equip(hero, leatherHat);
    }
}
