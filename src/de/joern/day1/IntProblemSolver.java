package de.joern.day1;

import de.joern.ProblemSolver;

public abstract class IntProblemSolver implements ProblemSolver {
    public void consider(String line) {
        consider(Integer.parseInt(line));
    }

    protected abstract void consider(Integer value);
}
