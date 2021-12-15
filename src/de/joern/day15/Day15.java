package de.joern.day15;

import de.joern.IntField;
import de.joern.Point;
import de.joern.ProblemSolver;

import java.util.*;
import java.util.stream.Collectors;

public class Day15 implements ProblemSolver {
    private final IntField field = new IntField();

    @Override
    public void consider(String line) {
        field.add(line);
    }

    @Override
    public long finished() {
        Set<Point> toCheck = new HashSet<>();
        Point endPoint = new Point(field.getWidth()-1, field.getHeight()-1);
        Map<Point, Path> shortestToEnd = new HashMap<>();
        shortestToEnd.put(endPoint, new Path(field).append(endPoint));
        field.surroundingPoints(endPoint).forEach(toCheck::add);
        Point startPoint = new Point(0, 0);
        while (!toCheck.isEmpty()) {
//            System.out.printf("checking %s%n", toCheck);
            Set<Point> checkAfter = new HashSet<>();
            for (Point point : toCheck) {
                Path shortestPathFromHere = field.surroundingPoints(point)
                        .filter(shortestToEnd::containsKey)
                        .map(shortestToEnd::get)
                        .min(Comparator.comparing(this::value))
                        .orElseThrow()
                        .prepend(point);
                shortestToEnd.put(point, shortestPathFromHere);
                if (!startPoint.equals(point)) {
                    field.surroundingPoints(point)
                            .filter(p -> !shortestToEnd.containsKey(p))
                            .forEach(checkAfter::add);
                }
            }
            toCheck = checkAfter;
        }
//        Point startPoint = new Point(0, 0);
        Path shortestPath = shortestToEnd.get(startPoint);
        long value = value(shortestPath) - field.valueAt(startPoint);
        System.out.printf("shortest path from %s: %s - value %d%n", startPoint,
                shortestPath, value);

        return value;
    }

    public long value(Path p) {
        return p.getPoints().stream()
                .mapToLong(field::valueAt)
                .sum();
    }

    static class Path {
        private final IntField field;
        private final List<Point> points;
        Path(IntField field) {
            this(field, Collections.emptyList());
        }

        Path(IntField field, List<Point> points) {
            this.field = field;
            this.points = points;
        }
        public Path append(Point p) {
            List<Point> newPoints = new ArrayList<>(points);
            newPoints.add(p);
            return new Path(field, newPoints);
        }
        public Path prepend(Point p) {
            List<Point> newPoints = new ArrayList<>(points);
            newPoints.add(0, p);
            return new Path(field, newPoints);
        }

        public List<Point> getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return points.stream()
                    .map(this::pointString)
                    .collect(Collectors.joining(", "));
        }
        private String pointString(Point p) {
            return String.format("%d/%d (%d)", p.row(), p.col(), field.valueAt(p));
        }
    }
}
