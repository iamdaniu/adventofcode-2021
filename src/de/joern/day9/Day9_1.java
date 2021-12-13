package de.joern.day9;

import de.joern.Point;

public class Day9_1 extends Day9 {
    @Override
    public long finished() {
        int sum = map.allPoints()
                .filter(p -> map.valueAt(p) < getMinInSurrounding(p))
                .mapToInt(map::valueAt)
                .sum();
        System.out.printf("Sum of lowest points is %d%n", sum);
        return sum;
    }

    private int getMinInSurrounding(Point p) {
        return map.surroundingPoints(p)
                .mapToInt(map::valueAt)
                .min().orElse(Integer.MAX_VALUE);
    }
}
