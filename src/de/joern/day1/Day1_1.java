package de.joern.day1;


import de.joern.IntProblemSolver;

public class Day1_1 extends IntProblemSolver {
    private Integer lastValue;
    private int higherCount;

    @Override
    protected void consider(Integer integer) {
        if (lastValue != null && integer > lastValue) {
            higherCount++;
        }
        lastValue = integer;
    }

    @Override
    public long finished() {
        System.out.println(higherCount);
        return higherCount;
    }
}
