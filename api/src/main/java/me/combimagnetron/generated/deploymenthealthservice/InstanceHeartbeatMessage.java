package me.combimagnetron.generated.deploymenthealthservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record InstanceHeartbeatMessage(java.util.UUID uuid) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
    }

    public static InstanceHeartbeatMessage of(java.util.UUID uuid) {
        return new InstanceHeartbeatMessage(uuid);
    }

    public static InstanceHeartbeatMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.util.UUID uuid = buffer.read(ByteBuffer.Adapter.UUID);
        return new InstanceHeartbeatMessage(uuid);
    }

    @Override()
    public int id() {
        return 20;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
