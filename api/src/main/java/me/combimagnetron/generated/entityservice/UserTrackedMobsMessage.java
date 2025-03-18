package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record UserTrackedMobsMessage(User user) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(user.serialize()));
    }

    public static UserTrackedMobsMessage of(User user) {
        return new UserTrackedMobsMessage(user);
    }

    public static UserTrackedMobsMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        User user = User.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new UserTrackedMobsMessage(user);
    }

    @Override()
    public int id() {
        return 136;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
