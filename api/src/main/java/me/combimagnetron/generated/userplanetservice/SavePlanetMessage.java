package me.combimagnetron.generated.userplanetservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record SavePlanetMessage(User user, java.util.UUID planetId, java.lang.String data) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(user.serialize()));
        buffer.write(ByteBuffer.Adapter.UUID, planetId);
        buffer.write(ByteBuffer.Adapter.STRING, data);
    }

    public static SavePlanetMessage of(User user, java.util.UUID planetId, java.lang.String data) {
        return new SavePlanetMessage(user, planetId, data);
    }

    public static SavePlanetMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        User user = User.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        java.util.UUID planetId = buffer.read(ByteBuffer.Adapter.UUID);
        java.lang.String data = buffer.read(ByteBuffer.Adapter.STRING);
        return new SavePlanetMessage(user, planetId, data);
    }

    @Override()
    public int id() {
        return 18;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
