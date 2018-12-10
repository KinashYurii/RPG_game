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
public class ClothChestArmor implements ChestArmor {
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.SORCERER);
    private final ArmorType armorType = ArmorType.CLOTH;
    private final int level;
    private final String name;
    private final int willpower;
    private final int charisma;
    private final int intelligence;
    private final double armor;
    private final double magicResist;
    private final double mana;
    private BackpackPlace backpackPlace = BackpackPlace.PLACE2x2;

    public ClothChestArmor(int level) {
        this.level = level;
        this.name = "Cloth chest Armor " + level;
        this.willpower = level;
        this.charisma = level;
        this.intelligence = level;
        this.armor = level;
        this.magicResist = level;
        this.mana = level;
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.WILLPOWER, willpower);
        AttributesService.getInstance().applyAttribute(hero, AttributesType.INTELLIGENCE, intelligence);
        AttributesService.getInstance().applyAttribute(hero, AttributesType.CHARISMA, charisma);
        StatsService.getInstance().applyMagicResistance(hero, magicResist);
        StatsService.getInstance().applyArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyMana(hero, mana);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.WILLPOWER, willpower);
        AttributesService.getInstance().discardAttribute(hero, AttributesType.INTELLIGENCE, intelligence);
        AttributesService.getInstance().discardAttribute(hero, AttributesType.CHARISMA, charisma);
        StatsService.getInstance().discardMagicResistance(hero, magicResist);
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().discardMana(hero, mana);
    }

    @Override
    public void applyStats(Hero hero) {
    }

    @Override
    public String toString() {
        return name;
    }
}
