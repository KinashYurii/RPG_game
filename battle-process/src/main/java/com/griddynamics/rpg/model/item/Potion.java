package com.griddynamics.rpg.model.item;

import com.griddynamics.rpg.model.backpack.BackpackPlacable;
import com.griddynamics.rpg.model.backpack.BackpackPlace;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.service.hero.StatsService;
import lombok.Getter;

public class Potion implements BackpackPlacable {

    @Getter
    private double health;

    public Potion() {
        this.health = 50;
    }

    public void applyPotion(Hero hero) {
        StatsService.getInstance().applyHealthPotion(hero, health);
    }

    @Override
    public BackpackPlace getBackpackPlace() {
        return BackpackPlace.PLACE1x1;
    }

    @Override
    public String toString() {
        return "Health Potion" +
                " (restore health=" + health +
                ')';
    }
}
