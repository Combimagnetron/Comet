package me.combimagnetron.generated;

import java.lang.Override;
import java.util.UUID;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.DataObject;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record SyncDataContainerMessage(Identifier identifier, UUID sync,
        DataObject data) implements Message {
    @Override
    public void write() {
        final ByteBuffer buffer = buffer();
        buffer.write(ByteBuffer.Adapter.IDENTIFIER, identifier);
        buffer.write(ByteBuffer.Adapter.UUID, sync);
        buffer.write(ByteBuffer.Adapter.DATA_OBJECT, data);
    }

    public static SyncDataContainerMessage of(Identifier identifier, UUID sync, DataObject data) {
        return new SyncDataContainerMessage(identifier, sync, data);
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.empty();
    }

    @Override
    public int id() {
        return 3;
    }
}
