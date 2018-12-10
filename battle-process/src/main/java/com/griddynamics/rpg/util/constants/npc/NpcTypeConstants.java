package com.griddynamics.rpg.util.constants.npc;

import com.griddynamics.rpg.model.hero.Stats;
import com.griddynamics.rpg.model.npc.AbilitiesType;
import com.griddynamics.rpg.model.npc.NpcRaceType;
import com.griddynamics.rpg.model.npc.NpcType;
import com.griddynamics.rpg.model.npc.Weapon;
import com.griddynamics.rpg.model.npc.race.NpcRace;
import com.griddynamics.rpg.model.npc.race.impl.*;

import java.util.Map;
import java.util.Random;

public interface NpcTypeConstants {
    int ARMOR_CHANGE_REDUCE_POINTS = 30;
    double ARMOR_REDUCE_MORE = 0.12;
    double ARMOR_REDUCE_LESS = 0.08;
    Double CRIT_CHANCE = 0.15;
    Double MISS_CHANCE = 0.15;
    Double REFLECTION_CHANCE = 0.1;
    Double COUNTERATTACK_CHANCE = 0.1;
    double EFFECTIVE_HEALTH_THRESHOLD = 0.75;
    Random RANDOM = new Random(System.currentTimeMillis());
    double MIN_COEFFICIENT = 1.0;
    double MAX_COEFFICIENT = 1.0;


    Map<NpcRaceType, NpcRace> NPC_RACE = Map.of(
            NpcRaceType.DEMON, new Demon(),
            NpcRaceType.DRAGON, new Dragon(),
            NpcRaceType.UNDEAD, new Undead(),
            NpcRaceType.ELEMENTAL, new Elemental(),
            NpcRaceType.TROLL, new Troll()
    );

    Map<AbilitiesType, Double> ABILITIES_CHANCE = Map.of(
            AbilitiesType.CRIT, CRIT_CHANCE,
            AbilitiesType.MISS, MISS_CHANCE,
            AbilitiesType.REFLECTION, REFLECTION_CHANCE,
            AbilitiesType.COUNTERATTACK, COUNTERATTACK_CHANCE
    );

    NpcType getNpcType();

    Stats getDefaultStatsNpc();

    Weapon getDefaultWeaponNpc();

    Stats getUpdateStatsPerLvl();

    Weapon getUpdateWeaponNpc();
}
