package de.joern.day8;

import de.joern.ProblemSolver;

import java.util.*;

public class Day8_2 implements ProblemSolver {
    private long sum;

    @Override
    public void consider(String line) {
        boolean atInput = true;
        Decider decider = new Decider();
        StringBuilder stringBuilder = new StringBuilder();
        for (String token : line.split(" ")) {
            if ("|".equals(token)) {
                atInput = false;
                continue;
            }
            if (atInput) {
                decider.consider(token);
            } else {
                stringBuilder.append(decider.decide(token));
            }
        }
        sum += Long.parseLong(stringBuilder.toString());
    }

    @Override
    public void finished() {
        System.out.printf("sum of all line outputs is %d%n", sum);
    }

    static BitSet toBitset(String input) {
        BitSet result = new BitSet(7);
        for (char i = 'a'; i < 'h'; i++) {
            if (input.contains("" + i)) {
                result.set(i - 'a');
            }
        }
        return result;
    }

    static class Decider {
        private BitSet four;
        private BitSet seven;
        public void consider(String segments) {
            if (segments.length() == 4 && four == null) {
                four = toBitset(segments);
            } else if (segments.length() == 3 && seven == null) {
                seven = toBitset(segments);
            }
        }
        public int decide(String segments) {
            return switch (segments.length()) {
                case 2 -> 1;
                case 3 -> 7;
                case 4 -> 4;
                case 5 -> decide235(segments);
                case 6 -> decide069(segments);
                default -> 8;
            };
        }

        private int decide235(String segments) {
            BitSet set = toBitset(segments);
            set.and(four);
            if (countSetBits(set) == 2) {
                return 2;
            }
            set = toBitset(segments);
            set.and(seven);
            return countSetBits(set) == 2
                    ? 5
                    : 3;
        }

        private int decide069(String segments) {
            BitSet set = toBitset(segments);
            set.and(four);
            if (countSetBits(set) == 4) {
                return 9;
            }
            set = toBitset(segments);
            set.and(seven);
            return countSetBits(set) == 2
                    ? 6
                    : 0;
        }

        private int countSetBits(BitSet set) {
            int result = 0;
            for (int i = 0; i < set.length(); i++) {
                if (set.get(i)) {
                    result++;
                }
            }
            return result;
        }
    }
}
