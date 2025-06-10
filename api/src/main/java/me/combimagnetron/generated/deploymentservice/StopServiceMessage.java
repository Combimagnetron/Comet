package me.combimagnetron.generated.deploymentservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record StopServiceMessage(me.combimagnetron.comet.data.Identifier identifier) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
    }

    public static StopServiceMessage of(me.combimagnetron.comet.data.Identifier identifier) {
        return new StopServiceMessage(identifier);
    }

    public static StopServiceMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        me.combimagnetron.comet.data.Identifier identifier = buffer.read(ByteBuffer.Adapter.IDENTIFIER);
        return new StopServiceMessage(identifier);
    }

    @Override()
    public int id() {
        return 23;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
