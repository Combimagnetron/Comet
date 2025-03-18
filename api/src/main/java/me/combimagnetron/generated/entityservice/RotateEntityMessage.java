package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record RotateEntityMessage(Entity entity, Vector3d rotation) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(entity.serialize()));
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(rotation.serialize()));
    }

    public static RotateEntityMessage of(Entity entity, Vector3d rotation) {
        return new RotateEntityMessage(entity, rotation);
    }

    public static RotateEntityMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        Entity entity = Entity.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        Vector3d rotation = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new RotateEntityMessage(entity, rotation);
    }

    @Override()
    public int id() {
        return 129;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
