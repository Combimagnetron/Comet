package me.combimagnetron.generated;

import java.lang.Integer;
import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record UserSwitchServerMessage(UUID userId, Integer serverType) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, userId);
        buffer.write(ByteBuffer.Adapter.INT, serverType);
    }

    public static UserSwitchServerMessage of(UUID userId, Integer serverType) {
        return new UserSwitchServerMessage(userId, serverType);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 3;
    }
}
