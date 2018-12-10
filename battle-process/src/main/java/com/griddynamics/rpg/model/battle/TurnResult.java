package com.griddynamics.rpg.model.battle;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TurnResult {

    public static final TurnResult HERO_DIED = TurnResult.builder()
            .message("Hero died")
            .build();

    public static final TurnResult NPC_DIED = TurnResult.builder()
            .message("Npc died")
            .build();

    public static final TurnResult HERO_RUN = TurnResult.builder()
            .message("Hero run away")
            .heroRun(true)
            .build();

    public static final TurnResult HERO_HOLD = TurnResult.builder()
            .message("Hero hold")
            .npcHit(false)
            .build();

    public static final TurnResult HERO_MOVE_FORWARD = TurnResult.builder()
            .message("Hero move forward")
            .npcHit(false)
            .build();

    public static final TurnResult HERO_MOVE_BACK = TurnResult.builder()
            .message("Hero move back")
            .npcHit(false)
            .build();

    public static final TurnResult NPC_HOLD = TurnResult.builder()
            .message("Npc hold")
            .npcHit(false)
            .build();

    public static final TurnResult NPC_MOVE_FORWARD = TurnResult.builder()
            .message("Npc move forward")
            .npcHit(false)
            .build();

    public static final TurnResult NPC_MOVE_BACK = TurnResult.builder()
            .message("Npc move back")
            .npcHit(false)
            .build();

    public static final TurnResult HP_REGEN = TurnResult.builder()
            .message("Regenerate HP")
            .build();

    private String message;
    private Attack attack;
    private AttackResult attackResult;
    private Attack reflection;
    private AttackResult reflectionResult;
    private double hpRegen;
    private boolean heroRun;
    private boolean npcHit;
}
