package de.joern;

import de.joern.day1.Day1_1;
import de.joern.day1.Day1_2;
import de.joern.day10.Day10;
import de.joern.day11.Day11;
import de.joern.day12.Day12;
import de.joern.day13.Day13;
import de.joern.day14.Day14;
import de.joern.day15.Day15;
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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum Problems {
    DAY1(1, Day1_1::new, Day1_2::new),
    DAY2(2, Day2_1::new, Day2_2::new),
    DAY3(3, Day3_1::new, Day3_2::new),
    DAY4(4, Day4_1::new, Day4_2::new),
    DAY5(5, Day5::day5_1, Day5::day5_2),
    DAY6(6, Day6::day6_1, Day6::day6_2),
    DAY7(7, Day7::day7_1, Day7::day7_2),
    DAY8(8, Day8_1::new, Day8_2::new),
    DAY9(9, Day9_1::new, Day9_2::new),
    DAY10(10, Day10::day10_1, Day10::day10_2),
    DAY11(11, Day11::day11_1, Day11::day11_2),
    DAY12(12, Day12::day12_1, Day12::day12_2),
    DAY13(13, Day13::new),
    DAY14(14, Day14::day14_1, Day14::day14_2),
    DAY15(15, Day15::new),
    ;

    public final int day;

    private final List<Supplier<ProblemSolver>> solver;
    @SafeVarargs
    Problems(int day, Supplier<ProblemSolver>... solver) {
        this.day = day;
        this.solver = Arrays.asList(solver);
    }

    public int getDay() {
        return day;
    }

    public List<ProblemSolver> getSolvers() {
        return solver.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
    }
}
