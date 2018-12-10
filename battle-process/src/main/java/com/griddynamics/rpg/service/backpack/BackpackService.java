package com.griddynamics.rpg.service.backpack;


import com.griddynamics.rpg.exception.CantAddToBackpackException;
import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.backpack.BackpackEntry;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.item.Potion;

import java.util.ArrayList;
import java.util.List;

import static com.griddynamics.rpg.util.config.BackpackConfig.MAX_HEIGHT;
import static com.griddynamics.rpg.util.config.BackpackConfig.MAX_WIDTH;

public class BackpackService {
    private final BackpackPutService backpackPutService = new BackpackPutService();
    private final BackpackPutByShuffleService backpackPutByShuffleService = new BackpackPutByShuffleService();
    private final BackpackPutByPermutationService backpackPutByPermutationService = new BackpackPutByPermutationService();
    private final BackpackRemoveService backpackRemoveService = new BackpackRemoveService();

    public Backpack getBackpack() {
        Backpack backpack = Backpack.builder()
                .backpackEntry(new BackpackEntry())
                .items(new ArrayList<>())
                .freeSlots(MAX_HEIGHT * MAX_WIDTH)
                .build();

        try {
            addToBackpack(backpack, new Potion());
            addToBackpack(backpack, new Potion());
            addToBackpack(backpack, new Potion());
        } catch (CantAddToBackpackException e) {
            e.printStackTrace();
        }

        return backpack;
    }

    public void addToBackpack(Backpack backpack, BackpackPlacable item) throws CantAddToBackpackException {
        if (haveEnoughPlaceForItem(backpack, item)) {
            try {
                backpackPutService.put(backpack, item);
            } catch (CantAddToBackpackException e) {
                tryPutByShuffle(backpack, item);
            }
        } else {
            throw new CantAddToBackpackException();
        }
        calculateAndSetFreeSlots(backpack);
    }

    private void tryPutByShuffle(Backpack backpack, BackpackPlacable item) throws CantAddToBackpackException {
        try {
            backpackPutByShuffleService.shuffleAndPut(backpack, item);
        } catch (CantAddToBackpackException e) {
            backpackPutByPermutationService.putCreatingPermutations(backpack, item);
        }
    }

    private void calculateAndSetFreeSlots(Backpack backpack) {
        List<BackpackPlacable> items = backpack.getItems();

        int fullArea = MAX_WIDTH * MAX_HEIGHT;
        int placedArea = items.stream()
                .map(BackpackPlacable::getBackpackPlace)
                .mapToInt(place -> place.getHeight() * place.getWidth())
                .sum();

        int freeArea = fullArea - placedArea;
        backpack.setFreeSlots(freeArea);
    }

    private boolean haveEnoughPlaceForItem(Backpack backpack, BackpackPlacable item) {
        BackpackPlace backpackPlace = item.getBackpackPlace();

        int freeSlots = backpack.getFreeSlots();
        int placedArea = backpackPlace.getHeight() * backpackPlace.getWidth();

        return freeSlots >= placedArea;
    }

    void removeFromBackpack(Backpack backpack, BackpackPlacable item) {
        backpackRemoveService.remove(backpack, item);
        calculateAndSetFreeSlots(backpack);
    }
}
