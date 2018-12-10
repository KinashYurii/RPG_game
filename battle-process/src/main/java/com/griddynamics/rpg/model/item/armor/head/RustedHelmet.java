package com.griddynamics.rpg.model.item.armor.head;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.armor.ArmorType;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.Getter;
import lombok.ToString;

import java.util.EnumSet;
import java.util.Set;

@ToString
public class RustedHelmet implements Head {
    private final int armor = 2;
    @Getter
    private final ArmorType armorType;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes;
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes;

    public RustedHelmet() {
        this.armorType = ArmorType.CHAIN;
        this.raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
        this.specializationTypes = EnumSet.of(SpecializationType.SWORDSMAN);
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        StatsService.getInstance().applyArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void applyStats(Hero hero) {
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE1x2;
    }
}
