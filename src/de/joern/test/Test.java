package de.joern.test;

import de.joern.IntField;
import de.joern.Point;
import de.joern.Problems;
import de.joern.ProblemSolver;
import de.joern.day15.FieldMultiplier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Test {
    private static final Map<Problems, List<Long>> EXPECTED_RESULTS = new EnumMap<>(Problems.class);

    static {
        EXPECTED_RESULTS.put(Problems.DAY1, List.of(7L, 5L));
        EXPECTED_RESULTS.put(Problems.DAY2, List.of(150L, 900L));
        EXPECTED_RESULTS.put(Problems.DAY3, List.of(198L, 230L));
        EXPECTED_RESULTS.put(Problems.DAY4, List.of(4512L, 1924L));
        EXPECTED_RESULTS.put(Problems.DAY5, List.of(5L, 12L));
        EXPECTED_RESULTS.put(Problems.DAY6, List.of(5934L, 26984457539L));
        EXPECTED_RESULTS.put(Problems.DAY7, List.of(37L, 168L));
        EXPECTED_RESULTS.put(Problems.DAY8, List.of(26L, 61229L));
        EXPECTED_RESULTS.put(Problems.DAY9, List.of(11L, 1134L));
        EXPECTED_RESULTS.put(Problems.DAY10, List.of(26397L, 288957L));
        EXPECTED_RESULTS.put(Problems.DAY11, List.of(1656L, 195L));
        EXPECTED_RESULTS.put(Problems.DAY12, List.of(10L, 36L));
        EXPECTED_RESULTS.put(Problems.DAY13, List.of(17L));
        EXPECTED_RESULTS.put(Problems.DAY14, List.of(1588L, 2188189693529L));
        EXPECTED_RESULTS.put(Problems.DAY15, List.of(40L, 315L));
    }

    public static void main(String[] args) throws IOException {
        boolean result = testMultiplier();

        for (int i = 0; i < Problems.values().length; i++) {
            System.out.printf("Day %d%n", i+1);
            Problems problem = Problems.values()[i];
            List<Long> expected = EXPECTED_RESULTS.get(problem);

            List<ProblemSolver> solvers = problem.getSolvers();
            for (int solve = 0; solve < solvers.size(); solve++) {
                result &= test(solvers.get(solve), fromFile(i+1), expected.get(solve));
            }
        }
        System.out.println(result ? "all tests successful" : "test(s) failed");
    }

    private static boolean test(ProblemSolver solver, Stream<String> values, long expected) {
        values.forEach(solver::consider);
        long result = solver.finished();
        boolean correct = result == expected;
        System.out.println(correct ? "ok" : "failed!");
        return correct;
    }

    private static boolean testMultiplier() throws IOException {
        IntField original = new IntField();
        original.setValueAt(new Point(0, 0), 8);
        IntField expected = new IntField();
        Files.lines(Paths.get("data/test", "multiplied.txt"))
                .map(s -> s.replaceAll(" ", ""))
                .forEach(expected::add);
        IntField multiplied = FieldMultiplier.multiply(original);
        IntField.print(multiplied);
        return expected.allPoints()
                .filter(p -> multiplied.valueAt(p) != expected.valueAt(p))
                .findFirst()
                .isEmpty();
    }

    private static Stream<String> fromFile(int day) throws IOException {
        String filename = String.format("day%d.txt", day);
        Path path = Paths.get("data/test", filename);
        return Files.lines(path);
    }
}
