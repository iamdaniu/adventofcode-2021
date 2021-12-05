package de.joern.day5;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

abstract class Day5 implements ProblemSolver {
    private final List<Line> lines = new ArrayList<>();
    protected final Predicate<Line> lineFilter;

    protected Day5(Predicate<Line> lineFilter) {
        this.lineFilter = lineFilter;
    }

    @Override
    public void consider(String line) {
        Line newLine = Line.parse(line);
        if (lineFilter.test(newLine)) {
            lines.add(newLine);
        }
    }

    @Override
    public void finished() {
        Set<Coordinate> overlaps = new HashSet<>();
        PointCollector collector = new PointCollector();
        for (int tested = 0; tested < lines.size(); tested++) {
            Line testedLine = lines.get(tested);
//            System.out.printf("testing %s%n...", testedLine);
            for (int i = tested+1; i < lines.size(); i++) {
                List<Coordinate> points = collector.getPoints(testedLine);
                Line candidate = lines.get(i);
                List<Coordinate> candidatePoints = collector.getPoints(candidate);
                points.retainAll(candidatePoints);
                if (!points.isEmpty()) {
//                    System.out.printf("lines overlap: %s and %s", testedLine, lines.get(i));
                    overlaps.addAll(points);
                }
            }
        }
        System.out.printf("lines overlap in %d points: %s", overlaps.size(), overlaps);
    }
}
