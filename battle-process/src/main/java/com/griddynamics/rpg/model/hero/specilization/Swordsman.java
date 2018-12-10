package com.griddynamics.rpg.model.hero.specilization;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.ItemSlots;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.RustedChainMail;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.RustedHelmet;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.item.weapon.swordsman.RustedShield;
import com.griddynamics.rpg.model.item.weapon.swordsman.RustedSword;
import com.griddynamics.rpg.service.hero.HeroService;

import static com.griddynamics.rpg.util.constants.SpecializationConstants.*;

public class Swordsman implements Specialization {
    /**
     * method applies specialization bonuses
     */
    @Override
    public void applySpecializationBonuses(Attributes attributes, ItemSlots itemSlots) {
        attributes.setStamina(attributes.getStamina() + SWORDSMAN_STAMINA_BONUS);
        itemSlots.setArmorType(ArmorType.CHAIN);
        itemSlots.setWeaponType(WeaponType.ONE_HAND_WEAPON);
    }
    /**
     * method applies specialization penalties
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applySpecializationPenalties(Attributes attributes) {
        attributes.setAgility(attributes.getAgility() - SWORDSMAN_AGILITY_PENALTY);
        attributes.setDexterity(attributes.getDexterity() - SWORDSMAN_DEXTERITY_PENALTY);
    }

    @Override
    public SpecializationType getSpecializationType() {
        return SpecializationType.SWORDSMAN;
    }
    /**
     * equips hero at the start of the game
     *  @param hero hero
     *
     */
    @Override
    public void starterPack(Hero hero) {
        ChestArmor rustedChainMail = new RustedChainMail();
        Head rustedHelmet = new RustedHelmet();
        Weapon rustedSword = new RustedSword();
        Weapon rustedShield = new RustedShield();
        HeroService.getInstance().equip(hero, rustedChainMail);
        HeroService.getInstance().equip(hero, rustedHelmet);
        HeroService.getInstance().equip(hero, rustedShield);
        HeroService.getInstance().equip(hero, rustedSword);
    }
}
