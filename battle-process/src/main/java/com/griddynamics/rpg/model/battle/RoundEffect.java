package com.griddynamics.rpg.model.battle;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RoundEffect {
    private Attackable target;
    private Attack attack;

    public AttackResult apply() {
        return target.takeAttack(attack, target.getBattleAttributes());
    }
}
