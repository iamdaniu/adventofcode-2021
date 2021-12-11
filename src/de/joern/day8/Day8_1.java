package de.joern.day8;

import de.joern.ProblemSolver;

import java.util.List;
import java.util.stream.Stream;

public class Day8_1 implements ProblemSolver {
    private static final List<Integer> WITH_UNIQUE = List.of(2, 4, 3, 7);

    private long totalCount;
    @Override
    public void consider(String line) {
        totalCount += Stream.of(line.split(" "))
                .dropWhile(s -> !"|".equals(s))
                .mapToInt(String::length)
                .filter(WITH_UNIQUE::contains)
                .count();
    }

    @Override
    public long finished() {
        System.out.printf("unique digits in output: %d%n", totalCount);
        return totalCount;
    }
}
