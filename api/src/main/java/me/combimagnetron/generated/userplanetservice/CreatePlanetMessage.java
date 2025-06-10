package me.combimagnetron.generated.userplanetservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.generated.baseservice.*;

public record CreatePlanetMessage(User user, java.lang.String name, Vector3d position) implements me.combimagnetron.comet.communication.Message {

    @Override()
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE, (byte) this.id());
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(user.serialize()));
        buffer.write(ByteBuffer.Adapter.STRING, name);
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(position.serialize()));
    }

    public static CreatePlanetMessage of(User user, java.lang.String name, Vector3d position) {
        return new CreatePlanetMessage(user, name, position);
    }

    public static CreatePlanetMessage of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        User user = User.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        java.lang.String name = buffer.read(ByteBuffer.Adapter.STRING);
        Vector3d position = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        return new CreatePlanetMessage(user, name, position);
    }

    @Override()
    public int id() {
        return 15;
    }

    @Override()
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }
}
