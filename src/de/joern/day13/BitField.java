package de.joern.day13;

import de.joern.Point;

import java.util.HashMap;
import java.util.Map;

class BitField {


    private int width;
    private int height;

    private final Map<Point, Boolean> set = new HashMap<>();

    public void set(int x, int y) {
        set.put(new Point(x, y), true);
        width = Math.max(width, x + 1);
        height = Math.max(height, y + 1);
    }

    public boolean isSet(int x, int y) {
        return set.getOrDefault(new Point(x, y), false);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    int visible() {
        return set.size();
    }

    private BitField applyXFold(int along) {
        BitField result = new BitField();
        for (int x = 0; x < along; x++) {
            for (int y = 0; y < height; y++) {
                int targetX = along - x - 1;
                boolean set = isSet(targetX, y);
                if (!set) {
                    int foldedX = along + x + 1;
                    set = foldedX < width && isSet(foldedX, y);
                }
                if (set) {
                    result.set(targetX, y);
                }
            }
        }

        return result;
    }

    private BitField applyYFold(int along) {
        BitField result = new BitField();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < along; y++) {
                int targetY = along - y - 1;
                boolean set = isSet(x, targetY);
                if (!set) {
                    int foldedY = along + y + 1;
                    set = foldedY < height && isSet(x, foldedY);
                }
                if (set) {
                    result.set(x, targetY);
                }
            }
        }

        return result;
    }

    public BitField applyFold(Fold fold) {
        return fold.alongX()
                ? applyXFold(fold.alongWhere())
                : applyYFold(fold.alongWhere());
    }
}
