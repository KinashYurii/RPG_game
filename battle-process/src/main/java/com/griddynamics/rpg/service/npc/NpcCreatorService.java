package com.griddynamics.rpg.service.npc;

import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.Stats;
import com.griddynamics.rpg.model.npc.*;
import com.griddynamics.rpg.model.npc.race.NpcRace;
import com.griddynamics.rpg.util.constants.npc.NpcTypeConstants;

import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.*;
import static com.griddynamics.rpg.util.validators.NpcValidator.isGeneratedMinLevelIsZero;

public class NpcCreatorService {
    private final NpcTypeConstants npcTypeConstants;
    private final Stats defaultStatsNpc;
    private final Stats updateStatsPerLvl;

    public NpcCreatorService(NpcTypeConstants npcTypeConstants) {
        this.npcTypeConstants = npcTypeConstants;
        this.defaultStatsNpc = npcTypeConstants.getDefaultStatsNpc();
        this.updateStatsPerLvl = npcTypeConstants.getUpdateStatsPerLvl();
    }

    private static int getRandomInRange(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    private static double getRandomInRange(double min, double max) {
        return RANDOM.nextDouble() * (max - min) + min;
    }

    private double generateStats(double characterStat) {
        return getRandomInRange(characterStat * MIN_COEFFICIENT, characterStat * MAX_COEFFICIENT);
    }

    private NpcRace generateNpcRace() {
        int index = RANDOM.nextInt(NpcRaceType.values().length);
        return NPC_RACE.get(NpcRaceType.values()[index]);
    }

    private AbilitiesType generateAbilities() {
        int index = RANDOM.nextInt(AbilitiesType.values().length);
        return AbilitiesType.values()[index];
    }

    private int generateLevel(Hero hero) {
        int minLevel = isGeneratedMinLevelIsZero(hero) ? 1 : hero.getLevel().getCurrentLevel() - 1;
        int maxLevel = hero.getLevel().getCurrentLevel() + 3;
        return getRandomInRange(minLevel, maxLevel);
    }

    private double generateStat(double defaultStat, double updateStat, int level) {
        return generateStats(defaultStat + (level * updateStat));
    }

    private Weapon generateWeapon(int level) {
        Weapon weapon = new Weapon();
        Weapon defaultWeaponNpc = npcTypeConstants.getDefaultWeaponNpc();
        Weapon updateWeaponNpc = npcTypeConstants.getUpdateWeaponNpc();
        int damageTypeIndex = RANDOM.nextInt(DamageType.values().length);
        double attackCount = defaultWeaponNpc.getAttackCount() + (level * updateWeaponNpc.getAttackCount());
        weapon.setAttackCount(attackCount);
        double maxDamage = defaultWeaponNpc.getMaxDamage() + (level * updateWeaponNpc.getMaxDamage());
        weapon.setMaxDamage(maxDamage);
        DamageType value = DamageType.values()[damageTypeIndex];
        weapon.setDamageType(value);
        weapon.setRange(defaultWeaponNpc.getRange() + (level * updateWeaponNpc.getRange()));
        return weapon;
    }


    private double generateRgRegen(int level) {
        return generateStat(defaultStatsNpc.getRageRegen(),
                updateStatsPerLvl.getRageRegen(), level);
    }


    private double generateHp(int level) {
        return generateStat(defaultStatsNpc.getHealth(),
                updateStatsPerLvl.getHealth(), level);
    }


    private double generateMp(int level) {
        return generateStat(defaultStatsNpc.getMana(),
                updateStatsPerLvl.getMana(), level);
    }


    private double generateRg(int level) {
        return generateStat(defaultStatsNpc.getRage(),
                updateStatsPerLvl.getRage(), level);
    }


    private double generateHpRegen(int level) {
        return generateStat(defaultStatsNpc.getHealthRegen(),
                updateStatsPerLvl.getHealthRegen(), level);
    }


    private double generateMpRegen(int level) {
        return generateStat(defaultStatsNpc.getManaRegen(),
                updateStatsPerLvl.getManaRegen(), level);
    }


    private double generateArmor(int level) {
        return generateStat(defaultStatsNpc.getArmor(),
                updateStatsPerLvl.getArmor(), level);
    }


    private double generateMagicResist(int level) {
        return generateStat(defaultStatsNpc.getMagicResistance(),
                updateStatsPerLvl.getMagicResistance(), level);
    }


    private Stats generateStats(int level) {
        return Stats.builder()
                .health(generateHp(level))
                .healthRegen(generateHpRegen(level))
                .mana(generateMp(level))
                .manaRegen(generateMpRegen(level))
                .rage(generateRg(level))
                .rageRegen(generateRgRegen(level))
                .armor(generateArmor(level))
                .magicResistance(generateMagicResist(level))
                .build();
    }

    public Npc build(Hero hero) {
        Npc npc = new Npc();
        npc.getLevel().setCurrentLevel (generateLevel(hero));
        npc.setNpcType(npcTypeConstants.getNpcType());
        npc.setRace(generateNpcRace());
        npc.setAbilitiesType(generateAbilities());
        npc.setWeapon(generateWeapon(npc.getLevel().getCurrentLevel()));
        npc.setNpcStats(generateStats(npc.getLevel().getCurrentLevel()));
        npc.getNpcStats().setMaxHealth(npc.getNpcStats().getHealth());
        return npc;
    }
}
