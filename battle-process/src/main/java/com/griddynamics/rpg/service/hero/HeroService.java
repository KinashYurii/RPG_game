package com.griddynamics.rpg.service.hero;

import com.griddynamics.rpg.exception.CantAddToBackpackException;
import com.griddynamics.rpg.model.hero.Hero;
import com.griddynamics.rpg.model.item.armor.accessory.Accessory;
import com.griddynamics.rpg.model.item.armor.chest.ChestArmor;
import com.griddynamics.rpg.model.item.armor.head.Head;
import com.griddynamics.rpg.model.item.weapon.Weapon;
import com.griddynamics.rpg.service.backpack.BackpackService;

import static com.griddynamics.rpg.util.constants.HeroDataConstants.DEFAULT_POINTS_QUANTITY;
import static com.griddynamics.rpg.util.constants.UserInfoConstants.*;
import static com.griddynamics.rpg.util.validators.BattleValidator.*;
import static com.griddynamics.rpg.util.validators.EquipValidator.*;
import static java.util.Objects.nonNull;

public class HeroService {
    private static HeroService heroService;
    private BackpackService backpackService = new BackpackService();

    private HeroService() {
    }

    public static HeroService getInstance() {
        if (heroService == null) {
            heroService = new HeroService();
        }
        return heroService;
    }

    public void setPrepared(Hero hero) {
        if (isElf(hero)) {
            hero.setPrepared(RollElfPreparedLuck());
        } else if (isRogue(hero)) {
            hero.setPrepared(RollRoguePrepared());
        } else hero.setPrepared(false);
    }

    public boolean equip(Hero hero, Weapon weapon) {
        if (isEquippableWeapon(hero, weapon) && isHeroOneHandWeapon(hero)) {
            return equipOneHand(hero, weapon);
        } else {
            return equipTwoHand(hero, weapon);
        }
    }

    private boolean equipOneHand(Hero hero, Weapon weapon) {
        if (isHeroLeftHandEmpty(hero)) {
            unEquipLeftHand(hero);
            hero.getItemSlots().setLeftHand(weapon);
            weapon.applyItemBonuses(hero);
            return true;
        } else if (isHeroRightHandEmpty(hero)) {
            unEquipRightHand(hero);
            hero.getItemSlots().setRightHand(weapon);
            weapon.applyItemBonuses(hero);
            return true;
        } else {
            System.out.println(DROP_ITEM_MSG);
            return false;
        }
    }

    public void applyItemBonuses(Hero hero) {
        if (hero.getItemSlots().getHead() != null) {
            hero.getItemSlots().getHead().applyStats(hero);
        }
        if (hero.getItemSlots().getChestArmor() != null) {
            hero.getItemSlots().getChestArmor().applyStats(hero);
        }
    }

    private boolean equipTwoHand(Hero hero, Weapon weapon) {
        if (isHeroHandsFree(hero)) {
            unEquipTwoHand(hero);
            hero.getItemSlots().setRightHand(weapon);
            hero.getItemSlots().setLeftHand(weapon);
            weapon.applyItemBonuses(hero);
            return true;
        } else {
            System.out.println(CANT_EQUIP);
            return false;
        }
    }

    public boolean equip(Hero hero, ChestArmor chestArmor) {
        if (isEquippableArmor(hero, chestArmor)) {
            unEquipChest(hero);
            hero.getItemSlots().setChestArmor(chestArmor);
            chestArmor.applyItemBonuses(hero);
            return true;
        } else {
            System.out.println(CANT_EQUIP);
            return false;
        }
    }

    public boolean equip(Hero hero, Head head) {
        if (isEquippableArmor(hero, head)) {
            unEquipHead(hero);
            hero.getItemSlots().setHead(head);
            head.applyItemBonuses(hero);
            return true;
        } else {
            System.out.println(CANT_EQUIP);
            return false;
        }
    }

    public boolean equip(Hero hero, Accessory accessory) {
        if (isEquippableAccessory(hero)) {
            hero.getItemSlots().setAccessory1(accessory);
            accessory.applyItemBonuses(hero);
            return true;
        } else if (isEquippableAccessory(hero)) {
            hero.getItemSlots().setAccessory2(accessory);
            accessory.applyItemBonuses(hero);
            return true;
        } else {
            System.out.println(NOT_SORCERER);
            return false;
        }
    }


    public void unEquipHead(Hero hero) {
        if (nonNull(hero.getItemSlots().getHead())) {
            hero.getItemSlots().getHead().discardItemBonuses(hero);
            try {
                backpackService.addToBackpack(hero.getBackpack(), hero.getItemSlots().getHead());
            } catch (CantAddToBackpackException e) {
                e.printStackTrace();
            }
            hero.getItemSlots().setHead(null);
        }
    }


    public void unEquipLeftHand(Hero hero) {
        if (nonNull(hero.getItemSlots().getLeftHand())) {
            hero.getItemSlots().getLeftHand().discardItemBonuses(hero);
            try {
                backpackService.addToBackpack(hero.getBackpack(), hero.getItemSlots().getLeftHand());
            } catch (CantAddToBackpackException e) {
                e.printStackTrace();
            }
            hero.getItemSlots().setLeftHand(null);
        }
    }


    public void unEquipRightHand(Hero hero) {
        if (nonNull(hero.getItemSlots().getRightHand())) {
            hero.getItemSlots().getRightHand().discardItemBonuses(hero);
            try {
                backpackService.addToBackpack(hero.getBackpack(), hero.getItemSlots().getRightHand());
            } catch (CantAddToBackpackException e) {
                e.printStackTrace();
            }
            hero.getItemSlots().setRightHand(null);
        }
    }


    public void unEquipTwoHand(Hero hero) {
        if (nonNull(hero.getItemSlots().getRightHand()) && nonNull(hero.getItemSlots().getLeftHand())) {
            hero.getItemSlots().getRightHand().discardItemBonuses(hero);
            try {
                backpackService.addToBackpack(hero.getBackpack(), hero.getItemSlots().getRightHand());
                backpackService.addToBackpack(hero.getBackpack(), hero.getItemSlots().getLeftHand());
            } catch (CantAddToBackpackException e) {
                e.printStackTrace();
            }
            hero.getItemSlots().setRightHand(null);
            hero.getItemSlots().setLeftHand(null);
        }
    }

    public void resetPoints(Hero hero) {
        hero.getLevel().setSkillPoints(DEFAULT_POINTS_QUANTITY);
    }


    public void unEquipChest(Hero hero) {
        if (nonNull(hero.getItemSlots().getChestArmor())) {
            hero.getItemSlots().getChestArmor().discardItemBonuses(hero);
            try {
                backpackService.addToBackpack(hero.getBackpack(), hero.getItemSlots().getChestArmor());
            } catch (CantAddToBackpackException e) {
                e.printStackTrace();
            }
            hero.getItemSlots().setChestArmor(null);
        }
    }
}


