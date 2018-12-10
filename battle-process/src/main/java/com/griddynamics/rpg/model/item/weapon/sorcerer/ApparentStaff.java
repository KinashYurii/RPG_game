package com.griddynamics.rpg.model.item.weapon.sorcerer;

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
public class ApparentStaff implements Weapon {
    private int intelligence = 1;
    private int damage = 4;
    @Getter
    private final WeaponType weaponType = WeaponType.TWO_HAND_WEAPON;
    @ToString.Exclude
    @Getter
    private final Set<RaceType> raceTypes = EnumSet.of(RaceType.HUMAN, RaceType.ORC, RaceType.ELF, RaceType.DWARF);
    @ToString.Exclude
    @Getter
    private final Set<SpecializationType> specializationTypes = EnumSet.of(SpecializationType.SORCERER);
    private int roll = 1;
    private int ammo = 2;
    private int currentAmmo = ammo;
    @ToString.Exclude
    private int heroIntelligenceTemp;

    @Override
    public List<Attack> getAttack(Boolean prepared) {
        List<Attack> attacks = new ArrayList<>();
        int range = 0;
        attacks.add(Attack.builder()
                .damage(calculateDamage())
                .damageType(DamageType.PHYSICAL)
                .name("Apparent Staff ")
                .roundAttack(null)
                .range(range)
                .build());
        if (currentAmmo >= 0) {
            currentAmmo--;
            int resultDamage = calculateMagicDamage();
            resultDamage = prepared ? resultDamage * 2 : resultDamage;
            if (prepared) {
                System.out.println("Prepared");
            }
            attacks.add(Attack.builder()
                    .damageType(DamageType.MAGIC)
                    .damage(resultDamage)
                    .range(3)
                    .name("Fireball")
                    .roundAttack(null)
                    .build());
        }
        return attacks;
    }

    @Override
    public void applyItemBonuses(Hero hero) {
        AttributesService.getInstance().applyAttribute(hero, AttributesType.INTELLIGENCE, intelligence);
        StatsService.getInstance().calculateAllStats(hero);
        heroIntelligenceTemp = hero.getAttributes().getIntelligence();
    }

    @Override
    public void discardItemBonuses(Hero hero) {
        AttributesService.getInstance().discardAttribute(hero, AttributesType.INTELLIGENCE, intelligence);
        StatsService.getInstance().calculateAllStats(hero);
    }

    @Override
    public void reset() {
        currentAmmo = ammo;
    }

    private int calculateMagicDamage() {
        Random random = new Random();
        int result = 0;
        int newMagicRoll = 2;
        while (newMagicRoll-- > 0) {
            int magicDamage = 6;
            result += random.nextInt(magicDamage) + 1;
            result += heroIntelligenceTemp / 10;
        }
        return result;
    }

    @Override
    public int preparedDamage() {
        Random random = new Random();
        int result = 0;
        while (roll-- > 0) {
            result += random.nextInt(damage) + 1;
            result *= 2;
        }
        return result;
    }

    @Override
    public int calculateDamage() {
        return DiceService.getInstance().roll(this.roll, this.damage);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE4x1;
    }

    @Override
    public String toString() {
        return "Staff";
    }
}

