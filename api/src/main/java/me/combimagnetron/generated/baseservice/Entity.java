package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Entity(java.lang.String data) implements me.combimagnetron.comet.data.Type<Entity> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, data);
        return buffer.bytes();
    }

    public static Entity of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.lang.String data = buffer.read(ByteBuffer.Adapter.STRING);
        return new Entity(data);
    }

    @Override()
    public java.lang.Class<Entity> type() {
        return Entity.class;
    }
}
