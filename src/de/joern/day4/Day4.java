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

    @Override
    public void finished() {
        for (int i = 0; i < boards.size(); i++) {
            System.out.println("Board " + i);
            System.out.println(boards.get(i));
        }
    }

    private class Parser {
        private int currentRow;
        private Board currentBoard;

        void accept(String line) {
            if (drawNumbers == null) {
//                System.out.println("parsing numbers to draw");
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
//                    System.out.println("starting board");
                    currentBoard = new Board(row.length);
                    boards.add(currentBoard);
                }
                currentBoard.setRow(currentRow++, row);
            } else if (currentBoard != null) {
//                System.out.println("ending board\n" + currentBoard);
                currentBoard = null;
                currentRow = 0;
            }
        }
    }
}
