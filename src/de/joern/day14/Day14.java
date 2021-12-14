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
                initialPairs = parse(line);
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
        Map<PolymerPair, Long> pairCount = countOccurrences(initialPairs.stream());
        Map<Character, Long> initialCounts = countOccurrences(toPolymerStream(initialPairs));
        GenerationStore store = new GenerationStore(pairCount, initialCounts);
        generationByStep.add(store);

        for (int i = 0; i < insertionCount; i++) {
            GenerationStore lastStep = generationByStep.get(i);
            Map<PolymerPair, Long> newPairs = new HashMap<>();
            Map<Character, Long> generated = new HashMap<>(lastStep.totalCount());
            lastStep.getNewPairs().forEach((pair, count) -> {
                generated.merge(insertions.get(pair), count, Long::sum);
                insert(pair).forEach(p -> newPairs.merge(p, count, Long::sum));
            });
            GenerationStore newStep = new GenerationStore(newPairs, generated);
            generationByStep.add(newStep);
        }

        LongSummaryStatistics stats = generationByStep.get(generationByStep.size()-1)
                .totalCount().values().stream()
                .mapToLong(l -> l)
                .summaryStatistics();
        long result = stats.getMax() - stats.getMin();
        System.out.printf("occurrences of most common: %d; least common: %d; difference: %d%n",
                stats.getMax(), stats.getMin(), result);
        return result;
    }

    private static List<PolymerPair> parse(String line) {
        final List<PolymerPair> pairs = new ArrayList<>();
        Character lastChar = null;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (lastChar != null) {
                pairs.add(new PolymerPair(lastChar, c));
            }
            lastChar = c;
        }
        return pairs;
    }

    private Stream<PolymerPair> insert(PolymerPair into) {
        char toInsert = insertions.get(into);
        return Stream.of(new PolymerPair(into.left(), toInsert),
                new PolymerPair(toInsert, into.right()));
    }

    private static Stream<Character> toPolymerStream(final List<PolymerPair> pairs) {
        return Stream.concat(Stream.of(pairs.get(0).left()),
                pairs.stream()
                .map(PolymerPair::right));
    }

    static class GenerationStore {

        private final Map<PolymerPair, Long> newPairs;
        private final Map<Character, Long> totalCount;
        public Map<PolymerPair, Long> getNewPairs() {
            return newPairs;
        }

        public Map<Character, Long> totalCount() {
            return totalCount;
        }

        GenerationStore(Map<PolymerPair, Long> newPairs, Map<Character, Long> count) {
            this.newPairs = newPairs;
            this.totalCount = count;
        }

    }
    private static <T> Map<T, Long> countOccurrences(final Stream<T> in) {
        return in.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
