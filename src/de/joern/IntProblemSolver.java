package de.joern;


public abstract class IntProblemSolver implements ProblemSolver {
    public final void consider(String line) {
        consider(Integer.parseInt(line));
    }

    protected abstract void consider(Integer value);
}
