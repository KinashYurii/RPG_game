package com.griddynamics.rpg.model.battle;

import com.griddynamics.rpg.model.arena.Battlefield;

import java.util.Set;

public interface Turn {
    TurnResult make(Attackable hero, Attackable npc, Battlefield battleField, Set<RoundEffect> roundEffects);
}
