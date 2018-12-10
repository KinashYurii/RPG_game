package com.griddynamics.rpg.model.item.armor.chest;

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
public class RustedChainMail implements ChestArmor {
    private final int armor = 10;
    private final int agility = -1;
    private final double chance = 0.2;
    private final int damage = 10;
    @ToString.Exclude
    @Getter
    private final ArmorType armorType;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes;
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes;

    public RustedChainMail() {
        this.armorType = ArmorType.CHAIN;
        this.raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
        this.specializationTypes = EnumSet.of(SpecializationType.SWORDSMAN);
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.AGILITY, agility);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyArmor(hero, armor);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.AGILITY, agility);
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public double getArmorBonus() {
        return Math.random() < chance ? damage : 0;
    }

    @Override
    public void applyStats(Hero hero) {
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE2x2;
    }
}
