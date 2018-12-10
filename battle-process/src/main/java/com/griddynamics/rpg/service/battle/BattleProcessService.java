package com.griddynamics.rpg.service.battle;

import com.griddynamics.rpg.model.arena.Battlefield;
import com.griddynamics.rpg.model.battle.*;
import com.griddynamics.rpg.model.hero.Stats;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

public class BattleProcessService {
    private final NpcTurnService npcTurnService = new NpcTurnService();
    private final HeroTurnService heroTurnService = new HeroTurnService();
    private final ResultService resultService = new ResultService();

    public BattleResult launchBattle(Attackable hero, Attackable npc) {
        Set<RoundEffect> roundEffects = new HashSet<>();
        Battlefield battlefield = new Battlefield(npc);
        String npcInfo = getNpcStats(npc, hero);
        boolean battleContinues = true;
        int roundsCount = 0;
        int npcHitsCount = 0;

        while (battleContinues) {
            roundsCount++;
            showRoundInfo(hero, npcInfo, roundsCount);
            TurnResult heroTurn = heroTurnService.turn(hero, npc, battlefield, roundEffects);
            TurnResult npcTurn = npcTurnService.turn(hero, npc, battlefield);
            showTurnsResult(heroTurn, npcTurn);
            roundTriggers(roundEffects);
            battleContinues = hero.alive() && npc.alive() && !heroTurn.isHeroRun();
            if (heroTurn.isNpcHit()) {
                npcHitsCount++;
            }
        }

        return BattleResult.builder()
                .rounds(roundsCount)
                .heroDied(!hero.alive())
                .npcDied(!npc.alive())
                .npcHits(npcHitsCount)
                .build();
    }

    private void showTurnsResult(TurnResult... results) {
        Arrays.stream(results).forEach(resultService::showTurnResult);
    }

    private void showRoundInfo(Attackable hero, String npcInfo, int roundsCount) {
        System.out.println(ROUND + roundsCount);
        showHeroStats(hero);
        System.out.println(npcInfo);
    }

    private void showHeroStats(Attackable hero) {
        BattleAttributes heroAttributes = hero.getBattleAttributes();
        Stats stats = heroAttributes.getStats();
        String roundInfo = String.format(HERO_STATS_FORMAT,
                hero.getName(), stats.getHealth(), stats.getMana(), stats.getRage());
        System.out.println(roundInfo);
    }

    private String getNpcStats(Attackable npc, Attackable hero) {
        double range = getAttributeRange(hero.getBattleAttributes().getIntellect());
        BattleAttributes attributes = npc.getBattleAttributes();
        Stats stats = attributes.getStats();
        double hpRange = stats.getHealth() * range;
        double manaRange = stats.getMana() * range;
        double rageRange = stats.getRage() * range;
        return String.format(NPC_STATS_FORMAT,
                npc.getName(),
                stats.getHealth() - hpRange, stats.getHealth() + hpRange,
                stats.getMana() - manaRange, stats.getMana() + manaRange,
                stats.getRage() - rageRange, stats.getRage() + rageRange);
    }

    private void roundTriggers(Set<RoundEffect> roundEffects) {
        for (RoundEffect roundEffect : roundEffects) {
            AttackResult attackResult = roundEffect.apply();
            Attackable target = roundEffect.getTarget();
            TurnResult turnResult = TurnResult.builder()
                    .npcHit(false)
                    .attack(roundEffect.getAttack())
                    .attackResult(attackResult)
                    .message(String.format(ROUND_EFFECT_FORMAT, target.getName(), roundEffect.getAttack().getName()))
                    .build();
            showTurnsResult(turnResult);
        }
    }

    private double getAttributeRange(int intellect) {
        return intellect > 30 ? 0 : (double) 1 / intellect;
    }
}
