package com.griddynamics.rpg.model.item.armor;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.service.hero.AttributesService;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ArmorBuilder implements ChestArmor, Head {
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.SWORDSMAN
            , SpecializationType.ARCHER, SpecializationType.SORCERER, SpecializationType.WARRIOR, SpecializationType.ROGUE);
    private ArmorType armorType;
    private String name;
    private ItemAttributes attributes;
    private ItemStats stats;
    private ArmorBonus armorBonus;
    private BackpackPlace backpackPlace;

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.WILLPOWER, attributes.getWillpower());
        AttributesService.getInstance().applyAttribute(hero, AttributesType.AGILITY, attributes.getAgility());
        AttributesService.getInstance().applyAttribute(hero, AttributesType.DEXTERITY, attributes.getDexterity());
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STAMINA, attributes.getStamina());
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STRENGTH, attributes.getStrength());
        AttributesService.getInstance().applyAttribute(hero, AttributesType.INTELLIGENCE, attributes.getIntelligence());
        AttributesService.getInstance().applyAttribute(hero, AttributesType.CHARISMA, attributes.getCharisma());
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyArmor(hero, stats.getArmor());
        StatsService.getInstance().applyHealth(hero, stats.getHealth());
        StatsService.getInstance().applyMana(hero, stats.getMana());
        StatsService.getInstance().applyRage(hero, stats.getRage());
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.WILLPOWER, attributes.getWillpower());
        AttributesService.getInstance().discardAttribute(hero, AttributesType.AGILITY, attributes.getAgility());
        AttributesService.getInstance().discardAttribute(hero, AttributesType.DEXTERITY, attributes.getDexterity());
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STAMINA, attributes.getStamina());
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STRENGTH, attributes.getStrength());
        AttributesService.getInstance().discardAttribute(hero, AttributesType.INTELLIGENCE, attributes.getIntelligence());
        AttributesService.getInstance().discardAttribute(hero, AttributesType.CHARISMA, attributes.getCharisma());
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().discardArmor(hero, stats.getArmor());
        StatsService.getInstance().discardHealth(hero, stats.getHealth());
        StatsService.getInstance().discardMana(hero, stats.getMana());
        StatsService.getInstance().discardRage(hero, stats.getRage());
    }

    @Override
    public double getArmorBonus() {
        return Math.random() < armorBonus.getChance() ? armorBonus.getDamage() : 0;
    }

    @Override
    public void applyStats(Hero hero) {
    }
}

