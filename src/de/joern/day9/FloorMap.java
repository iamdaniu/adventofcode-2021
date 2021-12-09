package de.joern.day9;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class FloorMap {
    private int height;
    private int width;
    private final Map<Point, Integer> heightAt = new HashMap<>();

    public void add(List<Integer> heights) {
        for (int i = 0; i < heights.size(); i++) {
            heightAt.put(new Point(height, i), heights.get(i));
        }
        height++;
        width = Math.max(width, heights.size());
    }

    public Stream<Point> allPoints() {
        return heightAt.keySet().stream();
    }

    public Stream<Point> surroundingPoints(Point point) {
        return Stream.of(
                        new Point(point.row() - 1, point.col()),
                        new Point(point.row(), point.col() - 1),
                        new Point(point.row(), point.col() + 1),
                        new Point(point.row() + 1, point.col())
                ).filter(p -> p.col() >= 0 && p.col() < width)
                .filter(p -> p.row() >= 0 && p.row() < height);
    }

    public int heightAt(Point point) {
        return heightAt.get(point);
    }
}
