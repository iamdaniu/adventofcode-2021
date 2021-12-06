package de.joern.day1;

import java.util.ArrayList;
import java.util.List;

public class Day1_2 extends Day1_1 {
    private final List<Integer> window = new ArrayList<>();

    @Override
    protected void consider(Integer integer) {
        window.add(integer);
        if (window.size() >= 3) {
            if (window.size() > 3) {
                window.remove(0);
            }
            int sum = sum(window);
            super.consider(sum);
        }
    }

    int sum(List<Integer> list) {
        int result = 0;
        for (Integer integer : list) {
            result += integer;
        }
        return result;
    }
}
