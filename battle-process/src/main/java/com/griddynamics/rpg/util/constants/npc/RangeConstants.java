package com.griddynamics.rpg.util.constants.npc;

import com.griddynamics.rpg.model.hero.Stats;
import com.griddynamics.rpg.model.npc.NpcType;
import com.griddynamics.rpg.model.npc.Weapon;

public class RangeConstants implements NpcTypeConstants {

    private static final Stats DEFAULT_STATS_NPC = Stats.builder()
            .health(45)
            .mana(9)
            .rage(0)
            .healthRegen(0.5)
            .manaRegen(0.5)
            .rageRegen(0.1)
            .magicResistance(5)
            .armor(0)
            .build();

    private static final Weapon DEFAULT_WEAPON_NPC = Weapon.builder()
            .attackCount(1)
            .maxDamage(4)
            .range(3)
            .build();

    private static final Stats UPDATE_STATS_PER_LVL = Stats.builder()
            .health(10)
            .mana(0.8)
            .rage(0.2)
            .healthRegen(0.4)
            .manaRegen(0.3)
            .rageRegen(0.1)
            .magicResistance(0.2)
            .armor(1.25)
            .build();

    private static final Weapon UPDATE_WEAPON_NPC = Weapon.builder()
            .attackCount(0.1)
            .maxDamage(0.5)
            .range(0.1)
            .build();

    public NpcType getNpcType() {
        return NpcType.RANGE;
    }

    public Stats getDefaultStatsNpc() {
        return DEFAULT_STATS_NPC;
    }

    public Weapon getDefaultWeaponNpc() {
        return DEFAULT_WEAPON_NPC;
    }

    public Stats getUpdateStatsPerLvl() {
        return UPDATE_STATS_PER_LVL;
    }

    public Weapon getUpdateWeaponNpc() {
        return UPDATE_WEAPON_NPC;
    }
}
