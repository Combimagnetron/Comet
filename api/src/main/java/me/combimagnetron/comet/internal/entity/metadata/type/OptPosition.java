package me.combimagnetron.comet.internal.entity.metadata.type;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import org.checkerframework.checker.nullness.qual.Nullable;

public record OptPosition(@Nullable Position position) implements MetadataType {
    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        final boolean present = position != null;
        buffer.write(ByteBuffer.Adapter.BOOLEAN, present);
        if (present) {
            buffer.nio().put(position.bytes());
        }
        return buffer.bytes();
    }
}
