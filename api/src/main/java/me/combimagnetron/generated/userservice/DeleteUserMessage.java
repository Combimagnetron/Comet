package me.combimagnetron.generated.userservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record DeleteUserMessage(java.util.UUID uuid) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
    }

    public static DeleteUserMessage of(java.util.UUID uuid) {
        return new DeleteUserMessage(uuid);
    }

    public static DeleteUserMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.util.UUID uuid = buffer.read(ByteBuffer.Adapter.UUID);
        return new DeleteUserMessage(uuid);
    }

    @Override()
    public int id() {
        return 27;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
