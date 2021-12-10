package de.joern;

import de.joern.day1.Day1_1;
import de.joern.day1.Day1_2;
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
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Driver {

    public static void main(String[] args) {
        for (PROBLEMS p : PROBLEMS.values()) {
            execute(p);
        }
    }

    public static void execute(PROBLEMS problem) {
        try {
            System.out.printf("Day %d%n", problem.day);
            List<ProblemSolver> solvers = problem.getSolvers();
            for (int i = 0; i < solvers.size(); i++) {
                System.out.printf(" %d-%d%n", problem.day, i+1);
                ProblemSolver solver = solvers.get(i);
                Files.lines(problem.getFile()).forEach(solver::consider);
                solver.finished();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum PROBLEMS {
        DAY1(1, Day1_1::new, Day1_2::new),
        DAY2(2, Day2_1::new, Day2_2::new),
        DAY3(3, Day3_1::new, Day3_2::new),
        DAY4(4, Day4_1::new, Day4_2::new),
        DAY5(5, Day5::day5_1, Day5::day5_2),
        DAY6(6, Day6::day6_1, Day6::day6_2),
        DAY7(7, Day7::day7_1, Day7::day7_2),
        DAY8(8, Day8_1::new, Day8_2::new),
        DAY9(9, Day9_1::new, Day9_2::new),
        DAY10(10, Day10::new),
        ;

        public final int day;
        private final List<Supplier<ProblemSolver>> solver;

        @SafeVarargs
        PROBLEMS(int day, Supplier<ProblemSolver>... solver) {
            this.day = day;
            this.solver = Arrays.asList(solver);
        }

        public Path getFile() {
            String filename = String.format("day%d.txt", day);
            return Paths.get("data", filename);
        }

        public List<ProblemSolver> getSolvers() {
            return solver.stream()
                    .map(Supplier::get)
                    .collect(Collectors.toList());
        }
    }
}

