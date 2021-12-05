package de.joern.day5;

public class Day5_2 extends Day5 {
    public Day5_2() {
        super(l -> l.isHorizontal() ||
                l.isVertical() ||
                l.isDiagonal());
    }
}
