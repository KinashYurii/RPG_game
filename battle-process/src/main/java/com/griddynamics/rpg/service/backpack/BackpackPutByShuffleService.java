package com.griddynamics.rpg.service.backpack;


import com.griddynamics.rpg.exception.CantAddToBackpackException;
import com.griddynamics.rpg.model.backpack.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.griddynamics.rpg.util.config.BackpackConfig.MAX_HEIGHT;
import static com.griddynamics.rpg.util.config.BackpackConfig.MAX_WIDTH;

class BackpackPutByShuffleService {
    private final BackpackLevelService backpackLevelService = new BackpackLevelService();

    private List<BackpackLevel> backpackLevels;

    void shuffleAndPut(Backpack backpack, BackpackPlacable item) throws CantAddToBackpackException {
        BackpackPlace backpackPlace = item.getBackpackPlace();

        int itemHeight = backpackPlace.getHeight();
        int itemWidth = backpackPlace.getWidth();

        if (backpack.getFreeSlots() < itemHeight * itemWidth) {
            throw new CantAddToBackpackException();
        }

        tryToPut(backpack, item);
    }

    void shuffle(Backpack backpack) throws CantAddToBackpackException {
        List<BackpackPlacable> items = backpack.getItems();
        BackpackEntry newEntry = generateEntry(new ArrayList<>(items));
        backpack.setBackpackEntry(newEntry);
    }

    private void tryToPut(Backpack backpack, BackpackPlacable item) throws CantAddToBackpackException {
        List<BackpackPlacable> items = backpack.getItems();
        List<BackpackPlacable> newItems = new ArrayList<>(items);
        newItems.add(item);

        BackpackEntry newEntry = generateEntry(newItems);

        backpack.setBackpackEntry(newEntry);
        backpack.setItems(newItems);
    }

    private BackpackEntry generateEntry(List<BackpackPlacable> items) throws CantAddToBackpackException {
        backpackLevels = new ArrayList<>();
        if (items.size() > 0) {
            iterateOverItemsAndPutThemToLevels(items);
        }

        BackpackEntry backpackEntry = new BackpackEntry();
        List<List<BackpackPlacable>> itemsGrid = putItemsInGrid();
        backpackEntry.setItemsGrid(itemsGrid);
        return backpackEntry;
    }

    private List<List<BackpackPlacable>> putItemsInGrid() throws CantAddToBackpackException {
        boolean fitHeight = checkHeight();
        if (!fitHeight) {
            throw new CantAddToBackpackException();
        }
        return createGrid();
    }

    private List<List<BackpackPlacable>> createGrid() {
        List<List<BackpackPlacable>> result = new ArrayList<>();
        for (BackpackLevel backpackLevel : backpackLevels) {
            result.addAll(backpackLevel.getMatrix());
        }
        fillMatrixWithNulls(result);
        return result;
    }

    private void fillMatrixWithNulls(List<List<BackpackPlacable>> matrix) {
        int filledHeight = backpackLevels.stream().mapToInt(BackpackLevel::getHeight).sum();
        for (int i = filledHeight; i < MAX_HEIGHT; i++) {
            List<BackpackPlacable> newLevel = new ArrayList<>();
            for (int j = 0; j < MAX_WIDTH; j++) {
                newLevel.add(null);
            }
            matrix.add(newLevel);
        }
    }

    private boolean checkHeight() {
        int height = 0;
        for (BackpackLevel backpackLevel : backpackLevels) {
            height += backpackLevel.getHeight();
        }
        return height <= MAX_HEIGHT;
    }

    private void iterateOverItemsAndPutThemToLevels(List<BackpackPlacable> items) {
        sortByHeight(items);

        initLevel(items.get(0));
        for (BackpackPlacable item : items) {
            boolean placed = placeItem(item);
            if (!placed) {
                createLevelAndPlace(item);
            }
        }
    }

    private void createLevelAndPlace(BackpackPlacable item) {
        initLevel(item);
        int lastIndex = backpackLevels.size() - 1;
        backpackLevelService.placeItemOnLevel(backpackLevels.get(lastIndex), item);
    }

    private void sortByHeight(List<BackpackPlacable> items) {
        items.sort(Comparator.comparingInt(o -> o.getBackpackPlace().getHeight()));
        Collections.reverse(items);
    }

    private void initLevel(BackpackPlacable backpackPlacable) {
        int height = backpackPlacable.getBackpackPlace().getHeight();
        BackpackLevel backpackLevel = backpackLevelService.create(height);
        backpackLevels.add(backpackLevel);
    }

    private boolean placeItem(BackpackPlacable item) {
        for (BackpackLevel backpackLevel : backpackLevels) {
            boolean placed = backpackLevelService.placeItemOnLevel(backpackLevel, item);
            if (placed) {
                return true;
            }
        }
        return false;
    }
}
