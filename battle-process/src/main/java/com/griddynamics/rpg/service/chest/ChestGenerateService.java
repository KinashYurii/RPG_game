package com.griddynamics.rpg.service.chest;

import com.griddynamics.rpg.model.arena.Location;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.chest.Chest;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.item.armor.factory.FactoryService;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.griddynamics.rpg.util.constants.LocationConstants.*;
import static com.griddynamics.rpg.util.constants.npc.NpcTypeConstants.RANDOM;

@Getter
public final class ChestGenerateService {
    private static ChestGenerateService chestGenerateService;
    private int lootLocationCount;
    private final FactoryService factoryService = new FactoryService();
    private List<Integer> onStart;
    private List<Integer> lootLocations;

    private ChestGenerateService() {
    }

    public static ChestGenerateService getService() {
        if (chestGenerateService == null) {
            chestGenerateService = new ChestGenerateService();
        }
        return chestGenerateService;
    }

    private List<Chest> generateLoot() {
        int chestInLocation = RANDOM.nextInt(3);
        List<Chest> chests = new ArrayList<>();
        for (int i = 0; i < chestInLocation; i++) {
            List<BackpackPlacable> itemsList = generateItemInChest(RANDOM.nextInt(3));
            chests.add(new Chest(itemsList));
        }
        return chests;
    }

    private List<BackpackPlacable> generateItemInChest(int countOfItems) {
        return IntStream.range(0, countOfItems)
                .mapToObj(i -> factoryService.getRandomItem())
                .collect(Collectors.toList());
    }

    public void calcLootLocationCount(int locationCount) {
        double random = RANDOM.nextDouble() * (MAX_PERCENT - MIN_PERCENT) + MIN_PERCENT;
        this.lootLocationCount = (int) (locationCount * random);
        List<Integer> locations = IntStream.rangeClosed(1, locationCount - 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(locations);

        lootLocations = locations
                .stream()
                .limit(lootLocationCount)
                .collect(Collectors.toList());
        calcAndGetLocationWithDefaultChest();
    }

    private void calcAndGetLocationWithDefaultChest() {
        double randomLootLocations = lootLocationCount * PERCENT_DEFAULT_CHEST;
        List<Integer> locations = new ArrayList<>(lootLocations);
        Collections.shuffle(locations);
        onStart = locations
                .stream()
                .limit((int) randomLootLocations)
                .collect(Collectors.toList());
    }

    public void generateChestOnStart(Location location) {
        if (onStart.contains(location.getId())) {
            location.setChests(generateLoot());
        }
    }

    public void generateChestOnRuntime(Hero hero, Location location) {
        if (lootLocations.contains(location.getId())) {
            location.getChests().add(RuntimeItemGenerationService.getService().generateWeapon(hero));
        }
    }
}
