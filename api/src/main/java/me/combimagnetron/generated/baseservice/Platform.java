package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Platform(java.lang.String name, java.util.UUID uuid, java.lang.Boolean isProxy) implements me.combimagnetron.comet.data.Type<Platform> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, name);
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
        buffer.write(ByteBuffer.Adapter.BOOLEAN, isProxy);
        return buffer.bytes();
    }

    public static Platform of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.lang.String name = buffer.read(ByteBuffer.Adapter.STRING);
        java.util.UUID uuid = buffer.read(ByteBuffer.Adapter.UUID);
        java.lang.Boolean isProxy = buffer.read(ByteBuffer.Adapter.BOOLEAN);
        return new Platform(name, uuid, isProxy);
    }

    @Override()
    public java.lang.Class<Platform> type() {
        return Platform.class;
    }
}
