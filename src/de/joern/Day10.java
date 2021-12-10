package de.joern;

import java.util.*;

public class Day10 implements ProblemSolver {
    private long result = 0;
    private static final Map<Character, Character> closing = new HashMap<>();
    private static final Map<Character, Character> opening = new HashMap<>();
    private static final Map<Character, Integer> debugScore = new HashMap<>();
    private static final String correctionScore = "0)]}>";

    private final List<Stack<Character>> incompleteLines = new ArrayList<>();
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

    @Override
    public void consider(String line) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char at = line.charAt(i);
            if (closing.containsKey(at)) {
                if (stack.isEmpty() || closing.get(at) != stack.pop()) {
                    result += debugScore.get(at);
                    return;
                }
            } else {
                stack.push(at);
            }
        }
        incompleteLines.add(stack);
    }

    @Override
    public void finished() {
        System.out.printf("total debug result in lines is %d%n", result);
        List<Long> allScores = new ArrayList<>();
        for (Stack<Character> incomplete : incompleteLines) {
            allScores.add(correctionScore(incomplete));
        }
        Collections.sort(allScores);
        int middleIndex = (allScores.size()-1)/2;
        System.out.printf("total correction result in lines is %d%n", allScores.get(middleIndex));
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
