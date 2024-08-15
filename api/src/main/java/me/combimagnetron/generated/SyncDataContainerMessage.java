package me.combimagnetron.generated;

import java.lang.Override;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.DataContainer;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record SyncDataContainerMessage(Identifier identifier,
        DataContainer data) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
        buffer.write(ByteBuffer.Adapter.DATA_CONTAINER, data);
    }

    public static SyncDataContainerMessage of(Identifier identifier, DataContainer data) {
        return new SyncDataContainerMessage(identifier, data);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 6;
    }
}
