package com.griddynamics.rpg.service.roll;

public class PseudoRoll {
    private final double chance;
    private double currentChance;

    public PseudoRoll(double chance) {
        this.chance = chance;
    }

    public boolean areYouLucky() {
        fixChance();
        if (Math.random() < currentChance) {
            currentChance = chance - 0.2;

            return true;
        } else {
            currentChance = chance + 0.2;
            return false;
        }
    }

    public void fixChance() {
        if (currentChance < chance) {
            currentChance = chance;
        }
    }
}
