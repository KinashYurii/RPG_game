package com.griddynamics.rpg.model.item.armor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ArmorBonus {
    private double chance;
    private int damage;
}
