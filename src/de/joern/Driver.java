package de.joern;

import de.joern.day1.Day1_1;
import de.joern.day1.Day1_2;
import de.joern.day2.Day2_1;
import de.joern.day2.Day2_2;
import de.joern.day3.Day3_1;
import de.joern.day3.Day3_2;
import de.joern.day4.Day4_1;
import de.joern.day4.Day4_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Driver {

    public static void main(String[] args) {
        for (PROBLEMS p : PROBLEMS.values()) {
            System.out.println(p);
            execute(p);
        }
    }

    public static void execute(PROBLEMS problem) {
        final var file = new File(problem.getFilename());
        final var handler = problem.getSolver();
        try (FileInputStream fileStream = new FileInputStream(file);
             Scanner sc = new Scanner(fileStream)) {

            while (sc.hasNextLine()) {
                final var line = sc.nextLine();
                handler.consider(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.finished();
    }

    enum PROBLEMS {
        DAY1_1("data\\day1.txt", Day1_1::new),
        DAY1_2("data\\day1.txt", Day1_2::new),
        DAY2_1("data\\day2.txt", Day2_1::new),
        DAY2_2("data\\day2.txt", Day2_2::new),
        DAY3_1("data\\day3.txt", Day3_1::new),
        DAY3_2("data\\day3.txt", Day3_2::new),
        DAY4_1("data\\day4.txt", Day4_1::new),
        DAY4_2("data\\day4.txt", Day4_2::new)
        ;

        private final String filename;
        private final Supplier<ProblemSolver> solver;

        PROBLEMS(String filename, Supplier<ProblemSolver> solver) {
            this.filename = filename;
            this.solver = solver;
        }

        public String getFilename() {
            return filename;
        }

        public ProblemSolver getSolver() {
            return solver.get();
        }
    }
}

