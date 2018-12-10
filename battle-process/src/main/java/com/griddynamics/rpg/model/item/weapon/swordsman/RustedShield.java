package com.griddynamics.rpg.model.item.weapon.swordsman;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.npc.DamageType;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RustedShield implements Weapon {
    private int armor = 20;
    private double health = 30;
    @Getter
    private final WeaponType weaponType = WeaponType.ONE_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.SWORDSMAN);

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        return List.of(Attack.builder()
                .damage(0)
                .damageType(DamageType.PHYSICAL)
                .name("Shield ")
                .roundAttack(null)
                .range(-1)
                .build()
        );
    }

    public void applyItemBonuses(Hero hero) {
        StatsService.getInstance().applyArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().applyHealth(hero, health);

    }

    @Override
    public void discardItemBonuses(Hero hero) {
        StatsService.getInstance().discardArmor(hero, armor);
        StatsService.getInstance().calculateAllStats(hero);
        StatsService.getInstance().discardHealth(hero, health);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE3x2;
    }

    @Override
    public void reset() {
    }

    @Override
    public String toString() {
        return "Rusted shield";
    }
}
