package de.joern.day4;

import java.util.ArrayList;
import java.util.List;

public class Day4_2 extends Day4 {
    private final List<Board> winningBoards = new ArrayList<>();

    @Override
    public long finished() {
        for (int draw : drawNumbers) {
            for (Board b : boards) {
                if (winningBoards.contains(b)) {
                    continue;
                }
                b.mark(draw);
                if (b.isWinning()) {
                    winningBoards.add(b);
                    if (winningBoards.size() == boards.size()) {
                        int boardScore = b.score();
                        int score = draw * boardScore;
                        System.out.printf("last number drawn: %d; winning board score %d - final score %d%n",
                                draw, boardScore, score);
                        return score;
                    }
                }
            }
        }
        return -1;
    }
}
