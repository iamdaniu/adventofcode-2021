package de.joern.day16;

import de.joern.day16.Day16.PacketHeader;

import java.util.ArrayList;
import java.util.List;

public class Operator implements PacketContent {
    private final int length;
    private final List<PacketContent> content = new ArrayList<>();
    private final PacketHeader header;
    private final long value;

    public Operator(BitView parseFrom) {
        header = parseFrom.getHeader();
        int len = PacketHeader.LENGTH + 1;
        if (parseFrom.isSet(PacketHeader.LENGTH)) {
            len += 11;
            // parse n subpackets
            int parseOffset = PacketHeader.LENGTH + 1;
            long subpackageCount = parseFrom.subView(parseOffset, 11).toInt();
            BitView subview = parseFrom.subView(parseOffset + 11);
            content.addAll(ContentParser.parseMulti(subview, subpackageCount));
        } else {
            len += 15;
            int subpackageLength = (int) parseFrom.subView(PacketHeader.LENGTH + 1, 15).toInt();
            BitView subview = parseFrom.subView(len, subpackageLength);
            content.addAll(ContentParser.parseMulti(subview));
        }
        length = len + content.stream().mapToInt(PacketContent::getLength).sum();
        value = calcValue();
        System.out.printf("%s = %d%n", this, value);
    }

    @Override
    public long getValue() {
        return value;
    }

    public long calcValue() {
        return switch (header.type()) {
            case 0 ->
                    // sum
                    content.stream().mapToLong(PacketContent::getValue).sum();
            case 1 ->
                    // product
                    content.stream().mapToLong(PacketContent::getValue).reduce(1L, (i1, i2) -> i1 * i2);
            case 2 ->
                    // min
                    content.stream().mapToLong(PacketContent::getValue).min().orElseThrow();
            case 3 ->
                    // max
                    content.stream().mapToLong(PacketContent::getValue).max().orElseThrow();
            case 5 ->
                    // greater than
                    content.get(0).getValue() > content.get(1).getValue() ? 1 : 0;
            case 6 ->
                    // less than
                    content.get(0).getValue() < content.get(1).getValue() ? 1 : 0;
            case 7 ->
                    // greater than
                    content.get(0).getValue() == content.get(1).getValue() ? 1 : 0;
            default -> 0;
        };
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
        return String.format("(%s)", Op.fromType(getHeader().type()).toString(content));
//        return String.format("operator version %d type %d %s", getHeader().version(), getHeader().type(), getContained());
    }
}
