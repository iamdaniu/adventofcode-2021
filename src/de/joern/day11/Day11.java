package de.joern.day11;

import de.joern.IntField;
import de.joern.ProblemSolver;

import java.util.function.BinaryOperator;

public class Day11 implements ProblemSolver {
    private final IntField map = new IntField();
    private final BinaryOperator<Integer> selectResult;

    protected Day11(BinaryOperator<Integer> selectResult) {
        this.selectResult = selectResult;
    }

    @Override
    public void consider(String line) {
        map.add(line);
    }

    public static ProblemSolver day11_1() {
        return new Day11((count, steps) -> count);
    }
    public static ProblemSolver day11_2() {
        return new Day11((count, steps) -> steps);
    }

    @Override
    public long finished() {
        int flashCount = 0;
        int steps;
        for (steps = 0; steps < 100; steps++) {
            int flashes = step();
            flashCount += flashes;
        }
        System.out.printf("flashes after 100 steps: %d%n", flashCount);
        int totalFlashes = 0;
        while (totalFlashes != map.getHeight()*map.getWidth()) {
            totalFlashes = step();
            steps++;
        }
        System.out.printf("synchronized after %d steps%n", steps);
        return selectResult.apply(flashCount, steps);
    }

    private int step() {
        int totalFlashes;
        totalFlashes = map.allPoints().mapToInt(this::raiseEnergy).sum();
        map.allPoints()
                .filter(p -> map.valueAt(p) > 9)
                .forEach(p -> map.setValueAt(p, 0));
        return totalFlashes;
    }

    private int raiseEnergy(IntField.Point point) {
        int result = 0;
        map.setValueAt(point, map.valueAt(point) + 1);
        if (map.valueAt(point) == 10) {
            result = 1 + map.adjacentPoints(point)
                    .mapToInt(this::raiseEnergy)
                    .sum();
        }
        return result;
    }
}
