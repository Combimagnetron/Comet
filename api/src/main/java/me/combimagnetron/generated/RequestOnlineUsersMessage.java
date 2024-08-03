package me.combimagnetron.generated;

import java.lang.Override;
import java.lang.String;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record RequestOnlineUsersMessage(String scope) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.STRING, scope);
    }

    public static RequestOnlineUsersMessage of(String scope) {
        return new RequestOnlineUsersMessage(scope);
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
