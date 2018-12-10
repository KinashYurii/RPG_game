package com.griddynamics.rpg.model.battle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AttackResult {
    private double takenDamage;
    private double damagePerRound;
    private boolean isAlive;
    private String abilities;
    private Attack answerDamage;
}
