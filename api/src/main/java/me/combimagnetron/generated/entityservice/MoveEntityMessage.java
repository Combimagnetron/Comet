package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record MoveEntityMessage(Entity entity, Vector3d position) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(entity.serialize()));
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(position.serialize()));
    }

    public static MoveEntityMessage of(Entity entity, Vector3d position) {
        return new MoveEntityMessage(entity, position);
    }

    public static MoveEntityMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        Entity entity = Entity.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        Vector3d position = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new MoveEntityMessage(entity, position);
    }

    @Override()
    public int id() {
        return 128;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
