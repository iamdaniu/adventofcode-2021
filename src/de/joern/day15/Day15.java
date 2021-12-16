package de.joern.day15;

import de.joern.IntField;
import de.joern.Point;
import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Day15 implements ProblemSolver {
    private final IntField scanResult = new IntField();
    private final UnaryOperator<IntField> getField;

    private Day15(UnaryOperator<IntField> getField) {
        this.getField = getField;
    }

    public static Day15 day15_1() {
        return new Day15(UnaryOperator.identity());
    }

    public static Day15 day15_2() {
        return new Day15(FieldMultiplier::multiply);
    }

    @Override
    public void consider(String line) {
        scanResult.add(line);
    }

    @Override
    public long finished() {
        IntField field = getField.apply(this.scanResult);

        Point endPoint = new Point(field.getWidth()-1, field.getHeight()-1);
        Map<Point, Long> shortestToEnd = new HashMap<>();
        shortestToEnd.put(endPoint, 0L);

        Set<Point> toCheck = field.surroundingPoints(endPoint).collect(Collectors.toSet());
        Point startPoint = new Point(0, 0);
        while (!toCheck.isEmpty()) {
            Set<Point> checkAfter = new HashSet<>();
            for (Point point : toCheck) {
                var shortestPathFromHere = field.surroundingPoints(point)
                        .filter(shortestToEnd::containsKey)
                        .mapToLong(p -> shortestToEnd.get(p) + field.valueAt(p))
                        .min()
                        .orElseThrow();
                Long current = shortestToEnd.get(point);
                if (current == null || current > shortestPathFromHere) {
                    shortestToEnd.put(point, shortestPathFromHere);
                    if (!startPoint.equals(point)) {
                        field.surroundingPoints(point)
                                .forEach(checkAfter::add);
                    }
                }
            }
            toCheck = checkAfter;
        }
        var shortestPath = shortestToEnd.get(startPoint);
        System.out.printf("value of shortest path from %s: %d%n", startPoint, shortestPath);

        return shortestPath;
    }
}
