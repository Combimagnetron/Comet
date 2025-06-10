package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Packet(java.lang.String data) implements me.combimagnetron.comet.data.Type<Packet> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, data);
        return buffer.bytes();
    }

    public static Packet of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.lang.String data = buffer.read(ByteBuffer.Adapter.STRING);
        return new Packet(data);
    }

    @Override()
    public java.lang.Class<Packet> type() {
        return Packet.class;
    }
}
