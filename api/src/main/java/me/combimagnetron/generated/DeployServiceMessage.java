package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import me.combimagnetron.lagoon.service.Deployment;

public record DeployServiceMessage(Identifier identifier, Deployment deployment) implements Message {
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
