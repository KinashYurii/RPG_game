package com.griddynamics.rpg.model.npc;

import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.battle.AttackResult;
import com.griddynamics.rpg.model.battle.Attackable;
import com.griddynamics.rpg.model.battle.BattleAttributes;
import com.griddynamics.rpg.model.hero.Level;
import com.griddynamics.rpg.model.hero.Stats;
import com.griddynamics.rpg.model.npc.race.NpcRace;
import com.griddynamics.rpg.service.npc.NpcService;
import com.griddynamics.rpg.util.constants.npc.NpcTypeConstants;
import com.griddynamics.rpg.util.validators.NpcValidator;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Npc implements Attackable {
    private Stats npcStats;
    private double coefficient;
    private Level level;
    private Weapon weapon;
    private AbilitiesType abilitiesType;
    private NpcType npcType;
    private NpcRace race;
    private boolean isDead;
    private boolean isPrepared;

    public Npc() {
        this.level = new Level();
    }

    @Override
    public void resetAmmo() {

    }

    @Override
    public boolean alive() {
        return npcStats.getHealth() > 0;
    }

    @Override
    public String toString() {
        return "NPC:" +
                " Race: " + race +
                ", BackpackLevel: " + level +
                ", Type: " + npcType +
                ", Weapon: " + weapon +
                ", NPC ability: " + abilitiesType +
                "" + npcStats;
    }

    @Override
    public AttackResult takeAttack(Attack attack, BattleAttributes heroAttribute) {
        AttackResult attackResult = new AttackResult();
        takeDamageWithAbilities(attack, attackResult, heroAttribute);
        return attackResult;
    }

    @Override
    public Set<Attack> possibleAttacks() {
        Set<Attack> attacks = new HashSet<>();
        String attackName = weapon.getRange() > 1 ? "Range attack" : "Melee attack";
        attacks.add(Attack.builder()
                .damage(getWeapon().doDamage())
                .name(attackName)
                .damageType(getWeapon().getDamageType())
                .range((int) weapon.getRange())
                .build());
        if (npcType == NpcType.RANGE)
            attacks.add(Attack.builder()
                    .damage((double) getWeapon().doDamage() / 2)
                    .name("Range attack on melee zone")
                    .damageType(getWeapon().getDamageType())
                    .range(0)
                    .build());
        return attacks;
    }

    @Override
    public BattleAttributes getBattleAttributes() {
        return BattleAttributes.builder()
                .stats(npcStats)
                .build();
    }

    @Override
    public boolean prepared() {
        return isPrepared;
    }

    @Override
    public String getName() {
        return race.toString();
    }

    private void takeDamageWithAbilities(Attack attack, AttackResult attackResult, BattleAttributes heroAttribute) {
        if (NpcValidator.isAbilityNotCrit(this)) {
            double answerDamage = abilitiesType.useAbilities(NpcTypeConstants.ABILITIES_CHANCE.get(abilitiesType),
                    this, attack,
                    attackResult, heroAttribute);
            attackResult.setAnswerDamage(Attack.builder()
                    .damage(answerDamage)
                    .damageType(attack.getDamageType())
                    .build());
        } else
            takeDamage(attack.getDamage(), attack.getDamageType(), attackResult);
    }

    void takeDamage(double damage, DamageType damageType, AttackResult attackResult) {
        Stats npcStats = this.getNpcStats();
        double health = npcStats.getHealth();
        double resultDamage = damage - damage * NpcService.getInstance().percentDamageReduce(damageType,
                getBattleAttributes());
        npcStats.setHealth(health - resultDamage);
        attackResult.setTakenDamage(resultDamage);
        attackResult.setAlive(alive());
    }

    double doDamage(AttackResult attackResult, BattleAttributes target) {
        Attack attack = Attack.builder()
                .damage(weapon.doDamage())
                .damageType(weapon.getDamageType())
                .name("Attack")
                .range((int) weapon.getRange())
                .roundAttack(null)
                .build();
        if (NpcValidator.isAbilityCrit(this)) {
            AbilitiesType abilitiesType = getAbilitiesType();
            return abilitiesType.useAbilities(NpcTypeConstants.ABILITIES_CHANCE.get(abilitiesType),
                    this, attack, attackResult, target);
        }
        return attack.getDamage();
    }
}
