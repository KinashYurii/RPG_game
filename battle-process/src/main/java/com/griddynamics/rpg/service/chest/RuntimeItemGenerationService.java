package com.griddynamics.rpg.service.chest;

import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.chest.Chest;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.item.weapon.archer.ArcherRustedDagger;
import com.griddynamics.rpg.model.item.weapon.archer.WoodenBow;
import com.griddynamics.rpg.model.item.weapon.rogue.RogueRustedDagger;
import com.griddynamics.rpg.model.item.weapon.rogue.RogueRustedKnife;
import com.griddynamics.rpg.model.item.weapon.sorcerer.ApparentStaff;
import com.griddynamics.rpg.model.item.weapon.swordsman.RustedShield;
import com.griddynamics.rpg.model.item.weapon.swordsman.RustedSword;
import com.griddynamics.rpg.model.item.weapon.warrior.RustedAxe;
import com.griddynamics.rpg.model.item.weapon.warrior.Whip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.griddynamics.rpg.util.constants.chest.RuntimeItemGeneration.*;

public class RuntimeItemGenerationService {

    private static RuntimeItemGenerationService runtimeItemGenerationService;

    public static RuntimeItemGenerationService getService() {
        if (runtimeItemGenerationService == null) {
            runtimeItemGenerationService = new RuntimeItemGenerationService();
        }
        return runtimeItemGenerationService;
    }

    private List<BackpackPlacable> generateRogueWeaponItem(int level) {
        RogueRustedDagger dagger = RogueRustedDagger.builder()
                .damage(DEFAULT_ROGUE_DAGGER_DAMAGE + ((int) DAMAGE_COEFFICIENT * level))
                .roll(DEFAULT_ROLL + ((int) ROLL_COEFFICIENT * level))
                .build();
        RogueRustedKnife knife = RogueRustedKnife.builder()
                .poisonDamage(DEFAULT_POISON_DAMAGE + POISON_COEFFICIENT * level)
                .damage(DEFAULT_ROGUE_KNIFE_DAMAGE + ((int) DAMAGE_COEFFICIENT * level))
                .build();
        List<BackpackPlacable> result = new ArrayList<>();
        result.add(dagger);
        result.add(knife);
        return result;
    }

    private List<BackpackPlacable> generateWarriorWeaponItem(int level) {
        RustedAxe axe = RustedAxe
                .builder()
                .damage(DEFAULT_WARRIOR_DAMAGE + ((int) DAMAGE_COEFFICIENT * level))
                .chance(DEFAULT_WARRIOR_CRIT_CHANCE + ((double) level / CHANCE_DIVISION))
                .roll(DEFAULT_ROLL + ((int) ROLL_COEFFICIENT * level))
                .build();
        Whip whip = Whip.builder()
                .damage(DEFAULT_WARRIOR_DAMAGE + ((int) DAMAGE_COEFFICIENT * level))
                .chance(DEFAULT_WARRIOR_CRIT_CHANCE + ((double) level / CHANCE_DIVISION))
                .roll(DEFAULT_ROLL + ((int) ROLL_COEFFICIENT * level))
                .build();
        List<BackpackPlacable> result = new ArrayList<>();
        result.add(axe);
        result.add(whip);
        return result;
    }

    private List<BackpackPlacable> generateSorcererWeaponItem(int level) {
        ApparentStaff staff = ApparentStaff.builder()
                .ammo(DEFAULT_MAGIC_AMMO + ((int) AMMO_COEFFICIENT * level))
                .damage(DEFAULT_SORCERER_DAMAGE + ((int) DAMAGE_COEFFICIENT * level))
                .roll(DEFAULT_ROLL + ((int) ROLL_COEFFICIENT * level))
                .build();
        List<BackpackPlacable> result = new ArrayList<>();
        result.add(staff);
        return result;
    }

    private List<BackpackPlacable> generateArcherWeaponItem(int level) {
        ArcherRustedDagger dagger = ArcherRustedDagger.builder()
                .damage(DEFAULT_ARCHER_BOW_DAMAGE + ((int) DAMAGE_COEFFICIENT * level))
                .roll(DEFAULT_ROLL + ((int) ROLL_COEFFICIENT * level))
                .build();
        WoodenBow bow = WoodenBow.builder()
                .ammo(DEFAULT_ARCHER_AMMO + (int) (AMMO_COEFFICIENT * level))
                .damage(DEFAULT_ARCHER_BOW_DAMAGE + (int) (DAMAGE_COEFFICIENT * level))
                .roll(DEFAULT_ROLL + (int) (ROLL_COEFFICIENT * level))
                .range(DEFAULT_ARCHER_BOW_RANGE + level / RANGE_DIVISION)
                .build();
        List<BackpackPlacable> result = new ArrayList<>();
        result.add(dagger);
        result.add(bow);
        return result;
    }

    private List<BackpackPlacable> generateSwordsmanWeaponItem(int level) {
        RustedShield shield = RustedShield.builder()
                .armor(DEFAULT_SHIELD_ARMOR + (int) (level * SHIELD_ARMOR_MULTIPLIER))
                .health(DEFAULT_SHIELD_HEALTH + level * SHIELD_HEALTH_MULTIPLIER)
                .build();
        RustedSword sword = RustedSword.builder()
                .damage(DEFAULT_SWORDSMAN_DAMAGE + (int) (level * DAMAGE_COEFFICIENT))
                .roll(DEFAULT_ROLL + (int) (level * ROLL_COEFFICIENT))
                .chance(DEFAULT_SWORDSMAN_CRIT_CHANCE + (double) level / CHANCE_DIVISION)
                .build();
        List<BackpackPlacable> result = new ArrayList<>();
        result.add(shield);
        result.add(sword);
        return result;
    }

    Chest generateWeapon(Hero hero) {
        SpecializationType heroSpec = hero.getSpecialization().getSpecializationType();
        int heroLvl = hero.getLevel().getCurrentLevel();
        return weaponGeneration.get(heroSpec).apply(heroLvl);
    }

    private Map<SpecializationType, Function<Integer, Chest>> weaponGeneration = Map.of(
            SpecializationType.SWORDSMAN, x -> new Chest(generateSwordsmanWeaponItem(x)),
            SpecializationType.ARCHER, x -> new Chest(generateArcherWeaponItem(x)),
            SpecializationType.SORCERER, x -> new Chest(generateSorcererWeaponItem(x)),
            SpecializationType.WARRIOR, x -> new Chest(generateWarriorWeaponItem(x)),
            SpecializationType.ROGUE, x -> new Chest(generateRogueWeaponItem(x)));
}
