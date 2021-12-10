package de.joern;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Day10 implements ProblemSolver {
    long result = 0;
    Map<Character, Character> closing = new HashMap<>();
    Map<Character, Integer> score = new HashMap<>();
    {
        closing.put(')', '(');
        closing.put(']', '[');
        closing.put('}', '{');
        closing.put('>', '<');
        score.put(')', 3);
        score.put(']', 57);
        score.put('}', 1197);
        score.put('>', 25137);
    }

    @Override
    public void consider(String line) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char at = line.charAt(i);
            if (closing.containsKey(at)) {
                if (stack.isEmpty() || closing.get(at) != stack.pop()) {
                    result += score.get(at);
                    break;
                }
            } else {
                stack.push(at);
            }
        }
    }

    @Override
    public void finished() {
        System.out.printf("total result in lines is %d%n", result);
    }
}
