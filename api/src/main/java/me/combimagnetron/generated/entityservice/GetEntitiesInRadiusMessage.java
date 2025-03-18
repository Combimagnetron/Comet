package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record GetEntitiesInRadiusMessage(Vector3d position, java.lang.Double radius) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(position.serialize()));
        buffer.write(ByteBuffer.Adapter.DOUBLE, radius);
    }

    public static GetEntitiesInRadiusMessage of(Vector3d position, java.lang.Double radius) {
        return new GetEntitiesInRadiusMessage(position, radius);
    }

    public static GetEntitiesInRadiusMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        Vector3d position = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        java.lang.Double radius = buffer.read(ByteBuffer.Adapter.DOUBLE);
        return new GetEntitiesInRadiusMessage(position, radius);
    }

    @Override()
    public int id() {
        return 126;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
