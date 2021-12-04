package de.joern.day2;

public class Day2_2 extends Day2 {
    private int aim;

    @Override
    protected void forward(int value) {
        coordinates.position += value;
        coordinates.depth += (aim*value);
    }

    @Override
    protected void up(int value) {
        aim -= value;
    }

    @Override
    protected void down(int value) {
        aim += value;
    }

    @Override
    public void finished() {
        System.out.printf("depth: %d, position: %d; total %d%n", coordinates.depth, coordinates.position,
                coordinates.depth * coordinates.position);
    }
}