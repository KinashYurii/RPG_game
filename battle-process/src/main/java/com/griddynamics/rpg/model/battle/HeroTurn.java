package com.griddynamics.rpg.model.battle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeroTurn {
    private final String name;
    private final Turn turn;
}
