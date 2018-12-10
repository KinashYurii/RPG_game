package com.griddynamics.rpg.service.backpack;

import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.backpack.BackpackEntry;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;

import java.util.List;

class BackpackRemoveService {
    void remove(Backpack backpack, BackpackPlacable item) {
        boolean removed = backpack.getItems().remove(item);
        if (removed) {
            rebuildGrid(backpack.getBackpackEntry(), item);
        }
    }

    private void rebuildGrid(BackpackEntry backpackEntry, BackpackPlacable item) {
        List<List<BackpackPlacable>> itemsGrid = backpackEntry.getItemsGrid();
        int height = itemsGrid.size();
        int width = itemsGrid.get(0).size();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (itemsGrid.get(i).get(j) != null && itemsGrid.get(i).get(j).equals(item)) {
                    itemsGrid.get(i).set(j, null);
                }
            }
        }
    }
}
