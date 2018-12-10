package com.griddynamics.rpg.model.item.weapon.warrior;

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
public class Whip implements Weapon {
    @Getter
    private final WeaponType weaponType = WeaponType.TWO_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.ORC);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.WARRIOR);
    double chance = 0.15;
    int damage = 12;
    int roll = 1;

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        int range = 1;
        return List.of(Attack.builder()
                .damage(calculateDamage())
                .damageType(DamageType.PHYSICAL)
                .name("Whip ")
                .roundAttack(null)
                .range(range)
                .build()
        );
    }

    @Override
    public void reset() {
    }

    @Override
    public int multiply() {
        return Math.random() < chance ? 2 : 1;
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
        return "Whip";
    }
}
