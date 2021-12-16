package de.joern.day16;

import de.joern.day16.Day16.PacketHeader;

import java.util.Collections;
import java.util.List;

class Literal implements PacketContent {
    BitView view;
    private int length, itemCount;

    public Literal(BitView parseFrom) {
//        System.out.printf("parsing %s as a literal%n", parseFrom);
        // search first index of value that is has first bit set to 0
        length = PacketHeader.LENGTH;
        while (length < parseFrom.length() && parseFrom.isSet(length)) {
            length += 5;
            itemCount++;
        }
        // now, length contains first position of last value; one more step till end of actual data
        length += 5;
        // pad with zeroes yields total length
        view = parseFrom.subView(0, this.length);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public PacketHeader getHeader() {
        return view.getHeader();
    }

    @Override
    public List<PacketContent> getContained() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return String.format("literal version %d", getHeader().version());
    }
}
