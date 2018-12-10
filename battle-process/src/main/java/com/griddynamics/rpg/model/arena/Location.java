package com.griddynamics.rpg.model.arena;

import com.griddynamics.rpg.model.chest.Chest;
import com.griddynamics.rpg.model.npc.Npc;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Location {
    private int id;
    private String name;
    private String description;
    private boolean isFinish;
    private List<Location> possibleWays;
    private Npc npc;
    private List<Chest> chests;
    private boolean visited;
}
