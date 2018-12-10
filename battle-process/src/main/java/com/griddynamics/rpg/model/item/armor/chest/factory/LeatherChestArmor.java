package com.griddynamics.rpg.model.item.armor.chest.factory;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.service.hero.AttributesService;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

@Getter
public class LeatherChestArmor implements ChestArmor {
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.ARCHER, SpecializationType.ROGUE);
    private final ArmorType armorType = ArmorType.LEATHER;
    private final int level;
    private final String name;
    private final int agility;
    private final int dexterity;
    private final double armor;
    private final double magicResist;
    private BackpackPlace backpackPlace = BackpackPlace.PLACE3x2;

    public LeatherChestArmor(int level) {
        this.level = level;
        this.name = "Leather chest Armor " + level;
        this.agility = level;
        this.dexterity = level;
        this.armor = level;
        this.magicResist = level;
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.AGILITY, agility);
        AttributesService.getInstance().applyAttribute(hero, AttributesType.DEXTERITY, dexterity);
        StatsService.getInstance().applyArmor(hero, armor);
        StatsService.getInstance().applyMagicResistance(hero, magicResist);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.AGILITY, agility);
        AttributesService.getInstance().discardAttribute(hero, AttributesType.DEXTERITY, dexterity);
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().discardMagicResistance(hero, magicResist);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void applyStats(Hero hero) {
    }

    @Override
    public String toString() {
        return name;
    }
}
