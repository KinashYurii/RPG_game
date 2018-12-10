package com.griddynamics.rpg.model.item.weapon.rogue;

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
public class RogueRustedKnife implements Weapon {
    double poisonDamage = 2;
    @Getter
    private int damage = 4;
    @Getter
    private final WeaponType weaponType = WeaponType.ONE_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.ROGUE);
    @Getter
    private final int roll = 1;

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        int range = 0;
        int resultDamage = calculateDamage();
        resultDamage = prepared ? resultDamage * 3 : calculateDamage();
        if (prepared) {
            System.out.println("Prepared");
        }
        return List.of(Attack.builder()
                .damage(resultDamage)
                .damageType(DamageType.PHYSICAL)
                .name("Rogue rusted knife ")
                .roundAttack(Attack.builder()
                        .damage(poisonDamage)
                        .damageType(DamageType.POISON)
                        .build())
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
        return "Rogue rusted knife";
    }
}

