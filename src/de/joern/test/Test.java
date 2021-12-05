package de.joern.test;

import de.joern.ProblemSolver;
import de.joern.day4.Day4_1;
import de.joern.day4.Day4_2;
import de.joern.day5.Day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println("Day 4");
        test(new Day4_1(), fromFile(4));
        test(new Day4_2(), fromFile(4));
        System.out.println("Day 5");
        test(Day5.day5_1(), fromFile(5));
        test(Day5.day5_2(), fromFile(5));
    }

    private static void test(ProblemSolver solver, Stream<String> values) {
        values.forEach(solver::consider);
        solver.finished();
    }

    private static Stream<String> fromFile(int day) throws IOException {
        String filename = String.format("day%d_test.txt", day);
        Path path = Paths.get("testdata", filename);
        return Files.lines(path);
    }
}
