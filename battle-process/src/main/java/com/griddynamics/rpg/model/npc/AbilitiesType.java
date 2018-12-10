package com.griddynamics.rpg.model.npc;

import com.griddynamics.rpg.model.battle.Attack;
import com.griddynamics.rpg.model.battle.AttackResult;
import com.griddynamics.rpg.model.battle.Attackable;
import com.griddynamics.rpg.model.battle.BattleAttributes;
import com.griddynamics.rpg.service.npc.NpcService;

import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.RANDOM;

public enum AbilitiesType {
    /**
     * Return damage for hero
     */
    CRIT {
        @Override
        public double useAbilities(double chance, Attackable npc, Attack attack, AttackResult attackResult,
                                   BattleAttributes heroBattleAttributes) {
            boolean abilitiesChanceNeedChange = NpcService.getInstance().isAbilitiesChanceNeedChange(
                    heroBattleAttributes, npc.getBattleAttributes(), attack.getDamageType(),
                    ((Npc) npc).getWeapon().getDamageType());
            chance = abilitiesChanceNeedChange ? chance / 2 : chance;
            if (chance > RANDOM.nextDouble()) {
                attackResult.setAbilities(CRIT.toString());
                return attack.getDamage() * 2;
            }
            return attack.getDamage();
        }
    },
    /**
     * Deal damage for npc, and return damage for hero
     */
    MISS {
        @Override
        public double useAbilities(double chance, Attackable npc, Attack attack, AttackResult attackResult,
                                   BattleAttributes heroBattleAttributes) {
            boolean abilitiesChanceNeedChange = NpcService.getInstance().isAbilitiesChanceNeedChange(
                    heroBattleAttributes, npc.getBattleAttributes(), attack.getDamageType(),
                    ((Npc) npc).getWeapon().getDamageType());
            chance = abilitiesChanceNeedChange ? chance / 2 : chance;
            if (chance > RANDOM.nextDouble()) {
                attackResult.setAbilities(MISS.toString());
                ((Npc) npc).takeDamage(0, attack.getDamageType(), attackResult);
                return 0;
            }
            ((Npc) npc).takeDamage(attack.getDamage(), attack.getDamageType(), attackResult);
            return 0;
        }
    },
    REFLECTION {
        @Override
        public double useAbilities(double chance, Attackable npc, Attack attack, AttackResult attackResult,
                                   BattleAttributes heroBattleAttributes) {
            boolean abilitiesChanceNeedChange = NpcService.getInstance().isAbilitiesChanceNeedChange(
                    heroBattleAttributes, npc.getBattleAttributes(), attack.getDamageType(),
                    ((Npc) npc).getWeapon().getDamageType());
            chance = abilitiesChanceNeedChange ? chance / 2 : chance;
            if (chance > RANDOM.nextDouble()) {
                attackResult.setAbilities(REFLECTION.toString());
                ((Npc) npc).takeDamage(attack.getDamage() / 2, attack.getDamageType(), attackResult);
                return attack.getDamage();
            }
            ((Npc) npc).takeDamage(attack.getDamage(), attack.getDamageType(), attackResult);
            return 0;
        }
    },
    COUNTERATTACK {
        @Override
        public double useAbilities(double chance, Attackable npc, Attack attack, AttackResult attackResult,
                                   BattleAttributes heroBattleAttributes) {
            boolean abilitiesChanceNeedChange = NpcService.getInstance().isAbilitiesChanceNeedChange(
                    heroBattleAttributes, npc.getBattleAttributes(), attack.getDamageType(),
                    ((Npc) npc).getWeapon().getDamageType());
            chance = abilitiesChanceNeedChange ? chance / 2 : chance;
            ((Npc) npc).takeDamage(attack.getDamage(), attack.getDamageType(), attackResult);
            if (chance > RANDOM.nextDouble()) {
                attackResult.setAbilities(COUNTERATTACK.toString());
                return ((Npc) npc).doDamage(attackResult, heroBattleAttributes);
            }
            return 0;
        }
    };

    public abstract double useAbilities(double chance, Attackable damageDealer, Attack attack,
                                        AttackResult attackResult, BattleAttributes battleAttributes);
}
