package me.combimagnetron.generated.baseservice;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public record Vector3d() implements me.combimagnetron.comet.data.Type<Vector3d> {

    @Override()
    public byte[] serialize() {
        final ByteBuffer buffer = ByteBuffer.empty();
        return buffer.bytes();
    }

    public static Vector3d of(byte[] bytes) {
        final ByteBuffer buffer = ByteBuffer.of(bytes);
        return new Vector3d();
    }

    @Override()
    public java.lang.Class<Vector3d> type() {
        return Vector3d.class;
    }
}
