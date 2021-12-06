package de.joern.day6;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 implements ProblemSolver {
    private final int dayCount;

    private final List<Long> newFishIn = new ArrayList<>(8);
    private long fishCount;

    public Day6(int dayCount) {
        this.dayCount = dayCount;
    }

    public static Day6 day6_1() {
        return new Day6(80);
    }

    public static Day6 day6_2() {
        return new Day6(256);
    }

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
        for (int i = 0; i < dayCount; i++) {
            long newFish = newFishIn.remove(0);
            newFishIn.add(newFish);
            newFishIn.set(6, newFishIn.get(6) + newFish);
            fishCount += newFish;
        }
        System.out.printf("%d fish after %d days%n", fishCount, dayCount);
    }
}
