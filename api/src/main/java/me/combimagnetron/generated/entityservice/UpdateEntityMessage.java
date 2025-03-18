package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record UpdateEntityMessage(Entity entity) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(entity.serialize()));
    }

    public static UpdateEntityMessage of(Entity entity) {
        return new UpdateEntityMessage(entity);
    }

    public static UpdateEntityMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        Entity entity = Entity.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new UpdateEntityMessage(entity);
    }

    @Override()
    public int id() {
        return 131;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
