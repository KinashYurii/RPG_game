package com.griddynamics.rpg.model.battle;

import java.util.Set;

public interface Attackable {

    void resetAmmo();

    boolean alive();

    boolean prepared();

    String getName();

    AttackResult takeAttack(Attack attack, BattleAttributes battleAttributesWhoHit);

    Set<Attack> possibleAttacks();

    BattleAttributes getBattleAttributes();
}
