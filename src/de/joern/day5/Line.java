package de.joern.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public record Line(Coordinate start, Coordinate end) {

    public boolean isHorizontal() {
        return start.y() == end.y();
    }

    public boolean isVertical() {
        return start.x() == end.x();
    }

    public boolean isDiagonal() {
        return Math.abs(start.x() - end.x()) == Math.abs(start.y() - end.y());
    }

    public static Line parse(String toParse) {
        String[] split = toParse.split(" -> ");
        return new Line(
                Coordinate.parse(split[0]),
                Coordinate.parse(split[1])
        );
    }

    public List<Coordinate> getPoints() {
        if (!isHorizontal() && !isVertical() && !isDiagonal()) {
            throw new IllegalArgumentException("can only create points for horizontal or vertical lines, not " + this);
        }
        UnaryOperator<Integer> adjustX = isVertical()
                ? UnaryOperator.identity()
                : getAdjustment(Coordinate::x);
        UnaryOperator<Integer> adjustY = isHorizontal()
                ? UnaryOperator.identity()
                : getAdjustment(Coordinate::y);
        final List<Coordinate> result = new ArrayList<>();
        var current = new Coordinate(start.x(), start.y());
        result.add(current);
        while (!current.equals(end)) {
            current = new Coordinate(adjustX.apply(current.x()),
                    adjustY.apply(current.y()));
            result.add(current);
        }
        return result;
    }

    private UnaryOperator<Integer> getAdjustment(Function<Coordinate, Integer> getPart) {
        int adjustment = getPart.apply(start) < getPart.apply(end)
                ? 1
                : -1;
        return i -> i + adjustment;
    }
}
