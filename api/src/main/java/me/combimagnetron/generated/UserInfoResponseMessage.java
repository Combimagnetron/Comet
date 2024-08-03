package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.user.User;

public record UserInfoResponseMessage(User user) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.USER, user);
    }

    public static UserInfoResponseMessage of(User user) {
        return new UserInfoResponseMessage(user);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 7;
    }
}
