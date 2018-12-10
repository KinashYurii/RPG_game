package com.griddynamics.rpg.model.hero.race;

import java.util.Map;
import java.util.function.Supplier;

/**
 * factory for race creation
 */
public class RaceFactory {
    private final Map<RaceType, Supplier<Race>> typeSpecializationMap = Map.of(
            RaceType.HUMAN, this::createHumanRace,
            RaceType.ORC, this::createOrcRace,
            RaceType.DWARF, this::createDwarfRace,
            RaceType.ELF, this::createElfRace
    );
    /**
     * Getting a method from map to creating new race
     * @param race race name
     * @return specified race
     */
    public Race getRace(RaceType race) {
        return typeSpecializationMap.get(race).get();
    }

    private Race createHumanRace() {
        return new Human();
    }

    private Race createOrcRace() {
        return new Orc();
    }

    private Race createDwarfRace() {
        return new Dwarf();
    }

    private Race createElfRace() {
        return new Elf();
    }
}