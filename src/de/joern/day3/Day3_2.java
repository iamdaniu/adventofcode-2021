package de.joern.day3;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day3_2 implements ProblemSolver {
    private final List<BitSet> values = new ArrayList<>();
    private int inputLength = 0;

    @Override
    public void consider(String line) {
        inputLength = line.length();
        BitSet digitSet = new BitSet(line.length());
        for (int i = 0; i < line.length(); i++) {
            boolean digit = line.charAt(i) == '1';
            digitSet.set(i, digit);
        }
        values.add(digitSet);
    }

    @Override
    public long finished() {
        final int oxygen = getFilteredValue(false);
        final int co2 = getFilteredValue(true);
        int result = oxygen * co2;
        System.out.printf("oxygen: %d; co2: %d - combined: %d%n", oxygen, co2, result);
        return result;
    }

    @SuppressWarnings("SimplifiableConditionalExpression")
    private int getFilteredValue(boolean invert) {
        List<BitSet> candidates = new ArrayList<>(values);
        for (int i = 0; i < inputLength; i++) {
            if (candidates.size() == 1) {
                break;
            }
            boolean mostCommon = mostCommonAt(candidates, i);
            boolean filterFor = invert
                    ? !mostCommon
                    : mostCommon;
            candidates = filterBitAt(candidates, i, filterFor);
        }
        return toValue(candidates.get(0));
    }

    private boolean mostCommonAt(List<BitSet> toFilter, int position) {
        MostCommonBitFinder bitFinder = new MostCommonBitFinder();
        toFilter.forEach(bs -> bitFinder.consider(bs.get(position)));
        return bitFinder.getMostCommonBit();

        // also works
//        final var byBitSet = toFilter.stream()
//                .collect(Collectors.partitioningBy(bs -> bs.get(position)));
//        return byBitSet.get(true).size() >= byBitSet.get(false).size();
    }
    private List<BitSet> filterBitAt(List<BitSet> toFilter, int position, boolean value) {
        return toFilter.stream()
                .filter(bs -> bs.get(position) == value)
                .collect(Collectors.toList());
    }

    private int toValue(BitSet set) {
        int result = 0;
        for (int i = 0; i < inputLength; i++) {
            result = (result << 1) + (set.get(i) ? 1 : 0);
        }
        return result;
    }

}

