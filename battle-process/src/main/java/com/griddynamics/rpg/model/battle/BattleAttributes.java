package com.griddynamics.rpg.model.battle;

import com.griddynamics.rpg.model.hero.Stats;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BattleAttributes {
    private Stats stats;
    private int intellect;
}
