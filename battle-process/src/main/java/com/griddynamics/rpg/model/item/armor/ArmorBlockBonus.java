package com.griddynamics.rpg.model.item.armor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ArmorBlockBonus {
    private int chance;
    private int damageToBlock;
}
