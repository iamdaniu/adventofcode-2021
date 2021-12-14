package de.joern.day14;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 implements ProblemSolver {
    private final int insertionCount;
    private List<PolymerPair> initialPairs;
    private final Map<PolymerPair, Character> insertions = new HashMap<>();

    private Day14(int insertionCount) {
        this.insertionCount = insertionCount;
    }

    public static Day14 day14_1() {
        return new Day14(10);
    }
    public static Day14 day14_2() {
        return new Day14(40);
    }

    @Override
    public void consider(String line) {
        if (!line.isEmpty()) {
            if (initialPairs == null) {
                PolymerPairBuilder builder = new PolymerPairBuilder();
                for (int i = 0; i < line.length(); i++) {
                    builder.accept(line.charAt(i));
                }
                initialPairs = builder.getPairs();
            } else {
                String[] split = line.split(" -> ");
                PolymerPair pair = new PolymerPair(split[0].charAt(0), split[0].charAt(1));
                insertions.put(pair, split[1].charAt(0));
            }
        }
    }

    @Override
    public long finished() {
        final List<GenerationStore> generationByStep = new ArrayList<>();
        Map<PolymerPair, Long> pairCount = initialPairs.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Character, Long> initialCounts = toPolymerStream(initialPairs)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        GenerationStore store = new GenerationStore(pairCount, initialCounts);
        generationByStep.add(store);

        for (int i = 0; i < insertionCount; i++) {
            GenerationStore lastStep = generationByStep.get(i);
            Map<PolymerPair, Long> newPairs = new HashMap<>();
            Map<Character, Long> generated = new HashMap<>(lastStep.generatedCount());
            lastStep.getNewPairs().forEach((pair, count) -> {
                generated.merge(insertions.get(pair), count, Long::sum);
                insert(pair).forEach(p -> newPairs.merge(p, count, Long::sum));
            });
            GenerationStore newStep = new GenerationStore(newPairs, generated);
            generationByStep.add(newStep);
        }

        LongSummaryStatistics stats = generationByStep.get(generationByStep.size()-1)
                .generatedCount().values().stream()
                .mapToLong(l -> l)
                .summaryStatistics();
        long result = stats.getMax() - stats.getMin();
        System.out.printf("occurrences of most common: %d; least common: %d; difference: %d%n",
                stats.getMax(), stats.getMin(), result);
        return result;
    }

    private static Stream<Character> toPolymerStream(List<PolymerPair> pairs) {
        return Stream.concat(Stream.of(pairs.get(0).left()),
                pairs.stream()
                .map(PolymerPair::right));
    }

    private Stream<PolymerPair> insert(PolymerPair into) {
        char toInsert = insertions.get(into);
        return Stream.of(new PolymerPair(into.left(), toInsert),
                new PolymerPair(toInsert, into.right()));
    }

    static class GenerationStore {
        public Map<PolymerPair, Long> getNewPairs() {
            return newPairs;
        }

        private final Map<PolymerPair, Long> newPairs;

        public Map<Character, Long> generatedCount() {
            return totalGeneratedCount;
        }

        private final Map<Character, Long> totalGeneratedCount;

        GenerationStore(Map<PolymerPair, Long> newPairs, Map<Character, Long> count) {
            this.newPairs = newPairs;
            this.totalGeneratedCount = count;
        }

    }

    static class PolymerPairBuilder {
        private final List<PolymerPair> pairs = new ArrayList<>();

        private Character lastChar;
        public void accept(char c) {
            if (lastChar != null) {
                pairs.add(new PolymerPair(lastChar, c));
            }
            lastChar = c;
        }

        public List<PolymerPair> getPairs() {
            return pairs;
        }
    }

    static record PolymerPair(char left, char right) {
        @Override
        public String toString() {
            return String.format("%s%s", left, right);
        }
    }
}
