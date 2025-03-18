package me.combimagnetron.generated.deploymenthealthservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record InitialInstanceHeartbeatMessage(java.util.UUID uuid, Deployment deployment) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(deployment.serialize()));
    }

    public static InitialInstanceHeartbeatMessage of(java.util.UUID uuid, Deployment deployment) {
        return new InitialInstanceHeartbeatMessage(uuid, deployment);
    }

    public static InitialInstanceHeartbeatMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.util.UUID uuid = buffer.read(ByteBuffer.Adapter.UUID);
        Deployment deployment = Deployment.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new InitialInstanceHeartbeatMessage(uuid, deployment);
    }

    @Override()
    public int id() {
        return 19;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
