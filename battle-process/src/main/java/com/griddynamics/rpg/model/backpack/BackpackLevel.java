package com.griddynamics.rpg.model.backpack;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BackpackLevel {
    private int height;
    private int width;
    private List<List<BackpackPlacable>> matrix;
}
