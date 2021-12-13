package de.joern.day13;

import de.joern.ProblemSolver;

import java.util.*;

public class Day13 implements ProblemSolver {
    private boolean foundEmpty;
    private final BitField field = new BitField();
    private final List<Fold> folds = new ArrayList<>();

    @Override
    public void consider(String line) {
        if (line.isBlank()) {
            foundEmpty = true;
            return;
        }
        if (foundEmpty) {
            String foldString = line.split(" ")[2];
            String[] split = foldString.split(("="));
            Fold fold = new Fold("x".equals(split[0]), Integer.parseInt(split[1]));
            folds.add(fold);
        } else {
            String[] split = line.split(",");
            field.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    @Override
    public long finished() {
        BitField afterFirst = field.applyFold(folds.get(0));
        System.out.printf("after first: %d dots are visible%n", afterFirst.visible());

        BitField result = field;

        for (Fold fold : folds) {
            result = result.applyFold(fold);
        }
        printField(result);
        return afterFirst.visible();
    }

    private void printField(BitField field) {
        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                System.out.print(field.isSet(j, i) ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }

}
