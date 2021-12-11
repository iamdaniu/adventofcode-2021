package de.joern.day4;

public class Day4_1 extends Day4 {
    public long finished() {
        for (int draw : drawNumbers) {
            for (Board b : boards) {
                b.mark(draw);
                if (b.isWinning()) {
                    int boardScore = b.score();
                    int score = draw * boardScore;
                    System.out.printf("last number drawn: %d; winning board score %d - final score %d%n",
                            draw, boardScore, score);
                    return score;
                }
            }
        }
        return -1;
    }
}
