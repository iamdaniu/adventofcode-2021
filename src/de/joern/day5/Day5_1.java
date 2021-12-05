package de.joern.day5;

public class Day5_1 extends Day5 {
    public Day5_1() {
        super(l -> l.isHorizontal() || l.isVertical());
    }
}
