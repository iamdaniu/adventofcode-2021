package de.joern.day1;

import java.util.ArrayList;
import java.util.List;

public class Day1_2 extends IntProblemSolver {

    private List<Integer> window = new ArrayList<>();
    private int incCount = 0;

    @Override
    protected void consider(Integer integer) {
        List<Integer> currentWindow = new ArrayList<>(window);
        if (currentWindow.size() == 3) {
            currentWindow.remove(0);
            currentWindow.add(integer);
            int oldSum = sum(window);
            int newSum = sum(currentWindow);

            if (newSum > oldSum) {
                incCount++;
            }
            window = currentWindow;
        } else {
            window.add(integer);
        }
    }

    int sum(List<Integer> list) {
        int result = 0;
        for (Integer integer : list) {
            result += integer;
        }
        return result;
    }

    @Override
    public void finished() {
        System.out.println(incCount);
    }
}
