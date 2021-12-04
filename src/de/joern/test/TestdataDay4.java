package de.joern.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestdataDay4 {
    public static Stream<String> getLines() throws IOException {
        Path path = Paths.get("testdata", "day4_test.txt");
        return Files.lines(path);
    }
}
