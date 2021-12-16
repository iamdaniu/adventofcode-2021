package de.joern.day16;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum Op {
    PLUS(0, between("+")),
    TIMES(1, between("*")),
    MIN(2, surrounding("min")),
    MAX(3, surrounding("max")),
    GREATER(5, between(">")),
    SMALLER(6, between("<")),
    EQUAL(7, between("=="));
    private final int type;
    private final Function<List<?>, String> createString;

    Op(int type, Function<List<?>, String> symbol) {
        this.type = type;
        this.createString = symbol;
    }

    public String toString(List<?> contents) {
        return createString.apply(contents);
    }

    static Op fromType(int type) {
        return Stream.of(Op.values())
                .filter(o -> o.type == type)
                .findFirst()
                .orElseThrow();
    }

    static Function<List<?>, String> between(String symbol) {
        return s -> s.stream().map(Object::toString).collect(Collectors.joining(symbol));
    }

    static Function<List<?>, String> surrounding(String prefix) {
        return s -> String.format("%s(%s)", prefix, s.stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
