package me.combimagnetron.generated;

import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

record UserGetServerMessage(UUID user_id) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, user_id);
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
