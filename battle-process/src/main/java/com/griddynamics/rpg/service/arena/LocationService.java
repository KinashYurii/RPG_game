package com.griddynamics.rpg.service.arena;

import com.griddynamics.rpg.model.arena.Location;
import com.griddynamics.rpg.model.arena.Node;
import com.griddynamics.rpg.service.chest.ChestGenerateService;

import java.util.List;
import java.util.Map;

public class LocationService {
    private final GraphGenerationService graphGenerationService = new GraphGenerationService();
    private final LocationGenerationService locationGenerationService = new LocationGenerationService();

    public Location generateRandomLocation(int length) {
        Map<Integer, List<Node>> graphLevels = graphGenerationService.generateGraph(length);
        ChestGenerateService.getService().calcLootLocationCount(calcLocation(graphLevels));
        return locationGenerationService.generateLocations(graphLevels);
    }

    private int calcLocation(Map<Integer, List<Node>> graphLevels) {
        return graphLevels.entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue().size())
                .sum();
    }
}
