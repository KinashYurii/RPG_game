package com.griddynamics.rpg.service.arena;

import com.griddynamics.rpg.model.arena.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.griddynamics.rpg.util.constants.LocationConstants.*;
import static java.util.Objects.nonNull;

class GraphGenerationService {
    private Map<Integer, List<Node>> nodeLevels;
    private int lastLevel;
    private int id;

    Map<Integer, List<Node>> generateGraph(int lengthBetweenStartAndEnd) {
        this.lastLevel = lengthBetweenStartAndEnd + 1;
        this.nodeLevels = new HashMap<>(lengthBetweenStartAndEnd + 2);
        generateWayFromStartToEnd();
        randomizeGraphOnLevels();
        return this.nodeLevels;
    }

    private void generateWayFromStartToEnd() {
        Node current = new Node();
        current.setWays(new ArrayList<>());
        for (int level = 0; level < lastLevel; level++) {
            current = createNewNodeWithWayForCurrent(current, level);
        }
        createEndNode(current);
    }

    private void randomizeGraphOnLevels() {
        addRandomNodesOnLevels();
        randomizeStart();
        randomizeNodesOnLevels();
        randomizeNodesBetweenLevels();
    }

    private void randomizeNodesOnLevels() {
        for (int level = FIRST_LEVEL; level < lastLevel; level++) {
            randomizeNodesOnLevel(nodeLevels.get(level));
        }
    }

    private void addRandomNodesOnLevels() {
        for (int level = FIRST_LEVEL; level < lastLevel; level++) {
            int random = random(MIN_NODES_ON_LEVEL, MAX_NODES_ON_LEVEL - 1);
            addRandomNodesByCount(random, level);
        }
    }

    private void randomizeNodesBetweenLevels() {
        for (int level = SECOND_LEVEL; level < lastLevel; level++) {
            randomizeBetweenLevels(nodeLevels.get(level - 1), nodeLevels.get(level));
        }
    }

    private void randomizeBetweenLevels(List<Node> levelAbove, List<Node> currentLevel) {
        currentLevel.forEach(nodeFromCurrentLevel -> addWaysForNodeBetweenLevels(levelAbove, nodeFromCurrentLevel));
    }

    private void addWaysForNodeBetweenLevels(List<Node> levelAbove, Node nodeFromCurrentLevel) {
        int currentMovesCount = nodeFromCurrentLevel.getWays().size();
        int targetMovesCount = random(MIN_WAYS_FOR_NODE_GEN, MAX_WAYS_FOR_NODE_GEN) + currentMovesCount;
        while (currentMovesCount < targetMovesCount && currentMovesCount < MAX_WAYS_FOR_NODE) {
            Node nodeFromLevelAbove = getRandomNodeFromLevel(levelAbove, nodeFromCurrentLevel);
            if (nonNull(nodeFromLevelAbove)) {
                addWay(nodeFromLevelAbove, nodeFromCurrentLevel);
            }
            currentMovesCount++;
        }
    }

    private Node getRandomNodeFromLevel(List<Node> level, Node node) {
        List<Integer> looked = new ArrayList<>();
        int maxIndex = level.size() - 1;
        int randomNodeIndex = random(0, maxIndex);
        Node randomNode = level.get(randomNodeIndex);
        while (nodesHaveWayOrHaveNotPlaceForWay(node, randomNode) && looked.size() < level.size()) {
            looked.add(randomNode.getId());
            randomNodeIndex = random(0, maxIndex);
            randomNode = level.get(randomNodeIndex);
        }
        return nodesHaveWayOrHaveNotPlaceForWay(node, randomNode) ? null : randomNode;
    }

    private boolean nodesHaveWayOrHaveNotPlaceForWay(Node node, Node randomNode) {
        return node.getWays().contains(randomNode) || randomNode.getWays().size() >= MAX_WAYS_FOR_NODE;
    }

    private void randomizeNodesOnLevel(List<Node> level) {
        level.forEach(currentNode -> addWaysForNodeOnLevel(level, currentNode));
    }

    private void addWaysForNodeOnLevel(List<Node> level, Node currentNode) {
        int currentMovesCount = currentNode.getWays().size();
        int targetMovesCount = random(MIN_WAYS_FOR_NODE_GEN, MAX_WAYS_FOR_NODE_GEN);
        while (currentMovesCount < targetMovesCount && currentMovesCount < MAX_WAYS_FOR_NODE_ON_LEVEL) {
            Node randomNode = findRandomNodeOnOneLevel(level, currentNode);
            addWay(currentNode, randomNode);
            currentMovesCount++;
        }
    }

    private void addWay(Node currentNode, Node nextNode) {
        currentNode.getWays().add(nextNode);
        nextNode.getWays().add(currentNode);
    }

    private Node findRandomNodeOnOneLevel(List<Node> level, Node current) {
        int randomNodeIndex = random(0, level.size() - 1);
        Node newNode = level.get(randomNodeIndex);
        while (nodesEqualsOrHaveNotPlaceForWay(newNode, current)) {
            randomNodeIndex = random(0, level.size() - 1);
            newNode = level.get(randomNodeIndex);
        }
        return newNode;
    }

    private boolean nodesEqualsOrHaveNotPlaceForWay(Node newNode, Node current) {
        return newNode.equals(current)
                || newNode.getWays().size() >= MAX_WAYS_FOR_NODE
                || current.getWays().contains(newNode);
    }

    private int random(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private void randomizeStart() {
        Node root = getRootNode();
        List<Node> nodes = nodeLevels.get(FIRST_LEVEL);
        while (root.getWays().size() < MIN_NODES_ON_LEVEL) {
            addRandomWayFromStart(root, nodes);
        }
    }

    private void addRandomWayFromStart(Node root, List<Node> nodes) {
        int maxNodeIndex = nodes.size() - 1;
        int nodeIndex = random(0, maxNodeIndex);
        while (root.getWays().contains(nodes.get(nodeIndex))) {
            nodeIndex = random(0, maxNodeIndex);
        }
        addWay(root, nodes.get(nodeIndex));
    }

    private Node getRootNode() {
        return nodeLevels.get(0).get(0);
    }

    private void addRandomNodesByCount(int nodeCount, int level) {
        for (int i = 0; i < nodeCount; i++) {
            Node node = new Node();
            node.setWays(new ArrayList<>());
            node.setId(nextId());
            node.setLevel(level);
            nodeLevels.get(level).add(node);
        }
    }

    private void createEndNode(Node node) {
        nodeLevels.computeIfAbsent(lastLevel, ArrayList::new).add(node);
        node.setId(nextId());
        node.setLevel(lastLevel);
        node.setFinish(true);
    }

    private Node createNewNodeWithWayForCurrent(Node current, int level) {
        nodeLevels.computeIfAbsent(level, ArrayList::new).add(current);
        current.setLevel(level);
        current.setId(nextId());
        Node next = new Node();
        next.setWays(new ArrayList<>());
        addWay(current, next);
        return next;
    }

    private int nextId() {
        return id++;
    }
}

