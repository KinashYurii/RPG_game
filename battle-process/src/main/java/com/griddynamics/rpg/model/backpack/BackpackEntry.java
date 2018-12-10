package com.griddynamics.rpg.model.backpack;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BackpackEntry {
    private List<List<BackpackPlacable>> itemsGrid;
}
