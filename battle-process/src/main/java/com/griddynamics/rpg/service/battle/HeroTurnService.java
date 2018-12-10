package com.griddynamics.rpg.service.battle;

import com.griddynamics.rpg.model.arena.Battlefield;
import com.griddynamics.rpg.model.battle.*;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.util.ConsoleReader;
import com.griddynamics.rpg.util.ConsoleWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

class HeroTurnService {
    private final ConsoleReader consoleReader = new ConsoleReader();
    private final ConsoleWriter consoleWriter = new ConsoleWriter();

    TurnResult turn(Attackable hero, Attackable npc, Battlefield battleField, Set<RoundEffect> roundEffects) {
        if (hero.alive()) {
            HeroTurn heroTurn = chooseTurn(hero, battleField);
            return heroTurn.getTurn().make(hero, npc, battleField, roundEffects);
        } else {
            return TurnResult.HERO_DIED;
        }
    }

    private HeroTurn chooseTurn(Attackable hero, Battlefield battleField) {
        Map<Integer, HeroTurn> turns = getPossibleTurns(hero, battleField);
        Map<Integer, String> toShow = turns.keySet()
                .stream()
                .collect(Collectors.toMap(i -> i, i -> turns.get(i).getName()));
        consoleWriter.writeMap(toShow);
        int turnIndex = consoleReader.readNumberFrom(turns.keySet());
        return turns.get(turnIndex);
    }

    private Map<Integer, HeroTurn> getPossibleTurns(Attackable hero, Battlefield battleField) {
        List<HeroTurn> turns = new ArrayList<>();

        generatePossibleTurns(hero, battleField, turns);

        return IntStream
                .range(0, turns.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, turns::get));
    }

    private void generatePossibleTurns(Attackable hero, Battlefield battleField, List<HeroTurn> turns) {
        addHold(turns);
        if (battleField.heroCanWalkForward()) {
            addMoveForward(turns);
        }
        if (battleField.heroCanWalkBack()) {
            addMoveBack(turns);
        }
        if (hero.prepared()) {
            addRunMove(turns);
        }
        addPossibleAttacks(hero, battleField, turns);
    }

    private void addRunMove(List<HeroTurn> turns) {
        turns.add(new HeroTurn(RUN, (hero, npc, battlefield, roundEffects) -> TurnResult.HERO_RUN));
    }

    private void addHold(List<HeroTurn> turns) {
        turns.add(new HeroTurn(HOLD, (hero, npc, battlefield, roundEffects) -> TurnResult.HERO_HOLD));
    }

    private void addPossibleAttacks(Attackable hero, Battlefield battleField, List<HeroTurn> turns) {
        int lengthBetweenOpponents = battleField.getLengthBetweenNpcAndHero();
        Set<Attack> attacks = hero.possibleAttacks();
        List<Attack> availableAttacks;
        if (lengthBetweenOpponents == 0)
            availableAttacks = getAvailableMeleeAttacks(attacks);
        else {
            availableAttacks = getAvailableRangeAttacks(lengthBetweenOpponents, attacks);
        }
        if (hero.prepared() && !availableAttacks.isEmpty()) {
            ((Hero) hero).setPrepared(false);
        }
        addAttacks(turns, availableAttacks);
    }

    private List<Attack> getAvailableMeleeAttacks(Set<Attack> attacks) {
        return attacks.stream()
                .filter(attack -> attack.getRange() <= 1 && attack.getRange() >= 0)
                .collect(Collectors.toList());
    }

    private List<Attack> getAvailableRangeAttacks(int lengthBetweenOpponents, Set<Attack> attacks) {
        return attacks.stream()
                .filter(attack -> attack.getRange() >= lengthBetweenOpponents)
                .collect(Collectors.toList());
    }

    private void addAttacks(List<HeroTurn> turns, List<Attack> attacks) {
        attacks.forEach(attack -> turns.add(new HeroTurn(attack.getName(), createAttackTurn(attack))));
    }

    private Turn createAttackTurn(Attack attack) {
        return (hero, npc, battlefield, roundEffects) -> {
            AttackResult attackResult = npc.takeAttack(attack, hero.getBattleAttributes());
            Attack reflection = attackResult.getAnswerDamage();
            AttackResult reflectionResult = getReflectionResult(hero, npc, reflection);

            addRoundAttackIfPresent(attack, npc, roundEffects);

            boolean hitNpc = attackResult.getTakenDamage() > 0;

            return TurnResult.builder()
                    .message(String.format(HERO_ATTACK_FORMAT, hero.getName(), attack.getName(), npc.getName()))
                    .attack(attack)
                    .attackResult(attackResult)
                    .npcHit(hitNpc)
                    .reflection(reflection)
                    .reflectionResult(reflectionResult)
                    .build();
        };
    }

    private void addRoundAttackIfPresent(Attack attack, Attackable npc, Set<RoundEffect> roundEffects) {
        if (attack.getRoundAttack() != null) {
            RoundEffect roundEffect = new RoundEffect(npc, attack.getRoundAttack());
            roundEffects.add(roundEffect);
        }
    }

    private AttackResult getReflectionResult(Attackable hero, Attackable npc, Attack reflection) {
        if (reflection != null) {
            return hero.takeAttack(reflection, npc.getBattleAttributes());
        }
        return null;
    }

    private void addMoveBack(List<HeroTurn> turns) {
        turns.add(new HeroTurn(BACK, (hero, npc, battlefield, roundEffects) -> {
            battlefield.heroWalkBack();
            return TurnResult.HERO_MOVE_BACK;
        }));
    }

    private void addMoveForward(List<HeroTurn> turns) {
        turns.add(new HeroTurn(FORWARD, (hero, npc, battlefield, roundEffects) -> {
            battlefield.heroWalkForward();
            return TurnResult.HERO_MOVE_FORWARD;
        }));
    }
}
