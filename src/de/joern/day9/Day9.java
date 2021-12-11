package de.joern.day9;

import de.joern.IntField;
import de.joern.ProblemSolver;

abstract class Day9 implements ProblemSolver {
    protected final IntField map = new IntField();

    @Override
    public void consider(String line) {
        map.add(line);
    }
}
