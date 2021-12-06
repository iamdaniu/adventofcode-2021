package de.joern.day5;

public record Coordinate(int x, int y) {

    public static Coordinate parse(String toParse) {
        String[] split = toParse.split(",");
        return new Coordinate(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1])
        );
    }
}
