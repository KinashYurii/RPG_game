package com.griddynamics.rpg.model.chest;

import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.LOOT_CHEST;

@NoArgsConstructor
public class Chest {
    @Getter
    private List<BackpackPlacable> items;

    public Chest(List<BackpackPlacable> item) {
        items = new ArrayList<>(item);
    }

    @Override
    public String toString() {
        return LOOT_CHEST + items;
    }
}