package com.griddynamics.rpg.model.item.weapon.archer;

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
public class ArcherRustedDagger implements Weapon {
    @Getter
    @Builder.Default
    private int damage = 6;
    @Getter
    private final WeaponType weaponType = WeaponType.ONE_HAND_WEAPON;
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.ARCHER);
    @Getter
    private int roll = 1;

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        int range = 0;
        return List.of(Attack.builder()
                .damage(calculateDamage())
                .damageType(DamageType.PHYSICAL)
                .name("Archer rusted dagger ")
                .roundAttack(null)
                .range(range)
                .build()
        );
    }

    @Override
    public void reset() {
    }

    @Override
    public int calculateDamage() {
        return DiceService.getInstance().roll(this.roll, this.damage);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE2x1;
    }

    @Override
    public String toString() {
        return "Archer rusted dagger";
    }
}
