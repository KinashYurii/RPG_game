package com.griddynamics.rpg.service.arena;

import com.griddynamics.rpg.model.arena.Location;

import java.util.*;

import static java.util.Objects.nonNull;

public class SearchWayService {
    private final Map<Location, Location> ways = new HashMap<>();

    public Location getWay(Location from, int lengthBetweenStartAndEnd) {
        int goalNodeId = lengthBetweenStartAndEnd + 1;
        Location goalLocation = getGoalLocation(from, goalNodeId);
        return findWay(from, goalLocation);
    }

    private Location findWay(Location from, Location way) {
        while (!ways.get(way).equals(from)) {
            way = ways.get(way);
        }
        return way;
    }

    private Location getGoalLocation(Location from, int goalNodeId) {
        Queue<Location> locationQueue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        locationQueue.add(from);
        visited.add(from.getId());
        while (!locationQueue.isEmpty()) {
            Location way = moveBySubLocations(locationQueue, visited, goalNodeId);
            if (nonNull(way)) {
                return way;
            }
        }
        return null;
    }

    private Location moveBySubLocations(Queue<Location> locationQueue, Set<Integer> visited, int goalNodeId) {
        Location current = locationQueue.remove();
        for (Location way : current.getPossibleWays()) {
            applyIfNotVisited(locationQueue, visited, current, way);
            if (way.getId() == goalNodeId) {
                return way;
            }
        }
        return null;
    }

    private void applyIfNotVisited(Queue<Location> locationQueue, Set<Integer> visited, Location current, Location way) {
        if (!visited.contains(way.getId())) {
            ways.put(way, current);
            visited.add(way.getId());
            locationQueue.add(way);
        }
    }
}
