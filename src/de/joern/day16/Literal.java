package de.joern.day16;

import de.joern.day16.Day16.PacketHeader;

import java.util.Collections;
import java.util.List;

class Literal implements PacketContent {
    BitView view;
    private int length;
    long value;

    public Literal(BitView parseFrom) {
        // search first index of value that is has first bit set to 0
        length = PacketHeader.LENGTH;
        var itemCount = 0;
        while (length < parseFrom.length() && parseFrom.isSet(length)) {
            length += 5;
            itemCount++;
        }
        // now, length contains first position of last value; one more step till end of actual data
        length += 5;
        // pad with zeroes yields total length
        view = parseFrom.subView(0, this.length);
        value = getValue(itemCount);
    }

    @Override
    public long getValue() {
        return value;
    }

    public long getValue(int itemCount) {
        long result = 0;
        for (int i = 0; i <= itemCount; i++) {
            int index = PacketHeader.LENGTH + i*5 + 1;
            BitView currentView = view.subView(index, 4);
            result += currentView.toInt();
        }
        return result;
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
        return String.valueOf(getValue());
//        return String.format("literal version %d value %d", getHeader().version(), getValue());
    }
}
