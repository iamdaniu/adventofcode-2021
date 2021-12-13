package de.joern.day9;

import de.joern.IntField;
import de.joern.Point;

import java.util.*;
import java.util.List;

public class Day9_2 extends Day9 {
    public static final Comparator<Collection<?>> REVERSE_SIZE =
            Comparator.<Collection<?>, Integer>comparing(Collection::size).reversed();

    @Override
    public long finished() {
        Set<Point> inAnyBasin = new HashSet<>();
        List<Set<Point>> basins = new ArrayList<>();
        map.allPoints()
                .filter(p -> map.valueAt(p) != 9)
                .filter(p -> !inAnyBasin.contains(p))
                .forEach(currentPoint -> {
                    Set<Point> basin = getBasin(currentPoint);
                    inAnyBasin.addAll(basin);
                    basins.add(basin);
                });

        int result = basins.stream()
                .sorted(REVERSE_SIZE)
                .mapToInt(Collection::size)
                .limit(3)
                .reduce(1, (i1, i2) -> i1 * i2);
        System.out.printf("multiplication result of 3 largest basins sizes is %d%n", result);
        return result;
    }

    private Set<Point> getBasin(Point point) {
        return map.depthSearch(point, IntField::surroundingPoints);
    }
}
