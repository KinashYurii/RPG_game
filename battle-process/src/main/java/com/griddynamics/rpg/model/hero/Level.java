package com.griddynamics.rpg.model.hero;

import lombok.Getter;
import lombok.Setter;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_POINTS_QUANTITY;

@Setter
@Getter
public class Level {
    public static final Integer DEFAULT_EXP_FOR_LVL = 10;
    public static final Integer MAX_LVL = 20;
    private int currentLevel = 1;
    private int experience;
    private int amountXpForNextLvl = DEFAULT_EXP_FOR_LVL;
    private int skillPoints = DEFAULT_POINTS_QUANTITY;
}
