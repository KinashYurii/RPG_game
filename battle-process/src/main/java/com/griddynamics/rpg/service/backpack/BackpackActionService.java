package com.griddynamics.rpg.service.backpack;

import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.item.Potion;
import com.griddynamics.rpg.model.item.armor.accessory.Accessory;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.service.hero.HeroService;
import com.griddynamics.rpg.service.hero.StatsService;
import com.griddynamics.rpg.util.ConsoleReader;
import com.griddynamics.rpg.util.ConsoleWriter;
import com.griddynamics.rpg.util.MapBuilder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

@Getter
public final class BackpackActionService {
    private static BackpackActionService chestActionService;
    private final HeroService heroService = HeroService.getInstance();
    private final BackpackService backpackService = new BackpackService();
    private final ConsoleReader reader = new ConsoleReader();
    private final ConsoleWriter writer = new ConsoleWriter();

    private BackpackActionService() {
    }

    public static BackpackActionService getService() {
        if (chestActionService == null) {
            chestActionService = new BackpackActionService();
        }
        return chestActionService;
    }

    public void equipItemFromBackpack(Hero hero) {
        Backpack backpack = hero.getBackpack();
        if (backpack.getItems().size() > 0) {
            List<BackpackPlacable> items = backpack.getItems();
            BackpackPlacable item = chooseItem(items);
            boolean equipped = equip(hero, item);
            if (equipped) {
                tryRebuildBackpackGrid(backpack, item);
                System.out.println(ITEM_EQUIPPED_MSG);
            } else {
                System.out.println(ITEM_NOT_EQUIPPED_MSG);
            }
        } else {
            System.out.println(NO_ITEMS);
        }
    }

    public void removeItemFromBackpack(Hero hero) {
        Backpack backpack = hero.getBackpack();
        if (backpack.getItems().size() > 0) {
            List<BackpackPlacable> items = backpack.getItems();
            BackpackPlacable item = chooseItem(items);
            tryRebuildBackpackGrid(backpack, item);
            System.out.println(ITEM_REMOVED_MSG);
        } else {
            System.out.println(NO_ITEMS);
        }
    }

    private void tryRebuildBackpackGrid(Backpack backpack, BackpackPlacable item) {
        backpackService.removeFromBackpack(backpack, item);
    }

    private BackpackPlacable chooseItem(List<BackpackPlacable> items) {
        Map<Integer, BackpackPlacable> itemsWithIndexes = MapBuilder.mapWithIndexes(items);
        writer.writeMap(itemsWithIndexes);
        int index = reader.readNumberFrom(itemsWithIndexes.keySet());
        return itemsWithIndexes.get(index);
    }

    private boolean equip(Hero hero, BackpackPlacable item) {
        if (item instanceof Head) {
            return heroService.equip(hero, (Head) item);
        } else if (item instanceof Weapon) {
            return heroService.equip(hero, (Weapon) item);
        } else if (item instanceof Accessory) {
            return heroService.equip(hero, (Accessory) item);
        } else if (item instanceof ChestArmor) {
            return heroService.equip(hero, (ChestArmor) item);
        } else if (item instanceof Potion) {
            Potion potion = (Potion) item;
            return StatsService.getInstance().applyHealthPotion(hero, potion.getHealth());
        } else {
            return false;
        }
    }

    public void printItemsInBackpack(Hero hero) {
        System.out.println(hero.getBackpack().getItems());
    }

    public void printFreeSpaceInBackpack(Hero hero) {
        System.out.println(hero.getBackpack().getFreeSlots());
    }
}
