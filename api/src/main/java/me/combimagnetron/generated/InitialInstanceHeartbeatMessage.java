package me.combimagnetron.generated;

import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.service.Deployment;

public record InitialInstanceHeartbeatMessage(UUID instance,
        Deployment deployment) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, instance);
        buffer.write(ByteBuffer.Adapter.DEPLOYMENT, deployment);
    }

    public static InitialInstanceHeartbeatMessage of(UUID instance, Deployment deployment) {
        return new InitialInstanceHeartbeatMessage(instance, deployment);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 1;
    }
}
