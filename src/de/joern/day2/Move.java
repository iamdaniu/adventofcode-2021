package de.joern.day2;

public class Move {
    private final Movement movement;
    private final int amount;

    private Move(Movement movement, int amount) {
        this.movement = movement;
        this.amount = amount;
    }

    public Movement getMovement() {
        return movement;
    }

    public int getAmount() {
        return amount;
    }

    public static Move parse(String line) {
        final var split = line.split(" ");
        Movement movement = Movement.valueOf(split[0].toUpperCase());
        int amount = Integer.parseInt(split[1]);

        return new Move(movement, amount);
    }
}
