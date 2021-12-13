package de.joern.day12;

import de.joern.ProblemSolver;

import java.util.*;

public class Day12 implements ProblemSolver {
    private final Graph graph = new Graph();

    @Override
    public void consider(String line) {
        String[] split = line.split("-");
        graph.addConnection(split[0], split[1]);
    }

    @Override
    public long finished() {
        final List<Path> result = new ArrayList<>();
        final Stack<Path> paths = new Stack<>();
        final Path currentPath = new Path().add(graph.getStartNode());
        paths.push(currentPath);
        while (!paths.isEmpty()) {
            Path p = paths.pop();
            System.out.printf("starting for path %s%n", p);
            for (Path further : appendAll(p, graph.connections(p.lastNode()))) {
                System.out.printf(" - checking path %s%n", further);
                if (further.lastNode().equals(graph.getEndNode())) {
                    System.out.println("it's an end path");
                    result.add(further);
                } else {
                    paths.push(further);
                }
            }
        }
        result.forEach(System.out::println);
        System.out.printf("found %d paths%n", result.size());
        return result.size();
    }

    public List<Path> appendAll(Path candidate, Collection<Node> nodes) {
        List<Path> result = new ArrayList<>(nodes.size());
        for (Node n : nodes) {
            if (n.isBig() || !candidate.contains(n)) {
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
