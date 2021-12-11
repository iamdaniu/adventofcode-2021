package de.joern.day2;

import de.joern.ProblemSolver;

abstract class Day2 implements ProblemSolver {
    protected final Coordinates coordinates = new Coordinates();

    @Override
    public void consider(String line) {
        final var move = Move.parse(line);
        switch (move.getMovement()) {
            case UP -> up(move.getAmount());
            case DOWN -> down(move.getAmount());
            case FORWARD -> forward(move.getAmount());
        }
    }

    @Override
    public final long finished() {
        int result = coordinates.depth * coordinates.position;
        System.out.printf(
                "depth: %d, position: %d; total: %d%n",
                coordinates.depth, coordinates.position,
                result);
        return result;
    }

    protected abstract void forward(int amount);

    protected abstract void down(int amount);

    protected abstract void up(int amount);
}
