package me.combimagnetron.generated.satelliteserviceexample;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record TestMessage(java.lang.String string, java.lang.Integer i) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.STRING, string);
        buffer.write(ByteBuffer.Adapter.INT, i);
    }

    @Override()
    public int id() {
        return 1;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
