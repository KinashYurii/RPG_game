package com.griddynamics.rpg.service.arena;

import com.griddynamics.rpg.model.arena.Location;
import com.griddynamics.rpg.model.arena.LocationInfo;
import com.griddynamics.rpg.model.arena.Node;
import com.griddynamics.rpg.service.chest.ChestGenerateService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.griddynamics.rpg.util.constants.LocationConstants.LOCATION_NAME_PSEUDO_RANDOM;

class LocationGenerationService {
    private Map<Integer, Location> locationMap;
    private int start = 0;

    private Location createLocation(Node node) {
        int index = LOCATION_NAME_PSEUDO_RANDOM.get(start++);
        LocationInfo locationInfo = getLocationInfo(index);
        Location location = Location.builder()
                .id(node.getId())
                .isFinish(node.isFinish())
                .name(locationInfo.getName())
                .chests(new ArrayList<>())
                .description(locationInfo.getDescription())
                .build();
        ChestGenerateService.getService().generateChestOnStart(location);
        return location;
    }

    private LocationInfo getLocationInfo(int index) {
        return LocationInfo.values()[index];
    }

    private void addLocation(List<Node> level) {
        level.forEach(node -> locationMap.put(node.getId(), createLocation(node)));
    }

    private void convertToLocation(Map<Integer, List<Node>> nodeMap) {
        int lastLevel = nodeMap.size() - 1;
        for (int i = lastLevel; i != -1; i--) {
            List<Node> level = nodeMap.get(i);
            addLocation(level);
        }
    }

    private void setLocationMap(Map<Integer, List<Node>> nodeMap) {
        convertToLocation(nodeMap);
        nodeMap.values().forEach(this::updateLocationWays);
    }

    private void updateLocationWays(List<Node> list) {
        list.forEach(this::rewriteLocation);
    }

    private void rewriteLocation(Node node) {
        List<Location> locationList = node.getWays()
                .stream()
                .map(ways -> locationMap.get(ways.getId()))
                .collect(Collectors.toList());
        locationMap.get(node.getId()).setPossibleWays(locationList);
    }

    Location generateLocations(Map<Integer, List<Node>> nodeMap) {
        locationMap = new HashMap<>();
        setLocationMap(nodeMap);
        return locationMap.get(0);
    }
}
