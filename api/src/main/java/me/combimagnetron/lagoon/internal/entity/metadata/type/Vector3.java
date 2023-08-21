package me.combimagnetron.lagoon.internal.entity.metadata.type;

import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public record Vector3(float x, float y, float z) implements MetadataType {

    public static Vector3 vec3(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, x)
                .write(ByteBuffer.Adapter.FLOAT, y)
                .write(ByteBuffer.Adapter.FLOAT, z);
        return buffer.bytes();
    }
}
