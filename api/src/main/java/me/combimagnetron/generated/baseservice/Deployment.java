package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Deployment(me.combimagnetron.comet.data.Identifier name, java.lang.String image, java.lang.Integer minReplicas, java.lang.Integer maxReplicas, java.lang.Integer playerInstanceThreshold) implements me.combimagnetron.comet.data.Type<Deployment> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, name);
        buffer.write(ByteBuffer.Adapter.STRING, image);
        buffer.write(ByteBuffer.Adapter.INT, minReplicas);
        buffer.write(ByteBuffer.Adapter.INT, maxReplicas);
        buffer.write(ByteBuffer.Adapter.INT, playerInstanceThreshold);
        return buffer.bytes();
    }

    public static Deployment of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        me.combimagnetron.comet.data.Identifier name = buffer.read(ByteBuffer.Adapter.IDENTIFIER);
        java.lang.String image = buffer.read(ByteBuffer.Adapter.STRING);
        java.lang.Integer minReplicas = buffer.read(ByteBuffer.Adapter.INT);
        java.lang.Integer maxReplicas = buffer.read(ByteBuffer.Adapter.INT);
        java.lang.Integer playerInstanceThreshold = buffer.read(ByteBuffer.Adapter.INT);
        return new Deployment(name, image, minReplicas, maxReplicas, playerInstanceThreshold);
    }

    @Override()
    public java.lang.Class<Deployment> type() {
        return Deployment.class;
    }
}
