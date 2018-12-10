package com.griddynamics.rpg.util.constants.npc;

import com.griddynamics.rpg.model.hero.Stats;
import com.griddynamics.rpg.model.npc.NpcType;
import com.griddynamics.rpg.model.npc.Weapon;

public class MeleeConstants implements NpcTypeConstants {

    private static final Stats DEFAULT_STATS_NPC = Stats.builder()
            .health(94)
            .mana(8)
            .rage(2)
            .healthRegen(5)
            .manaRegen(0.4)
            .rageRegen(0.9)
            .magicResistance(5)
            .armor(0)
            .build();

    private static final Weapon DEFAULT_WEAPON_NPC = Weapon.builder()
            .attackCount(1)
            .maxDamage(6)
            .range(0)
            .build();

    private static final Stats UPDATE_STATS_PER_LVL = Stats.builder()
            .health(30)
            .mana(0.5)
            .rage(0.5)
            .healthRegen(0.3)
            .manaRegen(0.2)
            .rageRegen(0.4)
            .magicResistance(0.2)
            .armor(1)
            .build();

    private static final Weapon UPDATE_WEAPON_NPC = Weapon.builder()
            .attackCount(0.1)
            .maxDamage(0.2)
            .range(0.1)
            .build();

    public NpcType getNpcType() {
        return NpcType.MELEE;
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
