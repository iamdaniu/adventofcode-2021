package de.joern.day16;

import de.joern.day16.Day16.PacketHeader;

class BitView {
    private final boolean[] bits;
    private final int offset, length;

    BitView(boolean[] bits) {
        this(bits, 0, bits.length);
    }

    BitView(boolean[] bits, int offset, int length) {
        this.bits = bits;
        this.offset = offset;
        this.length = length;
    }

    public boolean isSet(int index) {
        return bits[offset+index];
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            builder.append(bits[i] ? "1" : "0");
        }
        return builder.toString();
    }

    BitView subView(int offset) {
        return subView(offset, length - offset);
    }
    BitView subView(int offset, int length) {
        return new BitView(bits, this.offset + offset, length);
    }

    long toInt() {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result <<= 1;
            result += (isSet(i) ? 1 : 0);
        }
        return result;
    }

    PacketHeader getHeader() {
        int version = (int) subView(0, 3).toInt();
        int type = (int) subView(3, 3).toInt();
        return new PacketHeader(version, type);
    }

    public int length() {
        return length;
    }
}
