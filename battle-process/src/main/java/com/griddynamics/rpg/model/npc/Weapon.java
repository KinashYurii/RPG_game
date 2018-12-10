package com.griddynamics.rpg.model.npc;

import com.griddynamics.rpg.util.constants.npc.NpcTypeConstants;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Weapon {
    private double attackCount;
    private double maxDamage;
    private double range;
    private DamageType damageType;

    public int doDamage() {
        int count = (int) attackCount;
        int damage = 0;
        while (count-- > 0) {
            damage = NpcTypeConstants.RANDOM.nextInt((int) maxDamage) + 1;
        }
        return damage;
    }

    @Override
    public String toString() {
        return (int) attackCount + "d" + (int) maxDamage + ", Damage type: " + damageType;
    }
}
