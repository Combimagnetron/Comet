package me.combimagnetron.generated;

import java.lang.Byte;
import java.lang.Override;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record UserSendMinecraftPacketMessage(Byte[] data) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.BYTE_ARRAY, data);
    }

    public static UserSendMinecraftPacketMessage of(Byte[] data) {
        return new UserSendMinecraftPacketMessage(data);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 2;
    }
}
