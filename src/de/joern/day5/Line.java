package de.joern.day5;

public class Line {
    private final Coordinate start;
    private final Coordinate end;

    public Line(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public boolean isHorizontal() {
        return start.getY() == end.getY();
    }

    public boolean isVertical() {
        return start.getX() == end.getX();
    }

    public static Line parse(String toParse) {
        String[] split = toParse.split(" -> ");
        return new Line(
                Coordinate.parse(split[0]),
                Coordinate.parse(split[1])
        );
    }

    @Override
    public String toString() {
        return "{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
