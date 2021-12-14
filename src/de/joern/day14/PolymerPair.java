package de.joern.day14;

record PolymerPair(char left, char right) {
    @Override
    public String toString() {
        return String.format("%s%s", left, right);
    }
}
