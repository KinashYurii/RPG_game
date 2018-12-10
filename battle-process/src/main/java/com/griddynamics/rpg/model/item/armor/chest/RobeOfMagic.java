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
public class RobeOfMagic implements ChestArmor {
    private final int willpower = 2;
    private final int dexterity = 1;
    @ToString.Exclude
    @Getter
    private final ArmorType armorType;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes;
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes;

    public RobeOfMagic() {
        this.armorType = ArmorType.CLOTH;
        this.raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
        this.specializationTypes = EnumSet.of(SpecializationType.SORCERER);
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.WILLPOWER, willpower);
        AttributesService.getInstance().applyAttribute(hero, AttributesType.DEXTERITY, dexterity);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.WILLPOWER, willpower);
        AttributesService.getInstance().discardAttribute(hero, AttributesType.DEXTERITY, dexterity);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void applyStats(Hero hero) {
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE2x2;
    }
}
