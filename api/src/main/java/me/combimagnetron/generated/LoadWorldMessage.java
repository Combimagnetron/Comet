package me.combimagnetron.generated;

import java.lang.Override;
import java.lang.String;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public record LoadWorldMessage(String name) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.STRING, name);
    }

    public static LoadWorldMessage of(String name) {
        return new LoadWorldMessage(name);
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
