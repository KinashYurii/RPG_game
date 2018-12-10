package com.griddynamics.rpg.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserInfoConstants {
    public static final String ITEM_REMOVED_MSG = "Item removed";
    public static final String HERO_SAVED_MSG = "Hero saved.";
    public static final String HERO_NAME_MSG = "Give name for your hero:";
    public static final String HERO_BLANK_MSG = "Name cant be blank";
    public static final String CHOSE_RACE_MSG = "Choose your race:";
    public static final String CHOSE_SPEC_MSG = "Please type number of specialization according to race from the list: ";
    public static final String HERO_SAVE_MSG = "Do you agree and want to save this Hero?";
    public static final String DIALOG_MSG = "1: save hero, 2: recreate hero, 3: reset Attributes, 0 - exit";
    public static final String SPEND_POINTS_MSG = "===Now you must spend your points to attributes===\n" +
            "Choose attribute to add one point";
    public static final int GRAPH_LENGTH = 10;
    public static final String CHOOSE_LOCATION = "%d - to go %s%n";
    public static final String ITEM_EQUIPPED_MSG = "Item equipped";
    public static final String ITEM_NOT_EQUIPPED_MSG = "Item not equipped";
    public static final String USE_HINT_MSG = "0 - to use hint";
    public static final String LOOT_CHEST = "To loot chest -> ";
    public static final String GAME_OVER = "Game over!";
    public static final String HINT = "Hint: ";
    public static final String WRONG = "wrong input";
    public static final String NO_NPC = "There are no npc in location to fight";
    public static final String DESCRIPTION = "Description: ";
    public static final String OPEN_BACKPACK = "1: Open Backpack";
    public static final String SHOW_EQUIPMENT = "2: Show hero's equipment \033[31;1m";
    public static final String FIGHT = "3: FIGHT  \u001B[0m";
    public static final String FREE_SLOTS = "Backpack free slots: ";
    public static final String ADD_ITEM = "Location has next chests\n0: Go back\n";
    public static final String CANT_ADD = "You cant pick ";
    public static final String YOU_DIE = "YOU ARE DIED";
    public static final String KILL_NPC = "\033[33;1mNPC is defending chest, you need to kill him\033[0m";
    public static final String NO_CHEST = "\033[33;1mI searched but i didnt found any chets in location. Maybe if i will" +
            " hame more inteligince...  \033[0m";
    public static final String KILLED = " was killed by ";
    public static final String GO_BACK = "0: Go back";
    public static final String MENU = "1: Show items in backpack \n2: Show free slots in backpack " +
            "\n3: Equip item from backpack \n4: Remove item from backpack \n0: Return to general menu";
    public static final String PREPARE = "\033[31;1mPREPARE FOR BATTLE\u001B[0m";
    public static final String NAVIGATION_MSG = "1: Open Backpack      2:Search chests      3:Navigation panel";
    public static final String STAT_INFO = "\033[31;1m HP: %.2f - %.2f  \033[0m \033[34;1m MN: %.2f - %.2f \033" +
            "[0m \033[33;1m RG: %.2f - %.2f\033[0m";
    public static final String CANT_ADD_TO_BACKPACK = "Cant add to backpack";

    public static final String HERO_STATS_FORMAT = "%s \033[31;1m HP: %.2f \033[0m" +
            " \033[34;1m MN: %.2f \033[0m \033[33;1m RG: %.2f\033[0m";
    public static final String NPC_STATS_FORMAT = "%s \033[31;1m HP: %.2f - %.2f  " +
            "\033[0m \033[34;1m MN: %.2f - %.2f \033[0m \033[33;1m RG: %.2f - %.2f\033[0m";
    public static final String ROUND_EFFECT_FORMAT = "%s was damaged by %s";
    public static final String ROUND = "Round ";
    public static final String CHOSE_ITEM = "Print index of item to add to backpack:\n0: Go back\n";
    public static final String NO_ITEMS = "No items to equip";
    public static final String DROP_ITEM_MSG = "My hands already have weapons. I need to drop weapons in my hands";
    public static final String CANT_EQUIP = "I don't know how to equip this";
    public static final String NOT_SORCERER = "I cant equip accessory, or I'm not a Sorcerer";
    public static final String HERO_ATTACK_FORMAT = "%s deals damage with the %s to %s";
    public static final String RUN = "Run away";
    public static final String HOLD = "Hold";
    public static final String BACK = "Move back";
    public static final String FORWARD = "Move forward";
    public static final String NPC_HIT_PATTERN = "%s attacks %s by %s";
    public static final String ATTACK_POWER = "Attack power: ";
    public static final String RECEIVED_ATTACK = "Received attack: ";
    public static final String REFLECTION = "REFLECTION";
    public static final String REFLECTION_POWER = "Reflection power: ";
    public static final String RECEIVED_REFLECTION = "Received reflection: ";
    public static final String INF = "-----------------------------------------------------------------";
    public static final String NO_POINTS = "\033[31;1mNot enough points\033[0m";
    public static final String NOT_A_NUMBER = "\033[31;1mNot a number, try again!\033[0m";
    public static final String CHOOSE_CORRECT = "\033[31;1mChoose correct input!\033[0m";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final int INDEX = 1;
}
