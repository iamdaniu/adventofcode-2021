package de.joern.day5;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Predicate;

public class Day5 implements ProblemSolver {
    private final Predicate<Line> lineFilter;
    private final Map<Coordinate, Integer> occurrences = new HashMap<>();

    public static Day5 day5_1() {
        return new Day5(line -> line.isHorizontal() || line.isVertical());
    }

    public static Day5 day5_2() {
        return new Day5(line -> line.isHorizontal() || line.isVertical() || line.isDiagonal());
    }

    private Day5(Predicate<Line> lineFilter) {
        this.lineFilter = lineFilter;
    }

    @Override
    public void consider(String line) {
        final var newLine = Line.parse(line);
        if (lineFilter.test(newLine)) {
            for (Coordinate c : newLine.getPoints()) {
                occurrences.merge(c, 1, Integer::sum);
            }
        }
    }

    @Override
    public void finished() {
        final var count = occurrences.values().stream()
                .mapToInt(Integer::intValue)
                .filter(i -> i > 1)
                .count();
        System.out.printf("lines overlap in %d points%n", count);
    }
}
