package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Void(java.lang.String v) implements me.combimagnetron.comet.data.Type<Void> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, v);
        return buffer.bytes();
    }

    public static Void of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.lang.String v = buffer.read(ByteBuffer.Adapter.STRING);
        return new Void(v);
    }

    @Override()
    public java.lang.Class<Void> type() {
        return Void.class;
    }
}
