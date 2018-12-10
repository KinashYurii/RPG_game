package com.griddynamics.rpg.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapBuilder {
    public static <T> Map<Integer, T> mapWithIndexes(List<T> entities) {
        return IntStream
                .range(0, entities.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, entities::get));
    }
}
