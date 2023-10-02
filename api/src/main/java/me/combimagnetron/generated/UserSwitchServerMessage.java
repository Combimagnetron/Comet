package me.combimagnetron.generated;

import java.lang.Integer;
import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record UserSwitchServerMessage(UUID user_id, Integer server_type) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, user_id);
        buffer.write(ByteBuffer.Adapter.INT, server_type);
    }

    public static UserSwitchServerMessage of(UUID user_id, Integer server_type) {
        return new UserSwitchServerMessage(user_id, server_type);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 1;
    }
}
