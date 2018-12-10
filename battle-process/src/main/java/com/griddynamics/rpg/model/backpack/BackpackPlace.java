package com.griddynamics.rpg.model.backpack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BackpackPlace {
    public static final BackpackPlace PLACE1x1 = new BackpackPlace(1, 1);
    public static final BackpackPlace PLACE1x2 = new BackpackPlace(1, 2);
    public static final BackpackPlace PLACE1x3 = new BackpackPlace(1, 3);
    public static final BackpackPlace PLACE1x4 = new BackpackPlace(1, 4);
    public static final BackpackPlace PLACE2x1 = new BackpackPlace(2, 1);
    public static final BackpackPlace PLACE3x1 = new BackpackPlace(3, 1);
    public static final BackpackPlace PLACE4x1 = new BackpackPlace(4, 1);
    public static final BackpackPlace PLACE2x2 = new BackpackPlace(2, 2);
    public static final BackpackPlace PLACE2x3 = new BackpackPlace(2, 3);
    public static final BackpackPlace PLACE3x3 = new BackpackPlace(3, 3);
    public static final BackpackPlace PLACE3x2 = new BackpackPlace(3, 2);
    public static final BackpackPlace PLACE4x2 = new BackpackPlace(4, 2);
    private final int height;
    private final int width;
}
