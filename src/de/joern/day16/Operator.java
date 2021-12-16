package de.joern.day16;

import de.joern.day16.Day16.PacketHeader;

import java.util.ArrayList;
import java.util.List;

public class Operator implements PacketContent {
    private final int length;
    private final List<PacketContent> content = new ArrayList<>();
    private final PacketHeader header;

    public Operator(BitView parseFrom) {
        header = parseFrom.getHeader();
        int len = PacketHeader.LENGTH + 1;
//        System.out.printf("parsing %s as an operator version %d%n", parseFrom, header.version());
        if (parseFrom.isSet(PacketHeader.LENGTH)) {
            len += 11;
            // parse n subpackets
            int parseOffset = PacketHeader.LENGTH + 1;
            int subpackageCount = parseFrom.subView(parseOffset, 11).toInt();
            BitView subview = parseFrom.subView(parseOffset + 11);
//            System.out.printf("parsing %d subpackets in %s%n", subpackageCount, subview);
            content.addAll(ContentParser.parseMulti(subview, subpackageCount));
        } else {
            len += 15;
            int subpackageLength = parseFrom.subView(PacketHeader.LENGTH + 1, 15).toInt();
            BitView subview = parseFrom.subView(len, subpackageLength);
//            System.out.printf("parsing %s for subpackets%n", subview);
            content.addAll(ContentParser.parseMulti(subview));
        }
        length = len + content.stream().mapToInt(PacketContent::getLength).sum();
//        System.out.printf("parsed %s%n", this);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public PacketHeader getHeader() {
        return header;
    }

    @Override
    public List<PacketContent> getContained() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("operator version %d %s", getHeader().version(), getContained());
//        return "Operator length " + getLength() +" {header=" + getHeader() +
//                ", content=" + content +
//                '}';
    }
}
