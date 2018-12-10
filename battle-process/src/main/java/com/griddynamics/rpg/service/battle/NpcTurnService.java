package com.griddynamics.rpg.service.battle;

import com.griddynamics.rpg.model.arena.Battlefield;
import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.battle.AttackResult;
import com.griddynamics.rpg.model.battle.Attackable;
import com.griddynamics.rpg.model.battle.TurnResult;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.NPC_HIT_PATTERN;
import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.RANDOM;

class NpcTurnService {

    TurnResult turn(Attackable hero, Attackable npc, Battlefield battleField) {
        if (npc.alive()) {
            Set<Attack> attacks = getAccessAttack(npc, battleField.detDistanceBetweenHeroAndNpc());
            if (attacks.isEmpty()) {
                return move(battleField);
            } else {
                return hit(npc, hero, attacks);
            }
        } else {
            hero.resetAmmo();
            return TurnResult.NPC_DIED;
        }
    }

    private TurnResult move(Battlefield battleField) {
        if (battleField.npcCanWalkForward()) {
            return moveForward(battleField);
        }
        if (battleField.npcCanWalkBack()) {
            return moveBack(battleField);
        }
        return hold();
    }

    private TurnResult moveForward(Battlefield battleField) {
        battleField.npcWalkForward();
        return TurnResult.NPC_MOVE_FORWARD;
    }

    private TurnResult moveBack(Battlefield battleField) {
        battleField.npcWalkBack();
        return TurnResult.NPC_MOVE_BACK;
    }

    private TurnResult hold() {
        return TurnResult.NPC_HOLD;
    }

    private TurnResult hit(Attackable npc, Attackable hero, Set<Attack> attacks) {
        int index = RANDOM.nextInt(attacks.size());
        Attack attack = new ArrayList<>(attacks).get(index);
        AttackResult attackResult = hero.takeAttack(attack, hero.getBattleAttributes());
        return TurnResult.builder()
                .attack(attack)
                .attackResult(attackResult)
                .message(String.format(NPC_HIT_PATTERN, npc.getName(), hero.getName(), attack.getName()))
                .npcHit(false)
                .build();
    }

    private Set<Attack> getAccessAttack(Attackable npc, int distanceBetweenHeroAndNpc) {

        Set<Attack> attacks = npc.possibleAttacks();

        return distanceBetweenHeroAndNpc < 1 ?
                getMeleeAttacks(attacks) :
                getRangeAttacks(attacks, distanceBetweenHeroAndNpc);
    }

    private Set<Attack> getRangeAttacks(Set<Attack> attacks, int distanceBetweenHeroAndNpc) {
        return attacks.stream()
                .filter(attack -> attack.getRange() >= distanceBetweenHeroAndNpc)
                .collect(Collectors.toSet());
    }

    private Set<Attack> getMeleeAttacks(Set<Attack> attacks) {
        return attacks.stream()
                .filter(attack -> attack.getRange() < 1)
                .collect(Collectors.toSet());
    }
}
