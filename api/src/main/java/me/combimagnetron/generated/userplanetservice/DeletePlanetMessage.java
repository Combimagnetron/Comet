package me.combimagnetron.generated.userplanetservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record DeletePlanetMessage(User user, java.util.UUID planetId) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(user.serialize()));
        buffer.write(ByteBuffer.Adapter.UUID, planetId);
    }

    public static DeletePlanetMessage of(User user, java.util.UUID planetId) {
        return new DeletePlanetMessage(user, planetId);
    }

    public static DeletePlanetMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        User user = User.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        java.util.UUID planetId = buffer.read(ByteBuffer.Adapter.UUID);
        return new DeletePlanetMessage(user, planetId);
    }

    @Override()
    public int id() {
        return 16;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
