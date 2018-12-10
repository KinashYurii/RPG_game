package com.griddynamics.rpg.util.mykhailDemo;

import com.griddynamics.rpg.service.roll.DiceService;

import java.util.stream.IntStream;

public class DiceRollThreads {
    public static void main(String[] args) {

        IntStream.rangeClosed(0, 100).forEach(e -> System.out.println(DiceService.getInstance().roll(2, 6)));
        System.out.println("Pool shutdowning....");
        DiceService.getInstance().shutdownPool();
    }
}
