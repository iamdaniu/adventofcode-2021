package de.joern.day16;

import de.joern.ProblemSolver;

import java.util.ArrayList;
import java.util.List;

import static de.joern.day16.Day16.*;

public class Day16 implements ProblemSolver {
    final List<BitView> transmissions = new ArrayList<>();

    @Override
    public void consider(String line) {
        boolean[] bits = new boolean[line.length() * 4];
        for (int charIndex = 0; charIndex < line.length(); charIndex++) {
            String charString = line.charAt(charIndex) + "";
            int value = Integer.parseInt(charString, 16);
            for (int i = 0; i < 4; i++) {
                int andingWith = 1 << i;
                boolean set = (value & andingWith) != 0;
                bits[charIndex * 4 + (3 - i)] = set;
            }
        }
        BitView transmission = new BitView(bits);
        transmissions.add(transmission);
    }

    @Override
    public long finished() {
        int result = 0;
        for (BitView view : transmissions) {
            PacketContent content = ContentParser.parseContent(view);
            var version = versionSum(content);
            result += version;
            System.out.println(content);
            System.out.printf("value is %d%n", content.getValue());
        }
        System.out.printf("total version sum %d%n", result);
        return result;
    }
    private int versionSum(PacketContent content) {
        int result = content.getHeader().version();
        result += content.getContained().stream()
                .mapToInt(this::versionSum)
                .sum();
        return result;
    }

    record PacketHeader(int version, int type) {
        public static final int LENGTH = 6;
    }
}

interface PacketContent {
    long getValue();
    int getLength();
    PacketHeader getHeader();
    List<PacketContent> getContained();
}