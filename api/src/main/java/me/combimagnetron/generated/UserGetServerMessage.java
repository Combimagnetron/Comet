package me.combimagnetron.generated;

import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record UserGetServerMessage(UUID uuid) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
    }

    public static UserGetServerMessage of(UUID uuid) {
        return new UserGetServerMessage(uuid);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 0;
    }
}
