package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;

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
        return 4;
    }
}
