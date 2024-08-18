package me.combimagnetron.generated.satelliteserviceexample;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record TestType(java.lang.String name, java.lang.Integer age) implements me.combimagnetron.comet.data.Type<TestType> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, name);
        buffer.write(ByteBuffer.Adapter.INT, age);
        return buffer.bytes();
    }

    @Override()
    public java.lang.Class<TestType> type() {
        return TestType.class;
    }
}
