package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record GetEntitiesInBoxMessage(Vector3d min, Vector3d max) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(min.serialize()));
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(max.serialize()));
    }

    public static GetEntitiesInBoxMessage of(Vector3d min, Vector3d max) {
        return new GetEntitiesInBoxMessage(min, max);
    }

    public static GetEntitiesInBoxMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        Vector3d min = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        Vector3d max = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new GetEntitiesInBoxMessage(min, max);
    }

    @Override()
    public int id() {
        return 127;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
