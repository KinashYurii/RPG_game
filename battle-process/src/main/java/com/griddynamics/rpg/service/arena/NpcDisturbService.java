package com.griddynamics.rpg.service.arena;

import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.npc.Npc;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class NpcDisturbService {
    private static final int DISTURB_COUNT = 2;
    private static NpcDisturbService npcDisturbServiceInstance;
    @Getter
    private final Map<Npc, Integer> npcDisturb = new HashMap<>();

    public static NpcDisturbService getInstance() {
        if (npcDisturbServiceInstance == null) {
            npcDisturbServiceInstance = new NpcDisturbService();
        }
        return npcDisturbServiceInstance;
    }

    private void incrementDisturbCount(Npc npcInLocation) {
        npcDisturb.replaceAll((npc, oldValue) -> {
            if (!npc.equals(npcInLocation)) {
                return oldValue + 1;
            }
            return oldValue;
        });
    }

    private void calmDisturbNpc() {
        npcDisturb.forEach((npc, count) -> {
            if (count > 2) npc.setPrepared(false);
        });
        npcDisturb.entrySet().removeIf(e -> e.getValue() > DISTURB_COUNT);
    }

    public void npcDisturb(Npc npc, Hero hero) {
        npcDisturb.putIfAbsent(npc, 0);
        if (hero.isPrepared()) {
            npc.setPrepared(false);
        } else {
            npc.setPrepared(true);
        }
        incrementDisturbCount(npc);
        calmDisturbNpc();
    }
}