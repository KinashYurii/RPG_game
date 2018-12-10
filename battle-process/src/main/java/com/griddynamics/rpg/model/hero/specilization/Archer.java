package com.griddynamics.rpg.model.hero.specilization;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.ItemSlots;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.LeatherJacket;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.item.weapon.archer.ArcherRustedDagger;
import com.griddynamics.rpg.model.item.weapon.archer.WoodenBow;
import com.griddynamics.rpg.service.hero.HeroService;

import static com.griddynamics.rpg.util.constants.SpecializationConstants.ARCHER_AGILITY_PENALTY;
import static com.griddynamics.rpg.util.constants.SpecializationConstants.ARCHER_DEXTERITY_BONUS;

public class Archer implements Specialization {
    /**
     * method applies specialization bonuses
     */
    @Override
    public void applySpecializationBonuses(Attributes attributes, ItemSlots itemSlots) {
        attributes.setDexterity(attributes.getDexterity() + ARCHER_DEXTERITY_BONUS);
        itemSlots.setArmorType(ArmorType.LEATHER);
        itemSlots.setWeaponType(WeaponType.ONE_HAND_WEAPON);
    }
    /**
     * method applies specialization penalties
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applySpecializationPenalties(Attributes attributes) {
        attributes.setAgility(attributes.getAgility() - ARCHER_AGILITY_PENALTY);
    }

    @Override
    public SpecializationType getSpecializationType() {
        return SpecializationType.ARCHER;
    }
    /**
     * equips hero at the start of the game
     *  @param hero hero
     *
     */
    @Override
    public void starterPack(Hero hero) {
        ChestArmor leatherJacket = new LeatherJacket();
        Weapon woodenBow = new WoodenBow();
        Weapon rustedDagger = new ArcherRustedDagger();
        HeroService.getInstance().equip(hero, woodenBow);
        HeroService.getInstance().equip(hero, rustedDagger);
        HeroService.getInstance().equip(hero, leatherJacket);
    }
}

