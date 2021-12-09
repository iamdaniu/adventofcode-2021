package de.joern.test;

import de.joern.ProblemSolver;
import de.joern.day4.Day4_1;
import de.joern.day4.Day4_2;
import de.joern.day5.Day5;
import de.joern.day6.Day6;
import de.joern.day7.Day7;
import de.joern.day8.Day8_1;
import de.joern.day8.Day8_2;
import de.joern.day9.Day9_1;
import de.joern.day9.Day9_2;

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
        test(new Day6(80), fromFile(6));
        test(new Day6(256), fromFile(6));
        test(Day7.day7_1(), fromFile(7));
        test(Day7.day7_2(), fromFile(7));
        test(new Day8_1(), fromFile(8));
        test(new Day8_2(), fromFile(8));
        test(new Day9_1(), fromFile(9));
        test(new Day9_2(), fromFile(9));
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
