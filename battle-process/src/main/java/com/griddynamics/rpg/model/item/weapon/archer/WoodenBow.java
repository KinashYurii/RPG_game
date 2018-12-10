package com.griddynamics.rpg.model.item.weapon.archer;

import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.item.weapon.WeaponType;
import com.griddynamics.rpg.model.npc.DamageType;
import com.griddynamics.rpg.service.hero.AttributesService;
import com.griddynamics.rpg.service.hero.StatsService;
import com.griddynamics.rpg.service.roll.DiceService;
import lombok.*;

import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WoodenBow implements Weapon {
    private final int agility = 1;
    @Getter
    private int damage = 10;
    @Getter
    private final WeaponType weaponType = WeaponType.ONE_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.ARCHER);
    @Getter
    private int roll = 1;
    private int preparedRoll = 2;
    private int ammo = 5;
    private int currentAmmo = ammo;
    int range = 3;

    @Override
    public void reset() {
        currentAmmo = ammo;
    }

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        if (currentAmmo-- > 0) {

            int resultDamage = prepared ? DiceService.getInstance().roll(2, 12) : calculateDamage();
            if (prepared) {
                System.out.println("Prepared");
            }
            return List.of(Attack.builder()
                    .damage(resultDamage)
                    .damageType(DamageType.PHYSICAL)
                    .name("Wooden bow ")
                    .roundAttack(null)
                    .range(range)
                    .build()
            );
        }
        return new ArrayList<>();
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.AGILITY, agility);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.AGILITY, agility);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public int preparedDamage() {
        Random random = new Random();
        int result = 0;
        while (preparedRoll-- > 0) {
            int preparedDamage = 12;
            result += random.nextInt(preparedDamage) + 1;
        }
        int additionalPreparedDamage = 2;
        result += additionalPreparedDamage;
        return result;
    }

    @Override
    public int calculateDamage() {
        return DiceService.getInstance().roll(this.roll, this.damage);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE3x1;
    }

    @Override
    public String toString() {
        return "WoodenBow";
    }
}

