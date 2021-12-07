package de.joern.day7;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Day7 implements ProblemSolver {
    private int minValue = Integer.MAX_VALUE;
    private int maxValue = Integer.MIN_VALUE;
    private final BinaryOperator<Integer> fromToCalculation;

    private List<Integer> values;

    private Day7(BinaryOperator<Integer> fromToCalculation) {
        this.fromToCalculation = fromToCalculation;
    }

    public static Day7 day7_1() {
        return new Day7(Day7::distance);
    }

    public static Day7 day7_2() {
        return new Day7(Day7::increasingDistance);
    }

    @Override
    public void consider(String line) {
        String[] split = line.split(",");
        values = new ArrayList<>(split.length);
        for (String val : split) {
            int value = Integer.parseInt(val);
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
            values.add(value);
        }
        System.out.printf("read %d values - min %d, max %d%n", values.size(), minValue, maxValue);
    }

    @Override
    public void finished() {
        int target = 0;
        long result = Integer.MAX_VALUE;
        for (int i = minValue; i < maxValue+1; i++) {
            int index = i;
            int sum = values.stream()
                    .mapToInt(v -> fromToCalculation.apply(v, index))
                    .sum();
            if (sum < result) {
                target = i;
                result = sum;
            }
        }
        System.out.printf("minimum sum is %d for value %d%n", result, target);
    }

    private static int distance(int i1, int i2) {
        return Math.abs(i1 - i2);
    }

    private static int increasingDistance(int i1, int i2) {
        int distance = distance(i1, i2);
        int result = distance;
        for (int i = 1; i < distance; i++) {
            result += i;
        }
        return result;
    }
}
