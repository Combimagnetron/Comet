package me.combimagnetron.generated;

import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record InstanceHeartbeatMessage(UUID instance) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, instance);
    }

    public static InstanceHeartbeatMessage of(UUID instance) {
        return new InstanceHeartbeatMessage(instance);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 2;
    }
}
