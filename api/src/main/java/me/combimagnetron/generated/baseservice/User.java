package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record User(java.util.UUID uuid, java.lang.String username, java.util.UUID instance, me.combimagnetron.comet.data.Identifier channel, DataContainer data, Vector3d position, me.combimagnetron.comet.data.Identifier location) implements me.combimagnetron.comet.data.Type<User> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.UUID, uuid);
        buffer.write(ByteBuffer.Adapter.STRING, username);
        buffer.write(ByteBuffer.Adapter.UUID, instance);
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, channel);
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(data.serialize()));
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, org.apache.commons.lang3.ArrayUtils.toObject(position.serialize()));
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, location);
        return buffer.bytes();
    }

    public static User of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        java.util.UUID uuid = buffer.read(ByteBuffer.Adapter.UUID);
        java.lang.String username = buffer.read(ByteBuffer.Adapter.STRING);
        java.util.UUID instance = buffer.read(ByteBuffer.Adapter.UUID);
        me.combimagnetron.comet.data.Identifier channel = buffer.read(ByteBuffer.Adapter.IDENTIFIER);
        DataContainer data = DataContainer.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        Vector3d position = Vector3d.of(org.apache.commons.lang3.ArrayUtils.toPrimitive(buffer.read(ByteBuffer.Adapter.BYTE_ARRAY)));
        me.combimagnetron.comet.data.Identifier location = buffer.read(ByteBuffer.Adapter.IDENTIFIER);
        return new User(uuid, username, instance, channel, data, position, location);
    }

    @Override()
    public java.lang.Class<User> type() {
        return User.class;
    }
}
