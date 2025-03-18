package me.combimagnetron.generated.deploymentservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record DeploymentStatusMessage(me.combimagnetron.comet.data.Identifier identifier) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
    }

    public static DeploymentStatusMessage of(me.combimagnetron.comet.data.Identifier identifier) {
        return new DeploymentStatusMessage(identifier);
    }

    public static DeploymentStatusMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        me.combimagnetron.comet.data.Identifier identifier = buffer.read(ByteBuffer.Adapter.IDENTIFIER);
        return new DeploymentStatusMessage(identifier);
    }

    @Override()
    public int id() {
        return 22;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
