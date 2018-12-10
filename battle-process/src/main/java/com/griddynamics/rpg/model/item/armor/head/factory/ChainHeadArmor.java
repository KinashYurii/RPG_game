package com.griddynamics.rpg.model.item.armor.head.factory;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.service.hero.AttributesService;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

import static com.griddynamics.rpg.model.backpack.BackpackPlace.PLACE3x3;

@Getter
public class ChainHeadArmor implements Head {
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.SWORDSMAN);
    private final ArmorType armorType = ArmorType.CHAIN;
    private final int level;
    private final String name;
    private final int stamina;
    private final int strength;
    private final double health;
    private final BackpackPlace backpackPlace = PLACE3x3;

    public ChainHeadArmor(int level) {
        this.level = level;
        this.name = "Chain Head Armor " + level;
        this.stamina = level;
        this.strength = level;
        this.health = level * 4;
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STAMINA, stamina);
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STRENGTH, strength);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyHealth(hero, health);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STAMINA, stamina);
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STRENGTH, strength);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().discardHealth(hero, health);
    }

    @Override
    public void applyStats(Hero hero) {
        StatsService.getInstance().applyHealth(hero, health);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return backpackPlace;
    }

    @Override
    public String toString() {
        return name;
    }
}

