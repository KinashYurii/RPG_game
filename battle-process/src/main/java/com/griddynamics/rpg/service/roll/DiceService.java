package com.griddynamics.rpg.service.roll;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.griddynamics.rpg.util.constants.RollConstants.MAX_BOUND;
import static com.griddynamics.rpg.util.constants.RollConstants.MAX_PERCENTAGE;
import static java.util.stream.Collectors.toList;

public final class DiceService extends Thread {
    private static DiceService diceService;
    private static PseudoRoll pseudoRoll;
    private static ExecutorService pool;
    private final String noSuchDice = "No such dices in the world";

    public static DiceService getInstance() {
        if (diceService == null) {
            diceService = new DiceService();
            pool = Executors.newFixedThreadPool(4);
        }
        return diceService;
    }

    private static PseudoRoll getPseudoRoll(double chance) {
        if (pseudoRoll == null) {
            pseudoRoll = new PseudoRoll(chance);
        }
        return pseudoRoll;
    }

    public int roll(int quantity, int value, int additional) {
        return roll(quantity, value) + additional;
    }

    public boolean preparedRoll(int minBound) {
        double newMinBound = MAX_BOUND - minBound + 1;
        double chance = (MAX_PERCENTAGE / MAX_BOUND / MAX_PERCENTAGE) * newMinBound;
        return getPseudoRoll(chance).areYouLucky();
    }

    public int roll(int quantity, int value) {
        if (quantity <= 0 || value <= 0) {
            throw new IllegalArgumentException(noSuchDice);
        }
        List<Supplier<Integer>> tasks = IntStream.range(0, quantity)
                .mapToObj(i -> getRandomNumberFromThread(quantity, value))
                .collect(toList());
        return useCompletableFutureWithExecutor(tasks);
    }

    private Supplier<Integer> getRandomNumberFromThread(int quantity, int value) {
        return () -> ThreadLocalRandom.current().nextInt(quantity, value + 1);
    }

    private int useCompletableFutureWithExecutor(List<Supplier<Integer>> tasks) {
        List<CompletableFuture<Integer>> futures = tasks.stream()
                .map(t -> CompletableFuture.supplyAsync(t, pool))
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .mapToInt(p -> p)
                .sum();
    }

    public void shutdownPool() {

        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }
}