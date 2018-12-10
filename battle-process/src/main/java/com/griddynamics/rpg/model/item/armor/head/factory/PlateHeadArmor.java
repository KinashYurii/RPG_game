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

import static com.griddynamics.rpg.model.backpack.BackpackPlace.PLACE4x2;

@Getter
public class PlateHeadArmor implements Head {
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.WARRIOR);
    private ArmorType armorType = ArmorType.PLATE;
    private final int level;
    private final String name;
    private final int strength;
    private final int stamina;
    private final double armor;
    private final double health;
    private BackpackPlace backpackPlace = PLACE4x2;

    public PlateHeadArmor(int level) {
        this.level = level;
        this.name = "Plate Head Armor " + level;
        this.strength = level;
        this.stamina = level;
        this.armor = level;
        this.health = level;
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STAMINA, stamina);
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STRENGTH, strength);
        StatsService.getInstance().applyArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyHealth(hero, health);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STAMINA, stamina);
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STRENGTH, strength);
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().discardHealth(hero, health);
    }

    @Override
    public void applyStats(Hero hero) {
        StatsService.getInstance().applyHealth(hero, health);
    }

    @Override
    public String toString() {
        return name;
    }
}
