package com.griddynamics.rpg.util.constants;

import com.griddynamics.rpg.model.arena.LocationInfo;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class LocationConstants {
    public static final double PERCENT_DEFAULT_CHEST = 0.5;
    public static final double MAX_PERCENT = 0.6;
    public static final double MIN_PERCENT = 0.4;
    public static final int MIN_NODES_ON_LEVEL = 3;
    public static final int MAX_NODES_ON_LEVEL = 10;
    public static final int MIN_WAYS_FOR_NODE_GEN = 1;
    public static final int MAX_WAYS_FOR_NODE_GEN = 2;
    public static final int MAX_WAYS_FOR_NODE = 4;
    public static final int MAX_WAYS_FOR_NODE_ON_LEVEL = 2;
    public static final int FIRST_LEVEL = 1;
    public static final int SECOND_LEVEL = 2;
    public static final List<Integer> LOCATION_NAME_PSEUDO_RANDOM;
    public static final String NO_NPC_IN_LOCATION = "There are no npc to fight in location";
    public static final String CAN_GO_PREVIOUS = "YOU CAN GO TO PREVIOUS LOCATION";
    public static final String INVISIBLE = "YOU ARE INVISIBLE";

    static {
        List<Integer> locations = IntStream.rangeClosed(0, LocationInfo.values().length - 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(locations);
        LOCATION_NAME_PSEUDO_RANDOM = List.copyOf(locations);
    }
}
