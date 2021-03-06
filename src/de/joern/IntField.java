package de.joern;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntField {
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private int height;
    private int width;
    private final Map<Point, Integer> valueAt = new HashMap<>();

    public void add(String line) {
        List<Integer> values = IntStream.range(0, line.length())
                .map(i -> line.charAt(i) - '0')
                .boxed()
                .collect(Collectors.toList());
        add(values);
    }

    public void add(List<Integer> line) {
        width = Math.max(width, line.size());
        for (int i = 0; i < line.size(); i++) {
            int value = line.get(i);
            valueAt.put(new Point(height, i), value);
        }
        height++;
    }

    public void setValueAt(Point point, int value) {
        valueAt.put(point, value);
//        valueAt.replace(point, value);
        height = Math.max(height, point.row()+1);
        width = Math.max(width, point.col()+1);
    }

    public Stream<Point> allPoints() {
        return valueAt.keySet().stream();
    }

    public Set<Point> depthSearch(Point point, BiFunction<IntField, Point, Stream<Point>> connected) {
        Set<Point> basin = new HashSet<>();
        Queue<Point> toCheck = new ArrayDeque<>();
        toCheck.add(point);
        Point currentPoint;
        while ((currentPoint = toCheck.poll()) != null) {
            basin.add(currentPoint);
            connected.apply(this, currentPoint)
                    .filter(p -> valueAt(p) != 9)
                    .filter(p -> !basin.contains(p))
                    .forEach(toCheck::add);
        }
        return basin;
    }

    public Stream<Point> surroundingPoints(Point point) {
        return Stream.of(
                        new Point(point.row() - 1, point.col()),
                        new Point(point.row(), point.col() - 1),
                        new Point(point.row(), point.col() + 1),
                        new Point(point.row() + 1, point.col())
                ).filter(p -> p.col() >= 0 && p.col() < width)
                .filter(p -> p.row() >= 0 && p.row() < height);
    }

    // including diagonally adjacent
    public Stream<Point> adjacentPoints(Point point) {
        return IntStream.range(-1, 2)
                .mapToObj(i -> IntStream.range(-1, 2)
                        .mapToObj(j -> new Point(point.row()+i, point.col() + j)))
                .flatMap(s -> s)
                .filter(p -> !p.equals(point))
                .filter(p -> p.col() >= 0 && p.col() < width)
                .filter(p -> p.row() >= 0 && p.row() < height);
    }

    public int valueAt(Point point) {
        try {
            return valueAt.get(point);
        } catch (RuntimeException x) {
            System.out.printf("could not access value for %s%n", point);
            throw x;
        }
    }

    @SuppressWarnings("unused")
    public static void print(IntField map) {
        for (int row = 0; row < map.getHeight(); row++) {
            for (int col = 0; col < map.getWidth(); col++) {
                System.out.printf("%d", map.valueAt(new Point(row, col)));
            }
            System.out.println();
        }
    }

}
