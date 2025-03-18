package me.combimagnetron.generated.userservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record UpdateUserMessage(User user) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(user.serialize()));
    }

    public static UpdateUserMessage of(User user) {
        return new UpdateUserMessage(user);
    }

    public static UpdateUserMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        User user = User.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new UpdateUserMessage(user);
    }

    @Override()
    public int id() {
        return 26;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
