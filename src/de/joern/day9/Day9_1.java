package de.joern.day9;

public class Day9_1 extends Day9 {
    @Override
    public void finished() {
        int sum = map.allPoints()
                .filter(p -> map.heightAt(p) < getMinInSurrounding(p))
                .mapToInt(map::heightAt)
                .sum();
        System.out.printf("Sum of lowest points is %d%n", sum);
    }

    private int getMinInSurrounding(Point p) {
        return map.surroundingPoints(p)
                .mapToInt(map::heightAt)
                .min().orElse(Integer.MAX_VALUE);
    }
}
