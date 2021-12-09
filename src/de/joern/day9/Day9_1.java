package de.joern.day9;

public class Day9_1 extends Day9 {
    @Override
    public void finished() {
        int sum = 0;
        for (int row = 0; row < map.getHeight(); row++) {
            for (int col = 0; col < map.getWidth(); col++) {
                int currentHeight = map.heightAt(row, col);
                int minSurrounding = getMinInSurrounding(row, col);
                if (currentHeight < minSurrounding) {
                    sum += currentHeight + 1;
                }
            }
        }
        System.out.printf("Sum of lowest points is %d%n", sum);
    }

    int getMinInSurrounding(int row, int col) {
        return map.surroundingPoints(row, col)
                .stream()
                .mapToInt(map::heightAt)
                .min().orElse(Integer.MAX_VALUE);
    }
}
