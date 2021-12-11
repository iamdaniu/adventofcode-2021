package de.joern.day11;

import de.joern.IntField;
import de.joern.ProblemSolver;

public class Day11 implements ProblemSolver {
    private final IntField map = new IntField();

    @Override
    public void consider(String line) {
        map.add(line);
    }

    @Override
    public void finished() {
        int flashCount = 0;
        int i;
        for (i = 0; i < 100; i++) {
            int flashes = step();
            flashCount += flashes;
        }
        System.out.printf("flashes after 100 steps: %d%n", flashCount);
        int totalFlashes = 0;
        while (totalFlashes != map.getHeight()*map.getWidth()) {
            totalFlashes = step();
            i++;
        }
        System.out.printf("synchronized after %d steps%n", i);
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
