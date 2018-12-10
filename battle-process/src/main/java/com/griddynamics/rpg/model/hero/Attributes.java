package com.griddynamics.rpg.model.hero;

import lombok.*;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_CHARACTERISTIC;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Attributes {
    private int willpower = DEFAULT_CHARACTERISTIC;
    private int agility = DEFAULT_CHARACTERISTIC;
    private int dexterity = DEFAULT_CHARACTERISTIC;
    private int strength = DEFAULT_CHARACTERISTIC;
    private int intelligence = DEFAULT_CHARACTERISTIC;
    private int charisma = DEFAULT_CHARACTERISTIC;
    private int stamina = DEFAULT_CHARACTERISTIC;
    private int preparedBound;
}
