package com.griddynamics.rpg.controller;

import com.griddynamics.rpg.exception.CantAddToBackpackException;
import com.griddynamics.rpg.exception.GameOverException;
import com.griddynamics.rpg.model.arena.Location;
import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.battle.Attackable;
import com.griddynamics.rpg.model.battle.BattleAttributes;
import com.griddynamics.rpg.model.battle.BattleResult;
import com.griddynamics.rpg.model.chest.Chest;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.hero.Stats;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.model.npc.Npc;
import com.griddynamics.rpg.service.arena.LocationService;
import com.griddynamics.rpg.service.arena.NpcDisturbService;
import com.griddynamics.rpg.service.arena.SearchWayService;
import com.griddynamics.rpg.service.backpack.BackpackActionService;
import com.griddynamics.rpg.service.backpack.BackpackService;
import com.griddynamics.rpg.service.battle.BattleProcessService;
import com.griddynamics.rpg.service.chest.ChestGenerateService;
import com.griddynamics.rpg.service.hero.StatsService;
import com.griddynamics.rpg.service.level.BattleExperienceService;
import com.griddynamics.rpg.service.npc.NpcService;
import com.griddynamics.rpg.service.roll.DiceService;
import com.griddynamics.rpg.service.roll.PseudoRoll;
import com.griddynamics.rpg.util.ConsoleReader;
import com.griddynamics.rpg.util.constants.LocationConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

public class LocationController {

    private static final int DEFAULT_INTELLIGENCE_POINTS_FOR_CHEST = 15;
    private static final int MAX_INTELLECT_POINT_FOR_RANGE = 30;

    private final ConsoleReader reader = new ConsoleReader();
    private final BackpackService backpackService = new BackpackService();
    private final LocationService locationService = new LocationService();
    private final BattleExperienceService experienceService = new BattleExperienceService();
    private final PseudoRoll reGenerateNpc = new PseudoRoll(0.2);
    private final SearchWayService searchWayService = new SearchWayService();
    private final BackpackActionService backpackActionService = BackpackActionService.getService();
    private final Map<Integer, BiConsumer<Hero, Location>> backpackActionsMap = Map.of(
            1, (hero, location) -> backpackActionService.printItemsInBackpack(hero),
            2, (hero, location) -> backpackActionService.printFreeSpaceInBackpack(hero),
            3, (hero, location) -> backpackActionService.equipItemFromBackpack(hero),
            4, (hero, location) -> backpackActionService.removeItemFromBackpack(hero),
            0, this::generalDialog
    );
    private final Map<String, BiConsumer<Hero, Location>> prepareForBattleDialog = Map.of(
            "1", this::backpackActions,
            "2", (hero, location) -> System.out.println(hero.getItemSlots()),
            "3", this::prepareBattle
    );
    private final Map<String, BiConsumer<Hero, Location>> generalDialog = Map.of(
            "1", this::backpackActions,
            "2", this::tryLootChests,
            "4", this::prepareForBattle
    );

    public void launch(Hero hero) {
        Location start = locationService.generateRandomLocation(GRAPH_LENGTH);
        startNavigating(start, hero);
    }

    private void startNavigating(Location location, Hero hero) {
        while (!location.isFinish() && hero.alive()) {
            showLocationInfo(location);
            showNpcStatRangeIfNpcPresent(hero, location);
            location = navigationLogic(hero, location);
        }
    }

    private Location chooseNext(Map<Integer, Location> locationWithIndex, Location current) {
        System.out.println(USE_HINT_MSG);
        int input = reader.readNumberInSetOrZero(locationWithIndex.keySet());
        if (input == 0) {
            showHint(current);
            input = reader.readNumberFrom(locationWithIndex.keySet());
        }
        return locationWithIndex.get(input);
    }

    private void showHint(Location current) {
        Location hint = searchWayService.getWay(current, GRAPH_LENGTH);
        System.out.println(HINT + hint.getName());
    }

    private Map<Integer, Location> printAndGetPossibleWays(Location location) {
        Map<Integer, Location> result = new HashMap<>();
        int counter = 1;
        for (Location way : location.getPossibleWays()) {
            System.out.printf(CHOOSE_LOCATION, counter, way.getName());
            result.put(counter++, way);
        }
        return result;
    }

    private Map<Integer, Location> printBackWay(Location location) {
        Map<Integer, Location> result = new HashMap<>();
        int counter = 1;
        Location backLocation = location.getPossibleWays().get(1);
        System.out.printf(CHOOSE_LOCATION, counter, backLocation.getName());
        result.put(counter, backLocation);
        return result;
    }

    private void generalDialog(Hero hero, Location location) {
        while (true) {
            System.out.println(NAVIGATION_MSG);
            if (location.getNpc() != null && location.getNpc().getNpcStats().getHealth() > 0) {
                System.out.println(PREPARE);
            }
            var choice = reader.readWord();
            if (generalDialog.containsKey(choice)) {
                generalDialog.get(choice).accept(hero, location);
            } else if (choice.equals("3")) {
                return;
            } else {
                System.out.println(WRONG);
            }
        }
    }

    private Location backActionDialog(Hero hero, Location location) {
        while (true) {
            System.out.println(GO_BACK);
            if (location.getNpc() != null)
                System.out.println(PREPARE);
            var choice = reader.readWord();
            if (choice.equals("1")) {
                return backActionDialog(hero, location);
            }
            if (choice.equals("2")) {
                prepareForBattle(hero, location);
                return showNavigationPanel(location);
            } else {
                System.out.println(WRONG);
            }
        }
    }

    private void prepareForBattle(Hero hero, Location location) {
        if (location.getNpc() != null && location.getNpc().getNpcStats().getHealth() > 0) {
            while (true) {
                System.out.println(PREPARE);
                System.out.println(OPEN_BACKPACK);
                System.out.println(SHOW_EQUIPMENT);
                System.out.println(FIGHT);
                var choice = reader.readWord();
                if (prepareForBattleDialog.containsKey(choice)) {
                    prepareForBattleDialog.get(choice).accept(hero, location);
                    prepareForBattle(hero, location);
                    return;
                } else {
                    System.out.println(WRONG);
                }
            }
        } else {
            System.out.println(NO_NPC);
        }
    }

    private void showLocationInfo(Location location) {
        String locationName = location.getName();
        System.out.println("Current location: \033[34;1m" + locationName + " \033[0m");
        System.out.println(DESCRIPTION + location.getDescription());
    }

    private void backpackActions(Hero hero, Location location) {
        while (true) {
            System.out.println(MENU);
            Integer choice = reader.readNumberFrom(backpackActionsMap.keySet());
            if (choice.equals(0)) {
                generalDialog(hero, location);
                return;
            }
            backpackActionsMap.get(choice).accept(hero, location);
        }
    }

    private Location showNavigationPanel(Location location) {
        Map<Integer, Location> locationWithIndex = printAndGetPossibleWays(location);
        return chooseNext(locationWithIndex, location);
    }

    private void confirmNextLocation(Hero hero, Location location) {
        decideToGenerateNpc(hero, location);
        if (!location.isVisited()) {
            location.setVisited(true);
        }
        ChestGenerateService.getService().generateChestOnRuntime(hero, location);
    }

    private void npcDisturb(Hero hero, Location location) {
        if (location.getNpc() != null) {
            NpcDisturbService.getInstance().npcDisturb(location.getNpc(), hero);
        }
    }

    private Location showBackNavigationPanel(Location location) {
        Map<Integer, Location> locationWithIndex = printBackWay(location);
        return chooseNext(locationWithIndex, location);
    }

    private Location navigationLogic(Hero hero, Location location) {
        Location newLocation;
        boolean preparedResult = rollPreparation(hero, location);
        hero.setPrepared(preparedResult);
        npcDisturb(hero, location);
        if (preparedResult && hero.getSpecialization().getSpecializationType().equals(SpecializationType.ROGUE)) {
            System.out.println(LocationConstants.INVISIBLE);
            generalDialog(hero, location);
            newLocation = showNavigationPanel(location);
        } else if (preparedResult && hero.getRace().getRaceType().equals(RaceType.ELF)
                && hero.getAttributes().getPreparedBound() >= 4) {
            System.out.println(LocationConstants.CAN_GO_PREVIOUS);
            if (location.getNpc().getNpcStats().getHealth() <= 0) {
                newLocation = backActionDialog(hero, location);
            } else {
                newLocation = showBackNavigationPanel(location);
            }
        } else {
            prepareForBattle(hero, location);
            generalDialog(hero, location);
            newLocation = showNavigationPanel(location);
        }
        confirmNextLocation(hero, newLocation);
        return newLocation;
    }

    private boolean rollPreparation(Hero hero, Location location) {
        boolean preparedResult = false;
        int preparedBound = hero.getAttributes().getPreparedBound();
        if (location.getNpc() != null && !location.getNpc().isPrepared() && preparedBound >= 4) {
            preparedResult = DiceService.getInstance().preparedRoll(preparedBound);
        }
        return preparedResult;
    }

    private void prepareBattle(Hero hero, Location location) {
        Npc npc = location.getNpc();
        if (npc != null && npc.getNpcStats().getHealth() > 0) {
            BattleProcessService battleProcessService = new BattleProcessService();
            BattleResult battleResult = battleProcessService.launchBattle(hero, npc);
            if (battleResult.isNpcDied()) {
                System.out.println(npc.getName() + KILLED + hero.getName());
                experienceService.updateExperienceByBattleResult(hero, npc, battleResult.getNpcHits());
                StatsService.getInstance().regenerateHealthPerLocation(hero);
                Stats stats = hero.getStats();
                stats.setMana(stats.getMaxMana());
                stats.setRage(stats.getMaxRage());
            }
            if (battleResult.isHeroDied()) {
                throw new GameOverException(YOU_DIE);
            }
        } else {
            System.out.println(LocationConstants.NO_NPC_IN_LOCATION);
        }
    }

    private void decideToGenerateNpc(Hero hero, Location location) {
        if (location.isVisited() && location.getId() != 0 && reGenerateNpc.areYouLucky()) {
            Npc npc = NpcService.getInstance().generate(hero);
            location.setNpc(npc);
        } else {
            Npc npc = NpcService.getInstance().generate(hero);
            location.setNpc(npc);
        }
    }

    private double getAttributeRange(int intellect) {
        return intellect > MAX_INTELLECT_POINT_FOR_RANGE ? 0 : 1.0 / intellect;
    }

    private void showHeroStats(Attackable npc, double range) {
        BattleAttributes heroAttributes = npc.getBattleAttributes();
        double health = heroAttributes.getStats().getHealth();
        double mana = heroAttributes.getStats().getMana();
        double rage = heroAttributes.getStats().getRage();
        double hpRange = health * range;
        double manaRange = mana * range;
        double rageRange = rage * range;
        String roundInfo = String.format(npc.getName() + STAT_INFO,
                health - hpRange, health + hpRange,
                mana - manaRange, mana + manaRange,
                rage - rageRange, rage + rageRange);
        System.out.println(roundInfo);
    }

    private void showNpcStatRangeIfNpcPresent(Hero hero, Location location) {
        Attackable npc = location.getNpc();
        if (npc != null) {
            double attributeRange = getAttributeRange(hero.getBattleAttributes().getIntellect());
            showHeroStats(npc, attributeRange);
        }
    }

    private void tryLootChests(Hero hero, Location location) {
        if (location.getChests() != null && location.getNpc() != null && location.getNpc().getNpcStats().getHealth() > 0
                && !hero.getSpecialization().getSpecializationType().equals(SpecializationType.ROGUE) && !hero.isPrepared()
        ) {
            System.out.println(KILL_NPC);
        } else if (location.getChests() == null || hero.getAttributes().getIntelligence() < DEFAULT_INTELLIGENCE_POINTS_FOR_CHEST) {
            System.out.println(NO_CHEST);
        } else {
            chestActions(hero, location);
        }
    }

    private void chestActions(Hero hero, Location location) {
        int freeSlots = hero.getBackpack().getFreeSlots();
        System.out.println(FREE_SLOTS + freeSlots);
        List<Chest> chests = location.getChests();
        if (chests.size() > 1 && hero.getAttributes().getIntelligence() < DEFAULT_INTELLIGENCE_POINTS_FOR_CHEST * 2) {
            System.out.println("Can be more chest, but im so stupid... ");
            chests.remove(1);
            getChestsFromLocation(chests, hero.getBackpack());
        } else {
            getChestsFromLocation(chests, hero.getBackpack());
        }
    }

    private void getChestsFromLocation(List<Chest> chests, Backpack backpack) {
        while (!chests.isEmpty()) {
            System.out.print(ADD_ITEM + reader.listToString(chests));
            int chestIndex = reader.readNumberInRange(chests.size()) - INDEX;
            if (chestIndex == -1) return;
            Chest chest = chests.get(chestIndex);
            getItemsFromChest(backpack, chest);
            chests.remove(chest);
        }
    }

    private void getItemsFromChest(Backpack backpack, Chest chest) {
        while (!chest.getItems().isEmpty()) {
            System.out.print(CHOSE_ITEM + reader.listToString(chest.getItems()));
            int itemIndex = reader.readNumberInRange(chest.getItems().size()) - INDEX;
            if (itemIndex == -1) return;
            BackpackPlacable item = chest.getItems().get(itemIndex);
            try {
                backpackService.addToBackpack(backpack, item);
                chest.getItems().remove(item);
                System.out.println("You added " + item + " to backpack");
            } catch (CantAddToBackpackException e) {
                System.out.println(CANT_ADD + item);
            }
        }
    }
}
