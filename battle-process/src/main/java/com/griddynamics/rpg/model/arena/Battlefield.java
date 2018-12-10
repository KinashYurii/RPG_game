package com.griddynamics.rpg.model.arena;

import com.griddynamics.rpg.model.battle.Attackable;
import lombok.Getter;
import lombok.Setter;

import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.RANDOM;

@Setter
@Getter
public class Battlefield {
    private static final int MIDDLE = 4;
    private static final int START = 1;
    private static final int END = 7;
    private int heroPosition;
    private int npcPosition;

    public Battlefield(Attackable npc) {
        heroPosition = START;
        npcPosition = RANDOM.nextInt(3) + 4;

        if (npc.prepared()) {
            npcPosition = MIDDLE;
        }
    }

    public boolean heroCanWalkForward() {
        return heroPosition < END && heroPosition < npcPosition;
    }

    public boolean npcCanWalkForward() {
        return npcPosition > START && npcPosition > heroPosition;
    }

    public boolean heroCanWalkBack() {
        return heroPosition > START;
    }

    public boolean npcCanWalkBack() {
        return npcPosition < END;
    }

    public void heroWalkForward() {
        if (heroCanWalkForward()) {
            heroPosition++;
        }
    }

    public void heroWalkBack() {
        if (heroCanWalkBack()) {
            heroPosition--;
        }
    }

    public void npcWalkForward() {
        if (npcCanWalkForward()) {
            npcPosition--;
        }
    }

    public void npcWalkBack() {
        if (npcCanWalkBack()) {
            npcPosition++;
        }
    }

    public int detDistanceBetweenHeroAndNpc() {
        return Math.abs(npcPosition - heroPosition);
    }

    public int getLengthBetweenNpcAndHero() {
        return npcPosition - heroPosition;
    }

}