package de.joern.day5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class PointCollector {
    public List<Coordinate> getPoints(Line line) {
        List<Coordinate> result = new ArrayList<>();
        if (line.isHorizontal()) {
            Coordinate start = line.getStart();
            Coordinate end = line.getEnd();
            if (start.getX() > end.getX()) {
                start = end;
                end = line.getStart();
            }
            for (int i = start.getX(); i <= end.getX(); i++) {
                result.add(new Coordinate(i, start.getY()));
            }
        } else if (line.isVertical()) {
            Coordinate start = line.getStart();
            Coordinate end = line.getEnd();
            if (start.getY() > end.getY()) {
                start = end;
                end = line.getStart();
            }
            for (int i = start.getY(); i <= end.getY(); i++) {
                result.add(new Coordinate(start.getX(), i));
            }
        } else if (line.isDiagonal()) {
            Coordinate start = line.getStart();
            Coordinate end = line.getEnd();
            if (start.getX() > end.getX()) {
                start = end;
                end = line.getStart();
            }
            UnaryOperator<Integer> adjustY;
            if (start.getY() < end.getY()) {
                adjustY = y -> y + 1;
            } else {
                adjustY = y -> y - 1;
            }
            int currentY = start.getY();
            for (int i = start.getX(); i <= end.getX(); i++) {
                result.add(new Coordinate(i, currentY));
                currentY = adjustY.apply(currentY);
            }
        } else {
            throw new IllegalArgumentException("can only create points for horizontal or vertical lines, not " + line);
        }
        return result;
    }
}
