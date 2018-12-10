package com.griddynamics.rpg.model.item.armor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ItemAttributes {
    private int willpower;
    private int agility;
    private int dexterity;
    private int strength;
    private int intelligence;
    private int charisma;
    private int stamina;
}
