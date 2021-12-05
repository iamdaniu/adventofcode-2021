package de.joern.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class PointCollector {
    public List<Coordinate> getPointsRewrite(Line line) {
        if (!line.isHorizontal() && !line.isVertical() && !line.isDiagonal()) {
            throw new IllegalArgumentException("can only create points for horizontal or vertical lines, not " + line);
        }
        var start = line.getStart();
        var end = line.getEnd();
        UnaryOperator<Integer> adjustX = line.isVertical()
                ? UnaryOperator.identity()
                : getAdjustment(line, Coordinate::getX);
        UnaryOperator<Integer> adjustY = line.isHorizontal()
                ? UnaryOperator.identity()
                : getAdjustment(line, Coordinate::getY);
        final List<Coordinate> result = new ArrayList<>();
        var current = new Coordinate(start.getX(), start.getY());
        result.add(current);
        while (!current.equals(end)) {
            current = new Coordinate(adjustX.apply(current.getX()),
                    adjustY.apply(current.getY()));
            result.add(current);
        }
        return result;
    }

    private static UnaryOperator<Integer> getAdjustment(Line line, Function<Coordinate, Integer> getPart) {
        int adjustment = getPart.apply(line.getStart()) < getPart.apply(line.getEnd())
                ? 1
                : -1;
        return i -> i + adjustment;
    }
}
