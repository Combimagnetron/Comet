package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public record StartServiceMessage(Identifier identifier) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
    }

    public static StartServiceMessage of(Identifier identifier) {
        return new StartServiceMessage(identifier);
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
