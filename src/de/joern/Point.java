package de.joern;

import java.util.stream.Stream;

public record Point(int row, int col) {
    @Override
    public String toString() {
        return String.format("%d/%d", row, col);
    }
}
