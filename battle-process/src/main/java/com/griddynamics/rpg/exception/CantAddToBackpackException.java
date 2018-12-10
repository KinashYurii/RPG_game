package com.griddynamics.rpg.exception;

import static com.griddynamics.rpg.util.constants.UserInfoConstants.CANT_ADD_TO_BACKPACK;

public class CantAddToBackpackException extends Exception {
    @Override
    public String getMessage() {
        return CANT_ADD_TO_BACKPACK;
    }
}
