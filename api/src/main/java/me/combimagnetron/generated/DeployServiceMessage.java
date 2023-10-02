package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.service.Deployment;

public record DeployServiceMessage(Identifier identifier,
        Deployment deployment) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
        buffer.write(ByteBuffer.Adapter.DEPLOYMENT, deployment);
    }

    public static DeployServiceMessage of(Identifier identifier, Deployment deployment) {
        return new DeployServiceMessage(identifier, deployment);
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
