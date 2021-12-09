package de.joern.day9;

import java.util.*;
import java.util.List;

public class Day9_2 extends Day9 {
    public static final Comparator<Collection<?>> REVERSE_SIZE =
            Comparator.<Collection<?>, Integer>comparing(Collection::size).reversed();

    @Override
    public void finished() {
        Set<Point> inAnyBasin = new HashSet<>(map.getHeight() * map.getWidth());
        List<Set<Point>> basins = new ArrayList<>();
        for (int row = 0; row < map.getHeight(); row++) {
            for (int col = 0; col < map.getWidth(); col++) {
                Point currentPoint = new Point(row, col);
                if (map.heightAt(row, col) == 9 || inAnyBasin.contains(currentPoint)) {
                    continue;
                }
                Set<Point> basin = getBasin(currentPoint);
                inAnyBasin.addAll(basin);
                basins.add(basin);
            }
        }
        int result = basins.stream()
                .sorted(REVERSE_SIZE)
                .mapToInt(Collection::size)
                .limit(3)
                .reduce(1, (i1, i2) -> i1*i2);
        System.out.printf("multiplication result of 3 largest basins sizes is %d%n", result);
    }
    private Set<Point> getBasin(Point point) {
        Set<Point> basin = new HashSet<>();
        Queue<Point> toCheck = new ArrayDeque<>();
        toCheck.add(point);
        Point currentPoint;
        while ((currentPoint = toCheck.poll()) != null) {
            basin.add(currentPoint);
            map.surroundingPoints(currentPoint).stream()
                    .filter(p -> map.heightAt(p) != 9)
                    .filter(p -> !basin.contains(p))
                    .forEach(toCheck::add);
        }
        return basin;
    }
}
