package com.griddynamics.rpg.model.backpack;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Backpack {
    private int freeSlots;
    private List<BackpackPlacable> items;
    private BackpackEntry backpackEntry;
}
