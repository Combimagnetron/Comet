package me.combimagnetron.generated;

import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record UserLeaveNetworkMessage(UUID uuid, UUID instance) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
        buffer.write(ByteBuffer.Adapter.UUID, instance);
    }

    public static UserLeaveNetworkMessage of(UUID uuid, UUID instance) {
        return new UserLeaveNetworkMessage(uuid, instance);
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
