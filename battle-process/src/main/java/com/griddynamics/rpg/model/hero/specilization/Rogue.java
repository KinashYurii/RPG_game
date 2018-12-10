package com.griddynamics.rpg.model.hero.specilization;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.ItemSlots;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.chest.LeatherJacket;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.armor.head.Mask;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.item.weapon.rogue.RogueRustedDagger;
import com.griddynamics.rpg.model.item.weapon.rogue.RogueRustedKnife;
import com.griddynamics.rpg.service.hero.HeroService;

import static com.griddynamics.rpg.util.constants.SpecializationConstants.*;

public class Rogue implements Specialization {
    /**
     * method applies specialization bonuses
     *
     * @param attributes hero's attributes
     */
    @Override
    public void applySpecializationBonuses(Attributes attributes, ItemSlots itemSlots) {
        attributes.setDexterity(attributes.getDexterity() + ROGUE_DEXTERITY_BONUS);
        itemSlots.setArmorType(ArmorType.LEATHER);
        itemSlots.setWeaponType(WeaponType.ONE_HAND_WEAPON);
        if(attributes.getPreparedBound() == 0 ){
            attributes.setPreparedBound(5);
        }
    }
    /**
     * method applies specialization penalties
     */
    @Override
    public void applySpecializationPenalties(Attributes attributes) {
        attributes.setStrength(attributes.getStrength() - ROGUE_STRENGTH_PENALTY);
        attributes.setWillpower(attributes.getWillpower() - ROGUE_WILLPOWER_PENALTY);
    }

    @Override
    public SpecializationType getSpecializationType() {
        return SpecializationType.ROGUE;
    }
    /**
     * equips hero at the start of the game
     *  @param hero hero
     *
     */
    @Override
    public void starterPack(Hero hero) {
        Head mask = new Mask();
        ChestArmor leatherJacket = new LeatherJacket();
        Weapon rustedDagger = new RogueRustedDagger();
        Weapon rustedKnife = new RogueRustedKnife();
        HeroService.getInstance().equip(hero, rustedDagger);
        HeroService.getInstance().equip(hero, rustedKnife);
        HeroService.getInstance().equip(hero, leatherJacket);
        HeroService.getInstance().equip(hero, mask);
    }

}
