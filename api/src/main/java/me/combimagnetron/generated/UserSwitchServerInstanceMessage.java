package me.combimagnetron.generated;

import java.lang.Override;
import java.lang.String;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.service.Deployment;

public record UserSwitchServerInstanceMessage(UUID uuid, Deployment deployment, String service,
        UUID old, UUID current) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
        buffer.write(ByteBuffer.Adapter.DEPLOYMENT, deployment);
        buffer.write(ByteBuffer.Adapter.STRING, service);
        buffer.write(ByteBuffer.Adapter.UUID, old);
        buffer.write(ByteBuffer.Adapter.UUID, current);
    }

    public static UserSwitchServerInstanceMessage of(UUID uuid, Deployment deployment,
            String service, UUID old, UUID current) {
        return new UserSwitchServerInstanceMessage(uuid, deployment, service, old, current);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 4;
    }
}
