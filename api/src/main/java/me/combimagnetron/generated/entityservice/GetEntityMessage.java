package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record GetEntityMessage(java.util.UUID uuid) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
    }

    public static GetEntityMessage of(java.util.UUID uuid) {
        return new GetEntityMessage(uuid);
    }

    public static GetEntityMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.util.UUID uuid = buffer.read(ByteBuffer.Adapter.UUID);
        return new GetEntityMessage(uuid);
    }

    @Override()
    public int id() {
        return 125;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
