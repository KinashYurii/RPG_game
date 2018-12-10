package com.griddynamics.rpg.service.hero;

import com.griddynamics.rpg.model.hero.Attributes;
import com.griddynamics.rpg.model.hero.AttributesType;
import com.griddynamics.rpg.model.hero.Hero;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_CHARACTERISTIC;
import static com.griddynamics.rpg.util.constants.UserInfoConstants.NO_POINTS;

public class AttributesService {
    private static AttributesService attributesService;

    public static AttributesService getInstance() {
        if (attributesService == null) {
            attributesService = new AttributesService();
        }
        return attributesService;
    }

    private final Map<AttributesType, Function<Attributes, Integer>> getMap = Map.of(
            AttributesType.WILLPOWER, Attributes::getWillpower,
            AttributesType.AGILITY, Attributes::getAgility,
            AttributesType.CHARISMA, Attributes::getCharisma,
            AttributesType.DEXTERITY, Attributes::getDexterity,
            AttributesType.STAMINA, Attributes::getStamina,
            AttributesType.STRENGTH, Attributes::getStrength,
            AttributesType.INTELLIGENCE, Attributes::getIntelligence
    );

    private final Map<AttributesType, BiConsumer<Attributes, Integer>> setMap = Map.of(
            AttributesType.WILLPOWER, Attributes::setWillpower,
            AttributesType.AGILITY, Attributes::setAgility,
            AttributesType.CHARISMA, Attributes::setCharisma,
            AttributesType.DEXTERITY, Attributes::setDexterity,
            AttributesType.STAMINA, Attributes::setStamina,
            AttributesType.STRENGTH, Attributes::setStrength,
            AttributesType.INTELLIGENCE, Attributes::setIntelligence
    );

    public void applyAttribute(Hero hero, AttributesType attribute, int value) {
        int currentValue = getMap.get(attribute).apply(hero.getAttributes());
        currentValue += value;
        setMap.get(attribute).accept(hero.getAttributes(), currentValue);
    }

    public void discardAttribute(Hero hero, AttributesType attribute, int value) {
        int currentValue = getMap.get(attribute).apply(hero.getAttributes());
        if (value <= currentValue) {
            currentValue -= value;
            setMap.get(attribute).accept(hero.getAttributes(), currentValue);
        } else {
            setMap.get(attribute).accept(hero.getAttributes(), 0);
        }
    }

    public void addAttributePoint(Hero hero, AttributesType attribute) {
        int skillPoints = hero.getLevel().getSkillPoints();
        if (skillPoints >= hero.getRace().getRaceAttributeCost(attribute)) {
            int currentValue = getMap.get(attribute).apply(hero.getAttributes());
            currentValue += 1;
            setMap.get(attribute).accept(hero.getAttributes(), currentValue);
            hero.getLevel().setSkillPoints(skillPoints - (int) hero.getRace().getRaceAttributeCost(attribute));
        } else {
            System.out.println(NO_POINTS);
        }
    }

    public void resetAttributes(Hero hero) {
        Attributes attributes = hero.getAttributes();
        attributes.setWillpower(DEFAULT_CHARACTERISTIC);
        attributes.setAgility(DEFAULT_CHARACTERISTIC);
        attributes.setDexterity(DEFAULT_CHARACTERISTIC);
        attributes.setStrength(DEFAULT_CHARACTERISTIC);
        attributes.setIntelligence(DEFAULT_CHARACTERISTIC);
        attributes.setCharisma(DEFAULT_CHARACTERISTIC);
        attributes.setStamina(DEFAULT_CHARACTERISTIC);
        hero.getRace().applyRacialBonuses(attributes);
        hero.getRace().applyRacialPenalties(attributes);
        hero.getSpecialization().applySpecializationBonuses(attributes, hero.getItemSlots());
        hero.getSpecialization().applySpecializationPenalties(attributes);
    }
}