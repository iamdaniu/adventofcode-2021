package de.joern.day4;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Day4 implements ProblemSolver {
    protected List<Integer> drawNumbers;
    protected final List<Board> boards = new ArrayList<>();

    private final Parser parser = new Parser();

    @Override
    public final void consider(String line) {
        parser.accept(line);
    }

    private class Parser {
        private int currentRow;
        private Board currentBoard;

        void accept(String line) {
            if (drawNumbers == null) {
                drawNumbers = Stream.of(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } else if (!line.isEmpty()) {
                String[] split = line.split(" +");
                final var row = Stream.of(split)
                        .filter(s -> !s.isBlank())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                if (currentBoard == null) {
                    currentBoard = new Board(row.length);
                    boards.add(currentBoard);
                }
                currentBoard.setRow(currentRow++, row);
            } else if (currentBoard != null) {
                currentBoard = null;
                currentRow = 0;
            }
        }
    }
}
