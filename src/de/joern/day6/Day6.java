package de.joern.day6;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 implements ProblemSolver {
    private static final int DAY_COUNT = 80;

    private final List<Long> newFishIn = new ArrayList<>(8);
    private long fishCount;

    @Override
    public void consider(String line) {
        final var split = line.split(",");
        var counted = Stream.of(split)
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (int i = 0; i < 9; i++) {
            long newInDays = counted.getOrDefault(i, 0L);
            fishCount += newInDays;
            newFishIn.add(newInDays);
        }
    }

    @Override
    public void finished() {
        for (int i = 0; i < DAY_COUNT; i++) {
            long newFish = newFishIn.remove(0);
            newFishIn.add(newFish);
            newFishIn.set(6, newFishIn.get(6) + newFish);
            fishCount += newFish;
            System.out.printf("%d at day %d: %s%n", fishCount, i, newFishIn);
        }
        System.out.printf("%d fish after %d days%n", fishCount, DAY_COUNT);
    }
}
