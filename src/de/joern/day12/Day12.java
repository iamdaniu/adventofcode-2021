package de.joern.day12;

import de.joern.ProblemSolver;

import java.util.*;
import java.util.function.BiPredicate;

public class Day12 implements ProblemSolver {
    private final Graph graph = new Graph();

    private final BiPredicate<Path, Node> canAdd;

    private Day12(BiPredicate<Path, Node> canAdd) {
        this.canAdd = canAdd;
    }

    public static Day12 day12_1() {
        return new Day12((p, n) -> n.isBig() || !p.contains(n));
    }

    public static Day12 day12_2() {
        return new Day12((p, n) -> n.isBig() ||
                !p.contains(n) ||
                allSmallOnlyOnce(p));
    }

    private static boolean allSmallOnlyOnce(Path p) {
        Set<Node> existing = new HashSet<>();
        for (Node n : p.nodes) {
            if (n.isBig()) {
                continue;
            }
            if (!existing.add(n)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void consider(String line) {
        String[] split = line.split("-");
        graph.addConnection(split[0], split[1]);
    }

    @Override
    public long finished() {
        final Set<Path> result = new LinkedHashSet<>();
        final Stack<Path> paths = new Stack<>();
        final Path currentPath = new Path().add(graph.getStartNode());
        paths.push(currentPath);
        while (!paths.isEmpty()) {
            Path p = paths.pop();
            for (Path further : appendAll(p, graph.connections(p.lastNode()))) {
                if (further.lastNode().equals(graph.getEndNode())) {
                    result.add(further);
                } else {
                    paths.push(further);
                }
            }
        }
        System.out.printf("found %d paths%n", result.size());
        return result.size();
    }

    public List<Path> appendAll(Path candidate, Collection<Node> nodes) {
        List<Path> result = new ArrayList<>(nodes.size());
        for (Node n : nodes) {
            if (canAdd.test(candidate, n) && !n.equals(graph.getStartNode())) {
                Path newPath = new Path(candidate.nodes).add(n);
                result.add(newPath);
            }
        }
        return result;
    }

    static class Graph {
        private final Node startNode;
        private final Node endNode;

        private final Map<Node, Set<Node>> connections = new HashMap<>();

        Graph() {
            this.startNode = new Node("start");
            this.endNode = new Node("end");
        }
        public void addConnection(String from, String to) {
            connections.computeIfAbsent(new Node(from), key -> new HashSet<>())
                    .add(new Node(to));
            connections.computeIfAbsent(new Node(to), key -> new HashSet<>())
                    .add(new Node(from));
        }

        public Node getStartNode() {
            return startNode;
        }

        public Node getEndNode() {
            return endNode;
        }

        public Set<Node> connections(Node node) {
            return connections.getOrDefault(node, Collections.emptySet());
        }
    }
    static class Path {
        private final List<Node> nodes = new ArrayList<>();

        public Path() {
        }
        public Path(List<Node> nodes) {
            this.nodes.addAll(nodes);
        }
        public boolean contains(Node n) {
            return nodes.contains(n);
        }

        public Path add(Node node) {
            nodes.add(node);
            return this;
        }

        public Node lastNode() {
            return nodes.get(nodes.size()-1);
        }

        @Override
        public String toString() {
            return nodes.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return Objects.equals(nodes, path.nodes);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodes);
        }
    }
    static record Node(String name) {
        public boolean isBig() {
            return Character.isUpperCase(name.charAt(0));
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
