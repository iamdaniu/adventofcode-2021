package de.joern.test;

import de.joern.ProblemSolver;
import de.joern.day5.Day5_2;

import java.io.IOException;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        test(new Day5_2(), Testdata.fromFile(5));
    }

    public static void test(ProblemSolver solver, Stream<String> values) {
        values.forEach(solver::consider);
        solver.finished();
    }
}
