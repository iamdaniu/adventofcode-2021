package de.joern.day3;

import de.joern.ProblemSolver;


public class Day3_1 implements ProblemSolver {
    private MostCommonBitFinder[] bitFinders;

    @Override
    public void consider(String line) {
        if (bitFinders == null) {
            createFinders(line.length());
        }
        for (int i = 0; i < line.length(); i++) {
            boolean digit = line.charAt(i) == '1';
            bitFinders[i].consider(digit);
        }
    }
    private void createFinders(int length) {
        bitFinders = new MostCommonBitFinder[length];
        for (int i = 0; i < length; i++) {
            bitFinders[i] = new MostCommonBitFinder();
        }
    }

    @Override
    public void finished() {
        int gamma = 0;
        int epsilon = 0;
        for (int i = 0; i < bitFinders.length; i++) {
            int bitValue = (int) Math.pow(2, i);
            boolean mostCommon = bitFinders[bitFinders.length -i-1].getMostCommonBit();
            if (mostCommon) {
                gamma += bitValue;
            } else {
                epsilon += bitValue;
            }
        }
        System.out.printf("gamma: %d; epsilon: %d - consumption: %d%n", gamma, epsilon, (gamma * epsilon));
    }
}
