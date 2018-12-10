package com.griddynamics.rpg.model.item.weapon.swordsman;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.npc.DamageType;
import com.griddynamics.rpg.service.roll.DiceService;
import lombok.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RustedSword implements Weapon {
    @Getter
    private final WeaponType weaponType = WeaponType.ONE_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.SWORDSMAN);
    double chance = 0.2;
    int damage = 8;
    int roll = 1;

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        int range = 0;
        return List.of(Attack.builder()
                .damage(calculateDamage())
                .damageType(DamageType.PHYSICAL)
                .name("Rusted sword ")
                .roundAttack(null)
                .range(range)
                .build()
        );
    }

    @Override
    public void reset() {
    }

    @Override
    public int additionalDamage() {
        return Math.random() < chance ? 2 : 0;
    }

    @Override
    public int calculateDamage() {
        return DiceService.getInstance().roll(roll, damage);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE3x1;
    }

    @Override
    public String toString() {
        return "Rusted sword";
    }
}
