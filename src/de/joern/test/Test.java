package de.joern.test;

import de.joern.day1.Day1_1;
import de.joern.day1.Day1_2;
import de.joern.day10.Day10;
import de.joern.ProblemSolver;
import de.joern.day11.Day11;
import de.joern.day12.Day12;
import de.joern.day2.Day2_1;
import de.joern.day2.Day2_2;
import de.joern.day3.Day3_1;
import de.joern.day3.Day3_2;
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
        boolean result;
        System.out.println("Day 1");
        result = test(new Day1_1(), fromFile(1), 7);
        result &= test(new Day1_2(), fromFile(1), 5);
        System.out.println("Day 2");
        result &= test(new Day2_1(), fromFile(2), 150);
        result &= test(new Day2_2(), fromFile(2), 900);
        System.out.println("Day 3");
        result &= test(new Day3_1(), fromFile(3), 198);
        result &= test(new Day3_2(), fromFile(3), 230);
        System.out.println("Day 4");
        result &= test(new Day4_1(), fromFile(4), 4512);
        result &= test(new Day4_2(), fromFile(4), 1924);
        System.out.println("Day 5");
        result &= test(Day5.day5_1(), fromFile(5), 5);
        result &= test(Day5.day5_2(), fromFile(5), 12);
        System.out.println("Day 6");
        result &= test(new Day6(80), fromFile(6), 5934);
        result &= test(new Day6(256), fromFile(6), 26984457539L);
        System.out.println("Day 7");
        result &= test(Day7.day7_1(), fromFile(7), 37);
        result &= test(Day7.day7_2(), fromFile(7), 168);
        System.out.println("Day 8");
        result &= test(new Day8_1(), fromFile(8), 26);
        result &= test(new Day8_2(), fromFile(8), 61229);
        System.out.println("Day 9");
        result &= test(new Day9_1(), fromFile(9), 11);
        result &= test(new Day9_2(), fromFile(9), 1134);
        System.out.println("Day 10");
        result &= test(Day10.day10_1(), fromFile(10), 26397);
        result &= test(Day10.day10_2(), fromFile(10), 288957);
        System.out.println("Day 11");
        result &= test(Day11.day11_1(), fromFile(11), 1656);
        result &= test(Day11.day11_2(), fromFile(11), 195);
        System.out.println("Day 12");
        result &= test(Day12.day12_1(), fromFile(12), 10);
        result &= test(Day12.day12_2(), fromFile(12), 36);

        System.out.println(result ? "all tests successful" : "test(s) failed");
    }

    private static boolean test(ProblemSolver solver, Stream<String> values, long expected) {
        values.forEach(solver::consider);
        long result = solver.finished();
        boolean correct = result == expected;
        System.out.println(correct ? "ok" : "failed!");
        return correct;
    }

    private static Stream<String> fromFile(int day) throws IOException {
        String filename = String.format("day%d.txt", day);
        Path path = Paths.get("data/test", filename);
        return Files.lines(path);
    }
}
