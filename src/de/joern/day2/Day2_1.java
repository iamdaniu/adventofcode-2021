package de.joern.day2;


public class Day2_1 extends Day2 {

    @Override
    protected void forward(int amount) {
        coordinates.position += amount;
    }

    @Override
    protected void down(int amount) {
        coordinates.depth += amount;

    }

    @Override
    protected void up(int amount) {
        coordinates.depth -= amount;
    }
}