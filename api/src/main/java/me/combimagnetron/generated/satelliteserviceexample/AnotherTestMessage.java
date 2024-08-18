package me.combimagnetron.generated.satelliteserviceexample;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record AnotherTestMessage(java.lang.String string, TestType testType) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.STRING, string);
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(testType.serialize()));
    }

    @Override()
    public int id() {
        return 2;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
