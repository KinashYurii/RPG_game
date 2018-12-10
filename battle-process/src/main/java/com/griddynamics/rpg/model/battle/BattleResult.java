package com.griddynamics.rpg.model.battle;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BattleResult {
    private int rounds;
    private int npcHits;
    private boolean npcDied;
    private boolean heroDied;
}
