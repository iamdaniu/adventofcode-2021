package de.joern.day1;


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
    public void finished() {
        System.out.println(higherCount);
    }
}
