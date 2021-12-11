package de.joern.day10;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.BinaryOperator;

public class Day10 implements ProblemSolver {
    private static final Map<Character, Character> closing = new HashMap<>();
    private static final Map<Character, Character> opening = new HashMap<>();
    private static final Map<Character, Integer> debugScore = new HashMap<>();
    private static final String correctionScore = "0)]}>";

    private long totalDebugScore = 0;
    private final BinaryOperator<Long> selectResult;

    private final List<Long> allScores = new ArrayList<>();
    static {
        closing.put(')', '(');
        closing.put(']', '[');
        closing.put('}', '{');
        closing.put('>', '<');
        closing.forEach((k, v) -> opening.put(v, k));
        debugScore.put(')', 3);
        debugScore.put(']', 57);
        debugScore.put('}', 1197);
        debugScore.put('>', 25137);
    }

    protected Day10(BinaryOperator<Long> selectResult) {
        this.selectResult = selectResult;
    }

    public static ProblemSolver day10_1() {
        return new Day10((debug, correction) -> debug);
    }
    public static ProblemSolver day10_2() {
        return new Day10((debug, correction) -> correction);
    }

    @Override
    public void consider(String line) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char at = line.charAt(i);
            if (closing.containsKey(at)) {
                if (stack.isEmpty() || closing.get(at) != stack.pop()) {
                    totalDebugScore += debugScore.get(at);
                    return;
                }
            } else {
                stack.push(at);
            }
        }
        allScores.add(correctionScore(stack));
    }

    @Override
    public long finished() {
        System.out.printf("total debug result in lines is %d%n", totalDebugScore);

        Collections.sort(allScores);
        int middleIndex = (allScores.size()-1)/2;
        long correctionScore = allScores.get(middleIndex);
        System.out.printf("total correction result in lines is %d%n", correctionScore);
        return selectResult.apply(totalDebugScore, correctionScore);
    }

    private static long correctionScore(Stack<Character> incomplete) {
        long result = 0;
        while (!incomplete.isEmpty()) {
            result *= 5;
            Character pop = incomplete.pop();
            char correction = opening.get(pop);
            result += correctionFor(correction);
        }
        return result;
    }

    private static int correctionFor(char c) {
        return correctionScore.indexOf(c);
    }
}
