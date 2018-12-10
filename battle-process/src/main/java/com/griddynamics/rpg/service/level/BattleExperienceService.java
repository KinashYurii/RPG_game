package com.griddynamics.rpg.service.level;

import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.npc.Npc;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BattleExperienceService {

    private final LevelService levelService = LevelService.getInstance();

    public void updateExperienceByBattleResult(@NonNull Hero hero, @NonNull Npc npc, int hits) {
        double xpMultiplier = getXpMultiplier(hero.getLevel().getCurrentLevel(), npc.getLevel().getCurrentLevel());
        int experience = calculateExperienceByBattleResult(xpMultiplier, npc.getNpcStats().getMaxHealth(), hits);
        levelService.updateLvlAndExperience(hero, experience);
    }

    private int calculateExperienceByBattleResult(double xpMultiplier, double npcHealth, int hits) {
        return (int) (npcHealth / hits * xpMultiplier);
    }

    private double getXpMultiplier(int heroLvl, int npcLvl) {
        double lvlMultiplier = (double) npcLvl / heroLvl;
        BigDecimal bigDecimal = new BigDecimal(lvlMultiplier);
        return bigDecimal.setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}