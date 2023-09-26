package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public record StopServiceMessage(Identifier identifier) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
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
