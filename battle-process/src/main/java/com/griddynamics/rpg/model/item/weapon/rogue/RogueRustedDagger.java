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
import java.util.Random;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RogueRustedDagger implements Weapon {
    @Getter
    private int damage = 6;
    @Getter
    private final WeaponType weaponType = WeaponType.ONE_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.ROGUE);
    @Getter
    private int roll = 1;

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        int range = 0;
        int resultDamage = calculateDamage();
        resultDamage = prepared ? calculateDamage() * 3 : resultDamage;
        if (prepared) {
            System.out.println("Prepared");
        }
        return List.of(Attack.builder()
                .damage(resultDamage)
                .damageType(DamageType.PHYSICAL)
                .name("Rogue rusted dagger ")
                .roundAttack(null)
                .range(range)
                .build()
        );
    }

    @Override
    public void reset() {
    }

    @Override
    public int preparedDamage() {
        Random random = new Random();
        int result = 0;
        while (roll-- > 0) {
            result += random.nextInt(damage) + 1;
        }
        int multiplier = 3;
        result *= multiplier;
        return result;
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
        return "Rogue rusted dagger";
    }
}

