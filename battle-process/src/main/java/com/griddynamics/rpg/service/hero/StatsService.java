package com.griddynamics.rpg.service.hero;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.Stats;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.*;

public class StatsService {
    private static StatsService statsService;

    private StatsService() {
    }

    public static StatsService getInstance() {
        if (statsService == null) {
            statsService = new StatsService();
        }
        return statsService;
    }

    /**
     * calculates health based on willpower,  stamina and agility
     */

    public void calculateHealth(Hero hero) {
        double health = hero.getAttributes().getStrength() * (HEALTH_COEFFICIENT * (hero.getAttributes().getStamina() + ((double) hero.getAttributes().getAgility()) / hero.getAttributes().getStrength()));
        hero.getStats().setMaxHealth(health);
        hero.getStats().setHealth(hero.getStats().getMaxHealth());
    }

    /**
     * calculates mana based on intelligence and charisma
     *
     * @param hero
     * @see Hero
     */

    public void calculateMana(Hero hero) {
        Attributes attributes = hero.getAttributes();
        double mana = attributes.getWillpower() * (MANA_COEFFICIENT * ((attributes.getCharisma() +
                (double) attributes.getIntelligence()) / attributes.getWillpower()));
        mana = mana < 0 ? 0 : mana;
        hero.getStats().setMaxMana(mana);
        hero.getStats().setMana(mana);
    }

    /**
     * calculates rage based on strength and agility
     * @param hero hero
     * @see Hero
     */

    public void calculateRage(Hero hero) {
        Attributes attributes = hero.getAttributes();
        double rage = attributes.getStrength() * (RAGE_COEFFICIENT * ((attributes.getStamina() -
                (double) attributes.getIntelligence()) / attributes.getIntelligence()));
        rage = rage < 0 ? 0 : rage;
        hero.getStats().setMaxRage(rage);
        hero.getStats().setRage(rage);
    }

    /**
     * calculates health regeneration per round
     * @param hero hero
     * @see Hero
     */

    public void regenerateHealth(Hero hero) {
        int stamina = hero.getAttributes().getStamina();
        int strength = hero.getAttributes().getStrength();
        int agility = hero.getAttributes().getAgility();
        double healthRegen = REGENERATE_HEALTH_COF_PER_ROUND *
                (Math.min(Math.min(strength / STRENGTH_COF_FOR_HEALTH, stamina / STAMINA_COF_FOR_HEALTH), agility));
        hero.getStats().setHealthRegen(roundNumber(healthRegen));
    }

    /**
     * calculates health regeneration per location
     * @param hero hero
     * @see Hero
     */
    public void regenerateHealthPerLocation(Hero hero) {
        double healthRegen = getHealthRegenPerLocation(hero);
        double regeneratedHealth = healthRegen + hero.getStats().getHealth();
        double maxHealth = hero.getStats().getMaxHealth();

        if (regeneratedHealth <= maxHealth) {
            hero.getStats().setHealth(roundNumber(regeneratedHealth));
        } else {
            hero.getStats().setHealth(maxHealth);
        }
    }

    private double getHealthRegenPerLocation(Hero hero) {
        int stamina = hero.getAttributes().getStamina();
        int strength = hero.getAttributes().getStrength();
        int agility = hero.getAttributes().getAgility();
        double regeneratedHealth = REGENERATE_HEALTH_COF_PER_LOCATION *
                (Math.min(Math.min(strength / STRENGTH_COF_FOR_HEALTH, stamina / STAMINA_COF_FOR_HEALTH), agility));
        if (regeneratedHealth < MAX_HEALTH_PER_LOCATION) {
            return regeneratedHealth;
        }
        return MAX_HEALTH_PER_LOCATION;
    }

    /**
     * calculates mana regeneration per round
     * @param hero hero
     * @see Hero
     */

    public void regenerateMana(Hero hero) {
        int willpower = hero.getAttributes().getWillpower();
        int intelligence = hero.getAttributes().getIntelligence();
        double manaRegen = REGENERATE_MANA_COEFFICIENT *
                (Math.min(willpower / WILLPOWER_COF_FOR_MANA, intelligence / INTELLECT_COF_FOR_MANA));
        hero.getStats().setManaRegen(roundNumber(manaRegen));
    }

    /**
     * calculates rage regeneration per round
     * @param hero hero
     * @see Hero
     */

    public void regenerateRage(Hero hero) {
        int strength = hero.getAttributes().getStrength();
        int stamina = hero.getAttributes().getStamina();
        int intelligence = hero.getAttributes().getIntelligence();
        double rageRegen = REGENERATE_RAGE_COEFFICIENT *
                (Math.min(Math.min(strength / STRENGTH_COF_FOR_RAGE, stamina / STAMINA_COF_FOR_RAGE), intelligence));
        hero.getStats().setRageRegen(roundNumber(rageRegen));
    }

    /**
     * calculates hero's magic resistance
     * @param hero hero
     * @see Hero
     */

    public void calculateMagicResistance(Hero hero) {
        Attributes attributes = hero.getAttributes();
        double magicResistance = MAGIC_RESSISTANCE_CALCULATION_COEFFICIENT_1 * attributes.getWillpower()
                / MAGIC_RESSISTANCE_CALCULATION_STAMINA + MAGIC_RESSISTANCE_CALCULATION_STAMINA_COEFFICIENT
                * attributes.getStamina() / MAGIC_RESSISTANCE_CALCULATION_COEFFICIENT_2;
        hero.getStats().setMagicResistance(magicResistance);
    }

    /**
     * calculates hero's armor
     * @param hero hero
     * @see Hero
     */

    public void calculateArmor(Hero hero) {
        Stats stats = hero.getStats();
        double armor = stats.getArmor();
        if (armor < ARMOR_CALCULATION_THRESHOLD) {
            stats.setArmorPercentage(armor * ARMOR_COEFFICIENT_LOWER_30_POINTS);
        } else {
            stats.setArmorPercentage(armor * ARMOR_COEFFICIENT_HIGHER_30_POINTS);
        }
    }

    /**
     * Calculating Facade
     * @param hero hero
     * @see Hero
     */

    public void calculateAllStats(Hero hero) {
        calculateHealth(hero);
        calculateMana(hero);
        calculateRage(hero);
        calculateArmor(hero);
        calculateMagicResistance(hero);
        regenerateHealth(hero);
        regenerateMana(hero);
        regenerateRage(hero);
    }


    public void applyHealth(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMaxHealth(stats.getMaxHealth() + stat);
        stats.setHealth(stats.getMaxHealth());
    }

    public boolean applyHealthPotion(Hero hero, double health) {
        Stats stats = hero.getStats();
        if (stats.getHealth() + health > stats.getMaxHealth()) {
            stats.setHealth(stats.getMaxHealth());
        } else {
            stats.setHealth(stats.getHealth() + health);
        }
        return true;
    }

    public void applyMana(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMaxMana(stats.getMaxMana() + stat);
        stats.setMana(stats.getMana() + stat);
    }

    public void applyRage(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMaxRage(stats.getMaxRage() + stat);
        stats.setRage(stats.getRage() + stat);
    }

    public void applyMagicResistance(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMagicResistance(stats.getMagicResistance() + stat);
    }

    public void applyArmor(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setArmor(stats.getArmor() + stat);
    }


    public void discardHealth(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMaxHealth(stats.getMaxHealth() - stat);
        stats.setHealth(stats.getHealth() - stat);
    }


    public void discardMana(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMaxMana(stats.getMaxMana() - stat);
        stats.setMana(stats.getMana() - stat);
    }


    public void discardRage(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMaxRage(stats.getMaxRage() - stat);
        stats.setRage(stats.getRage() - stat);
    }


    public void discardMagicResistance(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setMagicResistance(stats.getMagicResistance() - stat);
    }


    public void discardArmor(Hero hero, double stat) {
        Stats stats = hero.getStats();
        stats.setArmor(stats.getArmor() - stat);
    }

    private double roundNumber(double number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
