package com.griddynamics.rpg.model.battle;

import com.griddynamics.rpg.model.npc.DamageType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Attack {
    private String name;
    private double damage;
    private int range;
    private DamageType damageType;
    private Attack roundAttack;
}
