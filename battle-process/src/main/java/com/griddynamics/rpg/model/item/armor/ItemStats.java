package com.griddynamics.rpg.model.item.armor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ItemStats {
    private double health;
    private double mana;
    private double rage;
    private double armor;
}
