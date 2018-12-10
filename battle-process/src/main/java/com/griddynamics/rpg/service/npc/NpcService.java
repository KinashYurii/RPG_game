package com.griddynamics.rpg.service.npc;

import com.griddynamics.rpg.model.battle.BattleAttributes;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.npc.DamageType;
import com.griddynamics.rpg.model.npc.Npc;
import com.griddynamics.rpg.model.npc.NpcType;
import com.griddynamics.rpg.util.constants.npc.MeleeConstants;
import com.griddynamics.rpg.util.constants.npc.NpcTypeConstants;
import com.griddynamics.rpg.util.constants.npc.RangeConstants;

import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.*;
import static com.griddynamics.rpg.util.validators.NpcValidator.isDamageTypePhysical;

public class NpcService {

    private static NpcService npcService;

    private NpcService() {
    }

    public static NpcService getInstance() {
        if (npcService == null) {
            npcService = new NpcService();
        }
        return npcService;
    }

    private NpcType generateNpcType() {
        return NpcType.values()[RANDOM.nextInt(NpcType.values().length)];
    }

    public Npc generate(Hero hero) {
        NpcType type = generateNpcType();
        NpcTypeConstants npcTypeConstants = type == NpcType.MELEE
                ? new MeleeConstants()
                : new RangeConstants();
        return new NpcCreatorService(npcTypeConstants).build(hero);
    }

    public boolean isAbilitiesChanceNeedChange(BattleAttributes heroBattleAttributes, BattleAttributes npcBattleAttributes,
                                               DamageType heroDamageType, DamageType npcDamageType) {
        double npcEffectiveHp = getNpcEffectiveHp(heroDamageType, npcBattleAttributes);
        double heroEffectiveHp = getHeroEffectiveHp(npcDamageType, heroBattleAttributes);

        double currentThreshold = 1 - heroEffectiveHp / npcEffectiveHp;
        return currentThreshold > EFFECTIVE_HEALTH_THRESHOLD;
    }

    private double getNpcEffectiveHp(DamageType heroDamageType, BattleAttributes npcBattleAttributes) {
        double npcHealth = npcBattleAttributes.getStats().getHealth();
        return npcHealth * (1 + percentDamageReduce(heroDamageType, npcBattleAttributes));
    }

    private double getHeroEffectiveHp(DamageType npcDamageType, BattleAttributes heroBattleAttributes) {
        double heroHealth = heroBattleAttributes.getStats().getHealth();
        return heroHealth * (1 + percentDamageReduce(npcDamageType, heroBattleAttributes));
    }

    public double percentDamageReduce(DamageType damageType, BattleAttributes battleAttributes) {
        if (isDamageTypePhysical(damageType)) {
            return battleAttributes.getStats().getArmor() * battleAttributes.getStats().getArmor() > ARMOR_CHANGE_REDUCE_POINTS ?
                    ARMOR_REDUCE_MORE :
                    ARMOR_REDUCE_LESS;
        }
        return battleAttributes.getStats().getMagicResistance() / 100;
    }
}
