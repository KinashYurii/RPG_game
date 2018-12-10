package com.griddynamics.rpg.service.backpack;

import com.griddynamics.rpg.exception.CantAddToBackpackException;
import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.backpack.BackpackEntry;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.backpack.BackpackPlace;

import java.util.ArrayList;
import java.util.List;

import static com.griddynamics.rpg.util.config.BackpackConfig.MAX_HEIGHT;
import static com.griddynamics.rpg.util.config.BackpackConfig.MAX_WIDTH;

class BackpackPutByPermutationService {
    private static final int MAX_NUMBER_OF_TRANSFORMATION = 25000;
    private int heightIndexToPut;
    private int widthIndexToPut;

    void putCreatingPermutations(Backpack backpack, BackpackPlacable item) throws CantAddToBackpackException {
        List<BackpackPlacable> newItems = getNewItems(backpack, item);
        List<BackpackPlacable> itemsForPermutations = new ArrayList<>(newItems);
        List<List<BackpackPlacable>> permutation = generatePermutations(itemsForPermutations);

        tryPutEveryPermutation(backpack, permutation);

        backpack.setItems(newItems);
    }

    private List<BackpackPlacable> getNewItems(Backpack backpack, BackpackPlacable item) {
        List<BackpackPlacable> items = backpack.getItems();
        List<BackpackPlacable> newItems = new ArrayList<>(items);
        newItems.add(item);
        return newItems;
    }

    private void tryPutEveryPermutation(Backpack backpack, List<List<BackpackPlacable>> permutations) throws CantAddToBackpackException {
        BackpackEntry backpackEntry = createEntry(permutations);
        if (backpackEntry == null) {
            throw new CantAddToBackpackException();
        }
        backpack.setBackpackEntry(backpackEntry);
    }

    private BackpackEntry createEntry(List<List<BackpackPlacable>> permutations) {
        for (List<BackpackPlacable> items : permutations) {
            BackpackEntry backpackEntry = entryFromList(items);
            if (backpackEntry != null) {
                return backpackEntry;
            }

        }
        return null;
    }

    private BackpackEntry entryFromList(List<BackpackPlacable> items) {
        List<List<BackpackPlacable>> entryMatrix = getEmptyEntry();
        for (BackpackPlacable item : items) {
            boolean putted = put(item, entryMatrix);
            if (!putted) {
                return null;
            }
        }
        BackpackEntry entry = new BackpackEntry();
        entry.setItemsGrid(entryMatrix);
        return entry;
    }

    private boolean put(BackpackPlacable item, List<List<BackpackPlacable>> matrix) {
        boolean canPlace = findPlace(matrix, item.getBackpackPlace());
        if (canPlace) {
            place(item, matrix);
            return true;
        }
        return false;
    }

    private void place(BackpackPlacable item, List<List<BackpackPlacable>> matrix) {
        BackpackPlace place = item.getBackpackPlace();
        for (int height = heightIndexToPut; height < heightIndexToPut + place.getHeight(); height++) {
            for (int width = widthIndexToPut; width < widthIndexToPut + place.getWidth(); width++) {
                matrix.get(height).set(width, item);
            }
        }
    }

    private boolean findPlace(List<List<BackpackPlacable>> matrix, BackpackPlace backpackPlace) {
        int height = backpackPlace.getHeight();
        int width = backpackPlace.getWidth();
        for (int currHeight = 0; currHeight <= MAX_HEIGHT - height; currHeight++) {
            for (int currWidth = 0; currWidth <= MAX_WIDTH - width; currWidth++) {
                boolean placed = placeIfHavePossible(matrix, height, width, currHeight, currWidth);
                if (placed) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean placeIfHavePossible(List<List<BackpackPlacable>> matrix, int height, int width, int currHeight, int currWidth) {
        if (matrix.get(currHeight).get(currWidth) == null) {
            return checkForFullFeasibility(currWidth, currHeight, height, width, matrix);
        }
        return false;
    }

    private boolean checkForFullFeasibility(int widthStart, int heightStart, int height, int width, List<List<BackpackPlacable>> matrix) {
        for (int currHeight = heightStart; currHeight < heightStart + height; currHeight++) {
            for (int currWidth = widthStart; currWidth < widthStart + width; currWidth++) {
                if (notNull(matrix, currHeight, currWidth)) {
                    return false;
                }
            }
        }
        heightIndexToPut = heightStart;
        widthIndexToPut = widthStart;
        return true;
    }

    private boolean notNull(List<List<BackpackPlacable>> matrix, int height, int width) {
        return matrix.get(height).get(width) != null;
    }

    private List<List<BackpackPlacable>> getEmptyEntry() {
        List<List<BackpackPlacable>> matrix = new ArrayList<>(MAX_HEIGHT);
        for (int currentHeight = 0; currentHeight < MAX_HEIGHT; currentHeight++) {
            initSlotsOnHeight(matrix, currentHeight);
        }
        return matrix;
    }

    private void initSlotsOnHeight(List<List<BackpackPlacable>> matrix, int height) {
        matrix.add(new ArrayList<>(MAX_WIDTH));
        for (int currentWidth = 0; currentWidth < MAX_WIDTH; currentWidth++) {
            matrix.get(height).add(null);
        }
    }

    private <E> List<List<E>> generatePermutations(List<E> input) {
        if (input.size() == 0) {
            return emptyResultListForPermutations();
        }
        E firstElement = input.remove(0);
        List<List<E>> result = new ArrayList<>();

        List<List<E>> permutations = generatePermutations(input);
        for (List<E> permutation : permutations) {
            if (splitOnSmallerPermutation(firstElement, result, permutation)) {
                return result;
            }
        }
        return result;
    }

    private <E> boolean splitOnSmallerPermutation(E firstElement, List<List<E>> result, List<E> permutation) {
        for (int index = 0; index <= permutation.size(); index++) {
            List<E> temp = new ArrayList<>(permutation);
            temp.add(index, firstElement);
            result.add(temp);
            if (result.size() == MAX_NUMBER_OF_TRANSFORMATION) {
                return true;
            }
        }
        return false;
    }

    private <E> List<List<E>> emptyResultListForPermutations() {
        List<List<E>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        return result;
    }
}
