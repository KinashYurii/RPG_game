package com.griddynamics.rpg;


import com.griddynamics.rpg.controller.HeroController;
import com.griddynamics.rpg.controller.LocationController;
import com.griddynamics.rpg.exception.GameOverException;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.service.roll.DiceService;

class App {
    public static void main(String[] args) {
        LocationController locationController = new LocationController();
        HeroController heroController = new HeroController();
        try {
            heroController.createHeroDialog();
            Hero hero = heroController.returnHero();
            locationController.launch(hero);
        } catch (GameOverException e) {
            System.out.println(e.getMessage());
        } finally {
            DiceService.getInstance().shutdownPool();
        }
    }
}