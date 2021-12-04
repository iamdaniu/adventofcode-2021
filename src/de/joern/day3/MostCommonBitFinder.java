package de.joern.day3;

public class MostCommonBitFinder {
    private int count;

    public void consider(boolean digit) {
        count = digit
                ? count+1
                : count - 1;
    }

    public boolean getMostCommonBit() {
        return count >= 0;
    }

    public static void main(String[] args) {
        final var mostCommonBitFinder = new MostCommonBitFinder();
        mostCommonBitFinder.consider(true);
        mostCommonBitFinder.consider(false);
        System.out.println(mostCommonBitFinder.getMostCommonBit());
    }

    public int getCount() {
        return count;
    }
}
