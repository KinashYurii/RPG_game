package com.griddynamics.rpg.model.item.armor.head;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.service.hero.AttributesService;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.Getter;
import lombok.ToString;

import java.util.EnumSet;
import java.util.Set;

@ToString
public class PlatedHelmet implements Head {
    private final int stamina = -1;
    private final int armor = 20;
    private final double health = 30;
    @Getter
    private final ArmorType armorType;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes;
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes;

    public PlatedHelmet() {
        this.armorType = ArmorType.PLATE;
        this.raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
        this.specializationTypes = EnumSet.of(SpecializationType.WARRIOR);
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.STAMINA, stamina);
        StatsService.getInstance().applyArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyHealth(hero, health);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.STAMINA, stamina);
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().discardHealth(hero, health);
    }

    @Override
    public void applyStats(Hero hero) {
        StatsService.getInstance().applyHealth(hero, health);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE1x2;
    }
}