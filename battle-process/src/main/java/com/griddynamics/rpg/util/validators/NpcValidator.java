package com.griddynamics.rpg.util.validators;

import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.npc.AbilitiesType;
import com.griddynamics.rpg.model.npc.DamageType;
import com.griddynamics.rpg.model.npc.Npc;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NpcValidator {

    public static boolean isAbilityCrit(final Npc npc){
        return npc.getAbilitiesType() == AbilitiesType.CRIT;
    }

    public static boolean isAbilityNotCrit(final Npc npc){
        return npc.getAbilitiesType() != AbilitiesType.CRIT;
    }

    public static boolean isDamageTypePhysical(DamageType damageType) {
        return damageType == DamageType.PHYSICAL;
    }

    public static boolean isGeneratedMinLevelIsZero(Hero hero){
        return hero.getLevel().getCurrentLevel() - 1 == 0;
    }
}
