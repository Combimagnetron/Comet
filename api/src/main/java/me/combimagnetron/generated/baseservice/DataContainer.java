package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record DataContainer(java.lang.String data) implements me.combimagnetron.comet.data.Type<DataContainer> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, data);
        return buffer.bytes();
    }

    public static DataContainer of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.lang.String data = buffer.read(ByteBuffer.Adapter.STRING);
        return new DataContainer(data);
    }

    @Override()
    public java.lang.Class<DataContainer> type() {
        return DataContainer.class;
    }
}
