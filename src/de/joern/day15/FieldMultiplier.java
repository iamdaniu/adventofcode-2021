package de.joern.day15;

import de.joern.IntField;
import de.joern.Point;

public class FieldMultiplier {
    public static IntField multiply(IntField field) {
        IntField result = new IntField();
        field.allPoints()
                .forEach(p -> {
                    for (int row = 0; row < 5; row++) {
                        for (int col = 0; col < 5; col++) {
                            Point targetPoint = new Point(row * field.getHeight() + p.row(),
                                    col * field.getHeight() + p.col());
                            int newValue = (field.valueAt(p) + row + col);
                            newValue = newValue > 9
                                    ? newValue - 9
                                    : newValue;
                            result.setValueAt(targetPoint, newValue);
                        }
                    }
                });
        return result;
    }

}
