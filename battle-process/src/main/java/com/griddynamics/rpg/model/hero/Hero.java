package com.griddynamics.rpg.model.hero;

import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.battle.AttackResult;
import com.griddynamics.rpg.model.battle.Attackable;
import com.griddynamics.rpg.model.battle.BattleAttributes;
import com.griddynamics.rpg.model.hero.race.Race;
import com.griddynamics.rpg.model.hero.specilization.Specialization;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.model.npc.DamageType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_POINTS_QUANTITY;
import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.*;
import static com.griddynamics.rpg.util.validators.NpcValidator.isDamageTypePhysical;

@Getter
public class Hero implements Attackable {
    private final String name;
    private final Race race;
    private final Specialization specialization;
    private final Attributes attributes;
    private final Stats stats;
    private final ItemSlots itemSlots;
    private final Backpack backpack;
    @Setter
    private Level level;
    @Setter
    private boolean prepared;
    @Setter
    private double points = DEFAULT_POINTS_QUANTITY;

    public Hero(String name, Race race, Specialization specialization, Attributes attributes, Stats stats, ItemSlots itemSlots, Backpack backpack) {
        this.name = name;
        this.race = race;
        this.specialization = specialization;
        this.attributes = attributes;
        this.stats = stats;
        this.itemSlots = itemSlots;
        this.backpack = backpack;
        this.level = new Level();
    }

    @Override
    public AttackResult takeAttack(Attack attack, BattleAttributes npcAttribute) {
        AttackResult attackResult = new AttackResult();
        double damage = attack.getDamage();
        DamageType damageType = attack.getDamageType();
        takeDamage(damage, damageType, attackResult);
        regenerateHealth();
        regenerateMana();
        regenerateRage();
        return attackResult;
    }

    @Override
    public Set<Attack> possibleAttacks() {
        Weapon leftHand = getItemSlots().getLeftHand();
        Weapon rightHand = getItemSlots().getRightHand();
        Set<Attack> result = new HashSet<>(rightHand.getAttack(prepared));
        result.addAll(leftHand.getAttack(prepared));
        return new HashSet<>(result.stream()
                .collect(Collectors.toMap(Attack::getName, Function.identity(), (attack, attack2) -> attack))
                .values());
    }

    @Override
    public BattleAttributes getBattleAttributes() {
        return BattleAttributes.builder()
                .stats(stats)
                .intellect(attributes.getIntelligence())
                .build();
    }

    @Override
    public void resetAmmo() {
        getItemSlots().getRightHand().reset();
        getItemSlots().getLeftHand().reset();
    }

    @Override
    public boolean alive() {
        return stats.getHealth() > 0;
    }

    @Override
    public boolean prepared() {
        return prepared;
    }

    private void takeDamage(double damage, DamageType damageType, AttackResult attackResult) {
        double health = stats.getHealth();
        double resultDamage = damage - damage * percentDamageReduce(damageType, getBattleAttributes());
        stats.setHealth(health - resultDamage);
        attackResult.setTakenDamage(resultDamage);
        attackResult.setAlive(alive());
    }

    private void regenerateHealth() {
        double regeneratedHealth = stats.getHealth() + stats.getHealthRegen();
        if (regeneratedHealth <= stats.getMaxHealth()) {
            stats.setHealth(regeneratedHealth);
        } else {
            stats.setHealth(stats.getMaxHealth());
        }
    }

    private void regenerateMana() {
        double regeneratedMana = stats.getMana() + stats.getManaRegen();
        if (regeneratedMana <= stats.getMaxMana()) {
            stats.setMana(regeneratedMana);
        } else {
            stats.setMana(stats.getMaxMana());
        }
    }

    private void regenerateRage() {
        double regeneratedRage = stats.getRage() + stats.getRageRegen();
        if (regeneratedRage < 0) {
            regeneratedRage = 0;
        }
        if (regeneratedRage <= stats.getMaxRage()) {
            stats.setRage(regeneratedRage);
        } else {
            stats.setRage(stats.getMaxRage());
        }
    }

    private double percentDamageReduce(DamageType damageType, BattleAttributes battleAttributes) {
        if (isDamageTypePhysical(damageType)) {
            return battleAttributes.getStats().getArmor() * battleAttributes.getStats().getArmor() > ARMOR_CHANGE_REDUCE_POINTS ?
                    ARMOR_REDUCE_MORE :
                    ARMOR_REDUCE_LESS;
        }
        return battleAttributes.getStats().getMagicResistance() / 100;
    }

    public String getAllAttributes() {
        return "Attributes: \n" +
                "[1]: Willpower | value: " + attributes.getWillpower() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.WILLPOWER) + "\n" +
                "[2]: Agility   | value: " + attributes.getAgility() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.AGILITY) + "\n" +
                "[3]: Dexterity | value: " + attributes.getDexterity() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.DEXTERITY) + "\n" +
                "[4]: Strength  | value: " + attributes.getStrength() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.STRENGTH) + "\n" +
                "[5]: Intellect | value: " + attributes.getIntelligence() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.INTELLIGENCE) + "\n" +
                "[6]: Charisma  | value: " + attributes.getCharisma() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.CHARISMA) + "\n" +
                "[7]: Stamina   | value: " + attributes.getStamina() + " | one point costs: " + (int)
                race.getRaceAttributeCost(AttributesType.STAMINA);
    }

    @Override
    public String toString() {
        return "Hero name: " + name +
                "\nRace: " + race.getRaceType() +
                "\nSpecialization: " + specialization.getSpecializationType() +
                "\nLevel: " + level.getCurrentLevel() +
                "\n" + attributes +
                "\n" + stats +
                "\n" + itemSlots;
    }
}




