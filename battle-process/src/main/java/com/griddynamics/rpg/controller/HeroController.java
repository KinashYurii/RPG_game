package com.griddynamics.rpg.controller;

import com.griddynamics.rpg.exception.GameOverException;
import com.griddynamics.rpg.model.backpack.Backpack;
import com.griddynamics.rpg.model.hero.*;
import com.griddynamics.rpg.model.hero.race.Race;
import com.griddynamics.rpg.model.hero.race.RaceFactory;
import com.griddynamics.rpg.model.hero.race.RaceType;
import com.griddynamics.rpg.model.hero.specilization.Specialization;
import com.griddynamics.rpg.model.hero.specilization.SpecializationFactory;
import com.griddynamics.rpg.model.hero.specilization.SpecializationType;
import com.griddynamics.rpg.service.backpack.BackpackService;
import com.griddynamics.rpg.service.hero.HeroService;
import com.griddynamics.rpg.service.hero.StatsService;
import com.griddynamics.rpg.util.ConsoleReader;
import com.griddynamics.rpg.util.constants.HeroDataConstants;
import com.griddynamics.rpg.util.constants.SpecializationConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;

public class HeroController {
    private final HeroService heroService = HeroService.getInstance();
    private final BackpackService backpackService;
    private final SpecializationFactory specializationFactory;
    private final RaceFactory raceFactory;
    private final ConsoleReader reader;
    private final Map<Integer, Runnable> heroDialogMap = Map.of(
            0, () -> {
                throw new GameOverException(GAME_OVER);
            },
            1, () -> System.out.println(HERO_SAVED_MSG),
            2, this::resetAll,
            3, this::resetAttributesDialog
    );
    private ItemSlots itemSlots = new ItemSlots();
    private Stats stats = new Stats();
    private Attributes attributes = new Attributes();
    private Hero hero;
    private String name;
    private Race race;
    private Specialization specialization;

    public HeroController() {
        this.backpackService = new BackpackService();
        this.specializationFactory = new SpecializationFactory();
        this.raceFactory = new RaceFactory();
        this.reader = new ConsoleReader();
    }

    public void createHeroDialog() {
        setName();
        setRace();
        setSpecialization();
        createHero();
        setAttributes(hero);
        StatsService.getInstance().calculateAllStats(hero);
        saveHeroDialog();
    }

    /**
     * reset Stats dialog where player
     * can put points to attributes again
     */
    private void resetAttributesDialog() {
        HeroDataConstants.ATTRIBUTES_SERVICE.resetAttributes(hero);
        heroService.resetPoints(hero);
        setAttributes(hero);
        saveHeroDialog();
    }

    private void setName() {
        System.out.println(HERO_NAME_MSG);
        name = reader.readWord();
        while (name.isBlank()) {
            System.out.println(HERO_BLANK_MSG);
            name = reader.readWord();
        }
    }

    private void setRace() {
        System.out.println(CHOSE_RACE_MSG);
        Map<Integer, RaceType> racesMap = convertListToNumberedMap(Arrays.asList(RaceType.values()));
        printNumberedMap(racesMap);
        Integer inputAccordingToRace = reader.readNumberFrom(racesMap.keySet());
        race = raceFactory.getRace(racesMap.get(inputAccordingToRace));
        race.applyRacialBonuses(attributes);
        race.applyRacialPenalties(attributes);
    }

    private void setSpecialization() {
        System.out.println(CHOSE_SPEC_MSG);
        Map<Integer, SpecializationType> specializationMap = convertListToNumberedMap(isSpecializationAvailable());
        printNumberedMap(specializationMap);
        Integer consoleInput = reader.readNumberFrom(specializationMap.keySet());
        chooseSpecialization(consoleInput, specializationMap);
    }

    private void chooseSpecialization(Integer choice, Map<Integer, SpecializationType> specializationMap) {
        SpecializationType chosenSpecialization = specializationMap.get(choice);
        specialization = specializationFactory.getSpecializationInstance(chosenSpecialization);
        specialization.applySpecializationBonuses(attributes, itemSlots);
        specialization.applySpecializationPenalties(attributes);
    }

    private List<SpecializationType> isSpecializationAvailable() {
        return SpecializationConstants.SPECIALIZATION_REQUIREMENTS.entrySet()
                .stream()
                .filter(x -> x.getValue().test(attributes))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private <T> void printNumberedMap(Map<Integer, T> inputMap) {
        inputMap.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    private <T> Map<Integer, T> convertListToNumberedMap(List<T> list) {
        Map<Integer, T> numberedMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            numberedMap.put(i + 1, list.get(i));
        }
        return numberedMap;
    }

    private void createHero() {
        Backpack backpack = backpackService.getBackpack();
        hero = new Hero(name, race, specialization, attributes, stats, itemSlots, backpack);
    }

    public void setAttributes(Hero hero) {
        System.out.println(SPEND_POINTS_MSG);
        Map<AttributesType, Double> attributesCostMap = hero.getRace().getAttributesCostMap();
        Level level = hero.getLevel();
        double minAttributeCost = attributesCostMap.entrySet().stream()
                .mapToDouble(Map.Entry::getValue)
                .min()
                .orElseThrow();
        while (level.getSkillPoints() >= minAttributeCost) {
            System.out.println("Your points: \033[31;1m" + level.getSkillPoints() + "\033[0m");
            System.out.println(hero.getAllAttributes());
            Integer key = reader.readNumberFrom(HeroDataConstants.ADD_ATTRIBUTE_ACTION_MAP.keySet());
            HeroDataConstants.ADD_ATTRIBUTE_ACTION_MAP.get(key).accept(hero);
            heroService.applyItemBonuses(hero);
        }
        StatsService.getInstance().calculateAllStats(hero);
    }

    private void saveHeroDialog() {
        specialization.starterPack(hero);
        System.out.println(hero);
        System.out.println(HERO_SAVE_MSG);
        System.out.println(DIALOG_MSG);
        Integer choice = reader.readNumberFrom(heroDialogMap.keySet());
        heroDialogMap.get(choice).run();
    }

    private void resetAll() {
        attributes = new Attributes();
        itemSlots = new ItemSlots();
        stats = new Stats();
        createHeroDialog();
    }

    public Hero returnHero() {
        return hero;
    }
}

