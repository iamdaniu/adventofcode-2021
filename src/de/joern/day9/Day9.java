package de.joern.day9;

import de.joern.ProblemSolver;

import java.util.*;

abstract class Day9 implements ProblemSolver {
    protected final FloorMap map = new FloorMap();

    @Override
    public void consider(String line) {
        List<Integer> row = new ArrayList<>(line.length());
        for (int i = 0; i < line.length(); i++) {
            int height = line.charAt(i) - '0';
            row.add(height);
        }
        map.add(row);
    }
}
