package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record OnlineUsersResponseMessage(
        java.util.List<me.combimagnetron.comet.user.User> users) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.writeCollection(ByteBuffer.Adapter.USER, users);
    }

    public static OnlineUsersResponseMessage of(
            java.util.List<me.combimagnetron.comet.user.User> users) {
        return new OnlineUsersResponseMessage(users);
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
