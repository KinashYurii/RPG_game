package com.griddynamics.rpg.model.hero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stats {
    private double maxHealth;
    private double health;
    private double maxMana;
    private double mana;
    private double maxRage;
    private double rage;
    private double healthRegen;
    private double manaRegen;
    private double rageRegen;
    private double magicResistance;
    private double armor;
    private double armorPercentage;

    @Override
    public String toString() {
        return "Stats:" +
                String.format("\033[31;1m HP: " + "%.1f", maxHealth) +
                String.format("\033[0m \033[34;1m  MN: " + "%.1f", mana) +
                String.format("\033[0m \033[33;1m  RG: " + "%.1f", rage) + "\033[0m" +
                String.format("\nMagic Resist: " + "%.2f", magicResistance) + "%" +
                String.format("\nArmor: " + "%.2f", armorPercentage) + "%";
    }
}
