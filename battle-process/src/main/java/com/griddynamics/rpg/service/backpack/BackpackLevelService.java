package com.griddynamics.rpg.service.backpack;


import com.griddynamics.rpg.model.backpack.BackpackLevel;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.util.config.BackpackConfig;

import java.util.ArrayList;
import java.util.List;

class BackpackLevelService {
    private int heightIndexToPut;
    private int widthIndexToPut;

    boolean placeItemOnLevel(BackpackLevel backpackLevel, BackpackPlacable item) {
        if (putOnCeiling(backpackLevel, item)) {
            return true;
        }
        return putOnFloor(backpackLevel, item);
    }

    private boolean putOnCeiling(BackpackLevel backpackLevel, BackpackPlacable item) {
        BackpackPlace place = item.getBackpackPlace();
        int heightBegin = 0;
        int widthBegin = 0;

        boolean havePlace = checkPlaceOnCeiling(heightBegin, widthBegin, backpackLevel, place);
        if (havePlace) {
            place(backpackLevel, item);
            return true;
        }
        return false;
    }

    private boolean putOnFloor(BackpackLevel backpackLevel, BackpackPlacable item) {
        BackpackPlace place = item.getBackpackPlace();
        int heightBegin = backpackLevel.getHeight() - 1;
        int widthBegin = backpackLevel.getWidth() - 1;

        boolean havePlace = checkPlaceOnFloor(heightBegin, widthBegin, backpackLevel, place);
        if (havePlace) {
            place(backpackLevel, item);
            return true;
        }
        return false;
    }

    private boolean checkPlaceOnFloor(int heightBegin, int widthBegin, BackpackLevel backpackLevel, BackpackPlace place) {
        for (int width = widthBegin; width > place.getWidth(); width--) {
            if (canAddOnFloorFromCurrentSlot(heightBegin, width, backpackLevel, place)) {
                return true;
            }
        }
        return false;
    }

    private boolean canAddOnFloorFromCurrentSlot(int heightBegin, int widthBegin, BackpackLevel backpackLevel, BackpackPlace place) {
        List<List<BackpackPlacable>> matrix = backpackLevel.getMatrix();
        if (matrix.get(heightBegin).get(widthBegin) == null) {
            int width = place.getWidth();
            int height = place.getHeight();
            int widthStart = widthBegin - width + 1;
            int heightStart = backpackLevel.getHeight() - height;
            return checkForFullFeasibility(widthStart, heightStart, height, width, backpackLevel);
        }
        return false;
    }

    private void place(BackpackLevel backpackLevel, BackpackPlacable item) {
        BackpackPlace place = item.getBackpackPlace();
        List<List<BackpackPlacable>> matrix = backpackLevel.getMatrix();
        for (int height = heightIndexToPut; height < heightIndexToPut + place.getHeight(); height++) {
            for (int width = widthIndexToPut; width < widthIndexToPut + place.getWidth(); width++) {
                matrix.get(height).set(width, item);
            }
        }
    }

    private boolean checkPlaceOnCeiling(int heightBegin, int widthBegin, BackpackLevel backpackLevel, BackpackPlace place) {
        for (int width = widthBegin; width <= backpackLevel.getWidth() - place.getWidth(); width++) {
            if (canAddOnCeilingFromCurrentSlot(heightBegin, backpackLevel, place, width)) {
                return true;
            }
        }
        return false;
    }

    private boolean canAddOnCeilingFromCurrentSlot(int heightBegin, BackpackLevel backpackLevel, BackpackPlace place, int width) {
        List<List<BackpackPlacable>> matrix = backpackLevel.getMatrix();
        if (matrix.get(heightBegin).get(width) == null) {
            return checkForFullFeasibility(width, heightBegin, place.getHeight(), place.getWidth(), backpackLevel);
        }
        return false;
    }

    private boolean checkForFullFeasibility(int widthStart, int heightStart, int height, int width, BackpackLevel backpackLevel) {
        for (int i = heightStart; i < heightStart + height; i++) {
            for (int j = widthStart; j < widthStart + width; j++) {
                if (notNull(backpackLevel, i, j)) {
                    return false;
                }
            }
        }
        heightIndexToPut = heightStart;
        widthIndexToPut = widthStart;
        return true;
    }

    private boolean notNull(BackpackLevel backpackLevel, int height, int width) {
        List<List<BackpackPlacable>> matrix = backpackLevel.getMatrix();
        return matrix.get(height).get(width) != null;
    }

    BackpackLevel create(int height) {
        return BackpackLevel.builder()
                .height(height)
                .width(BackpackConfig.MAX_WIDTH)
                .matrix(initLevelMatrix(height))
                .build();
    }

    private List<List<BackpackPlacable>> initLevelMatrix(int height) {
        List<List<BackpackPlacable>> matrix = new ArrayList<>(height);
        for (int currentHeight = 0; currentHeight < height; currentHeight++) {
            initSlotsOnHeight(matrix, currentHeight);
        }
        return matrix;
    }

    private void initSlotsOnHeight(List<List<BackpackPlacable>> matrix, int height) {
        matrix.add(new ArrayList<>(BackpackConfig.MAX_WIDTH));
        for (int currentWidth = 0; currentWidth < BackpackConfig.MAX_WIDTH; currentWidth++) {
            matrix.get(height).add(null);
        }
    }
}
