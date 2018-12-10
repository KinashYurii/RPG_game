package com.griddynamics.rpg.service.battle;

import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.battle.AttackResult;
import com.griddynamics.rpg.model.battle.TurnResult;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

class ResultService {

    void showTurnResult(TurnResult result) {
        System.out.println(INF);
        System.out.println(result.getMessage());
        showAttackResult(result);
        showReflectionResult(result);
        System.out.println(INF);
    }

    private void showAttackResult(TurnResult result) {
        Attack attack = result.getAttack();
        AttackResult attackResult = result.getAttackResult();
        if (attack != null && attackResult != null) {
            System.out.println(ATTACK_POWER + attack.getDamage());
            System.out.println(RECEIVED_ATTACK + attackResult.getTakenDamage());
            if (attackResult.getAbilities() != null) {
                System.out.println(attackResult.getAbilities());
            }
        }
    }

    private void showReflectionResult(TurnResult result) {
        Attack reflection = result.getReflection();
        AttackResult reflectionResult = result.getReflectionResult();
        if (reflection != null && reflection.getDamage() != 0) {
            System.out.println(REFLECTION);
            System.out.println(REFLECTION_POWER + reflection.getDamage());
            System.out.println(RECEIVED_REFLECTION + reflectionResult.getTakenDamage());
        }
    }
}
