package com.griddynamics.rpg.model.hero.specilization;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.ItemSlots;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.PlatedArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.PlatedHelmet;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.item.weapon.warrior.RustedAxe;
import com.griddynamics.rpg.model.item.weapon.warrior.Whip;
import com.griddynamics.rpg.service.hero.HeroService;

import static com.griddynamics.rpg.util.constants.SpecializationConstants.*;

public class Warrior implements Specialization {
    /**
     * method applies specialization bonuses
     */
    @Override
    public void applySpecializationBonuses(Attributes attributes, ItemSlots itemSlots) {
        attributes.setDexterity(attributes.getDexterity() + WARRIOR_STRENGTH_BONUS);
        itemSlots.setArmorType(ArmorType.PLATE);
        itemSlots.setWeaponType(WeaponType.TWO_HAND_WEAPON);
    }
    /**
     * method applies specialization penalties
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applySpecializationPenalties(Attributes attributes) {
        attributes.setAgility(attributes.getAgility() - WARRIOR_AGILITY_PENALTY);
        attributes.setDexterity(attributes.getDexterity() - WARRIOR_DEXTERITY_PENALTY);
    }

    @Override
    public SpecializationType getSpecializationType() {
        return SpecializationType.WARRIOR;
    }
    /**
     * equips hero at the start of the game
     *  @param hero        hero
     *
     */
    @Override
    public void starterPack(Hero hero) {
        ChestArmor platedArmor = new PlatedArmor();
        Head platedHelmet = new PlatedHelmet();
        Weapon rustedAxe = new RustedAxe();
        Weapon whip = new Whip();
        HeroService.getInstance().equip(hero, platedHelmet);
        HeroService.getInstance().equip(hero, platedArmor);
        RaceType raceType = hero.getRace().getRaceType();
        if (raceType.equals(RaceType.ORC)) {
            HeroService.getInstance().equip(hero, whip);
        } else if (raceType.equals(RaceType.DWARF)) {
            HeroService.getInstance().equip(hero, rustedAxe);
        }
    }
}
