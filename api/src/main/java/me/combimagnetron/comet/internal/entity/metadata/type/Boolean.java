package me.combimagnetron.comet.internal.entity.metadata.type;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Boolean(boolean val) implements MetadataType {

    public static Boolean of(boolean val) {
        return new Boolean(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.BOOLEAN, val);
        return buffer.bytes();
    }
}
