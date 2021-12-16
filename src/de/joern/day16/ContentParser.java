package de.joern.day16;

import de.joern.day16.Day16.PacketHeader;

import java.util.ArrayList;
import java.util.List;

public class ContentParser {
    static PacketContent parseContent(BitView view) {
        PacketHeader header = view.getHeader();
        PacketContent parsed;
        if (header.type() == 4) {
            parsed = new Literal(view);
        } else {
            parsed = new Operator(view);
        }
        return parsed;
    }
    static List<PacketContent> parseMulti(BitView parseFrom) {
        return parseMulti(parseFrom, Integer.MAX_VALUE);
    }
    static List<PacketContent> parseMulti(BitView parseFrom, long max) {
        List<PacketContent> result = new ArrayList<>();
        int parsedLength = 0;
        BitView toParse = parseFrom;
        do {
            toParse = toParse.subView(parsedLength);
            if (toParse.length() < PacketHeader.LENGTH) {
                break;
            }
            PacketContent content = parseContent(toParse);
            result.add(content);
            parsedLength = content.getLength();
        } while (parsedLength < toParse.length()
                && result.size() < max);
        return result;
    }
}
