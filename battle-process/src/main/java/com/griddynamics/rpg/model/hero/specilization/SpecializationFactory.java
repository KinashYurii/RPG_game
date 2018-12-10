package com.griddynamics.rpg.model.hero.specilization;

import java.util.Map;
import java.util.function.Supplier;

public class SpecializationFactory {
    private final Map<SpecializationType, Supplier<Specialization>> typeSpecializationMap = Map.of(
            SpecializationType.ARCHER, this::createArcherSpecialization,
            SpecializationType.ROGUE, this::createRogueSpecialization,
            SpecializationType.SORCERER, this::createSorcererSpecialization,
            SpecializationType.SWORDSMAN, this::createSwordsmanSpecialization,
            SpecializationType.WARRIOR, this::createWarriorSpecialization
    );

    private Specialization createWarriorSpecialization() {
        return new Warrior();
    }

    private Specialization createSwordsmanSpecialization() {
        return new Swordsman();
    }

    private Specialization createSorcererSpecialization() {
        return new Sorcerer();
    }

    private Specialization createRogueSpecialization() {
        return new Rogue();
    }

    private Specialization createArcherSpecialization() {
        return new Archer();
    }

    public Specialization getSpecializationInstance(SpecializationType specializationType) {
        return typeSpecializationMap.get(specializationType).get();
    }
}
