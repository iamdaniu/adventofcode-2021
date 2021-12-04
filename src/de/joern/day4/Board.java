package de.joern.day4;

public class Board {
    private final int[][] numbers;
    private final boolean[][] markings;

    public Board(int size) {
        numbers = new int[size][size];
        markings = new boolean[size][size];
    }

    void setRow(int rowNum, int[] row) {
        numbers[rowNum] = row;
    }

    void mark(int value) {
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers[row].length; col++) {
                if (numbers[row][col] == value) {
                    markings[row][col] = true;
                }
            }
        }
    }

    boolean isWinning() {
        for (int row = 0; row < numbers.length; row++) {
            boolean result = true;
            for (boolean marking : markings[row]) {
                if (!marking) {
                    result = false;
                    break;
                }
            }
            if (result) {
                return true;
            }
        }
        for (int col = 0; col < numbers.length; col++) {
            boolean result = true;
            for (int row = 0; row < numbers.length; row++) {
                if (!markings[row][col]) {
                    result = false;
                    break;
                }
            }
            if (result) {
                return true;
            }
        }
        return false;
    }

    int score() {
        int score = 0;
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers.length; col++) {
                if (!markings[row][col]) {
                    score += numbers[row][col];
                }
            }
        }
        return score;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < numbers.length; row++) {
            for (int col = 0; col < numbers.length; col++) {
                builder.append(String.format("%3d%s", numbers[row][col], (markings[row][col] ? "*" : " ")));
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
