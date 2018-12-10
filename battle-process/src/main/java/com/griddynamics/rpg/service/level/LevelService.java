package com.griddynamics.rpg.service.level;

import com.griddynamics.rpg.controller.HeroController;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.Level;

import java.util.concurrent.locks.ReentrantLock;

public final class LevelService {

    private static final Double EXPERIENCE_COEFF = 0.5;
    private static final Double LVL_DIVIDER = 10.0;
    private static final Integer SKILL_POINTS_PER_ROUND = 10;
    private static final ReentrantLock lock = new ReentrantLock();

    private static LevelService levelService;

    private final HeroController controller;

    private LevelService() {
        this.controller = new HeroController();
    }

    public static LevelService getInstance() {
        if (levelService == null) {
            lock.lock();
            try {
                if (levelService == null) {
                    levelService = new LevelService();
                }
            } finally {
                lock.unlock();
            }
        }
        return levelService;
    }

    void updateLvlAndExperience(Hero hero, int experience) {
        Level level = hero.getLevel();
        if (isLvlUp(level, experience)) {
            lvlUp(hero);
        } else {
            updateExperience(level, experience);
        }
    }

    private boolean isLvlUp(Level heroLevel, int experience) {
        return heroLevel.getAmountXpForNextLvl() - (heroLevel.getExperience() + experience) <= 0;
    }

    private void updateExperience(Level heroLevel, int experience) {
        int newExperience = heroLevel.getExperience() + experience;
        heroLevel.setExperience(newExperience);
    }

    private void lvlUp(Hero hero) {
        Level heroLevel = hero.getLevel();
        heroLevel.setCurrentLevel(heroLevel.getCurrentLevel() + 1);
        heroLevel.setExperience(heroLevel.getAmountXpForNextLvl());
        int experienceToNextLvl = calculateXpForNextLvl(heroLevel.getCurrentLevel());
        heroLevel.setAmountXpForNextLvl(heroLevel.getExperience() + experienceToNextLvl);
        heroLevel.setSkillPoints(heroLevel.getSkillPoints() + SKILL_POINTS_PER_ROUND);
        userChooseStatsStage(hero);
    }

    private int calculateXpForNextLvl(int currentLevel) {
        int experienceToNextLvlByFormula = (int) (Level.DEFAULT_EXP_FOR_LVL * currentLevel
                * (currentLevel / LVL_DIVIDER + EXPERIENCE_COEFF));
        int experienceToNextLvl;
        if (currentLevel == Level.MAX_LVL) {
            experienceToNextLvl = Integer.MAX_VALUE;
            System.out.println("You reached max level in the game, congrats!");
        } else {
            experienceToNextLvl = experienceToNextLvlByFormula;
        }
        return experienceToNextLvl;
    }

    private void userChooseStatsStage(Hero hero) {
        System.out.println("You have next lvl");
        controller.setAttributes(hero);
    }
}
