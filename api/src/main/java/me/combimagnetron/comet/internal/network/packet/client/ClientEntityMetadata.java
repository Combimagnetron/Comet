package me.combimagnetron.comet.internal.network.packet.client;

import me.combimagnetron.comet.internal.entity.Entity;
import me.combimagnetron.comet.internal.entity.metadata.Metadata;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.internal.network.packet.ClientPacket;
import me.combimagnetron.comet.internal.network.packet.Packet;

public class ClientEntityMetadata implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final Metadata metadata;

    public static ClientEntityMetadata entityMetadata(Entity entity) {
        return new ClientEntityMetadata(entity);
    }

    private ClientEntityMetadata(Entity entity) {
        this.byteBuffer = ByteBuffer.empty();
        this.metadata = entity.type().metadata();

    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        return new byte[0];
    }

}
