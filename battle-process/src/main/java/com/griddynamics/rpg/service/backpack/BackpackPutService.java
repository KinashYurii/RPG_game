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

class BackpackPutService {
    private int heightIndexToPut;
    private int widthIndexToPut;

    void put(Backpack backpack, BackpackPlacable item) throws CantAddToBackpackException {

        BackpackPlace place = item.getBackpackPlace();
        List<BackpackPlacable> itemList = backpack.getItems();
        BackpackEntry backpackEntry = backpack.getBackpackEntry();
        List<List<BackpackPlacable>> matrix = backpack.getBackpackEntry().getItemsGrid();

        if (matrix == null) {
            matrix = fillMatrixWithNulls();
        }

        int heightBegin = 0;
        int widthBegin = 0;

        boolean havePlace = checkPlaceOnCeiling(heightBegin, widthBegin, place, matrix);

        if (havePlace) {
            place(item, matrix);
            itemList.add(item);
        } else {
            throw new CantAddToBackpackException();
        }
        backpackEntry.setItemsGrid(matrix);
        backpack.setItems(itemList);
        backpack.setBackpackEntry(backpackEntry);
    }

    private boolean checkPlaceOnCeiling(int heightBegin, int widthBegin, BackpackPlace place, List<List<BackpackPlacable>> matrix) {
        for (int height = heightBegin; height <= MAX_HEIGHT - place.getHeight(); height++) {
            for (int width = widthBegin; width <= MAX_WIDTH - place.getWidth(); width++) {
                if (checkPositionToPut(height, place, width, matrix)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkPositionToPut(int heightBegin, BackpackPlace place, int width, List<List<BackpackPlacable>> matrix) {
        if (matrix.get(heightBegin).get(width) == null) {
            return checkRangeToPut(width, heightBegin, place.getHeight(), place.getWidth(), matrix);
        }
        return false;
    }

    private boolean checkRangeToPut(int widthStart, int heightStart, int height, int width, List<List<BackpackPlacable>> matrix) {
        for (int i = heightStart; i < heightStart + height; i++) {
            for (int j = widthStart; j < widthStart + width; j++) {
                if (matrix.get(i).get(j) != null) {
                    return false;
                }
            }
        }
        heightIndexToPut = heightStart;
        widthIndexToPut = widthStart;
        return true;
    }

    private void place(BackpackPlacable item, List<List<BackpackPlacable>> matrix) {
        BackpackPlace place = item.getBackpackPlace();
        for (int height = heightIndexToPut; height < heightIndexToPut + place.getHeight(); height++) {
            for (int width = widthIndexToPut; width < widthIndexToPut + place.getWidth(); width++) {
                matrix.get(height).set(width, item);
            }
        }
    }

    private List<List<BackpackPlacable>> fillMatrixWithNulls() {
        List<List<BackpackPlacable>> matrix = new ArrayList<>();
        for (int i = 0; i < MAX_HEIGHT; i++) {
            List<BackpackPlacable> newLine = new ArrayList<>();
            for (int j = 0; j < MAX_WIDTH; j++) {
                newLine.add(null);
            }
            matrix.add(newLine);
        }
        return matrix;
    }
}
