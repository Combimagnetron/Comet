package me.combimagnetron.comet.internal.network.packet.client;

import me.combimagnetron.comet.internal.entity.Entity;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.internal.network.packet.ClientPacket;

import java.util.UUID;

public class ClientSpawnEntity implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final Entity.EntityId entityId;
    private final UUID uuid;
    private final Entity.Type type;
    private final Vector3 position;


    private ClientSpawnEntity(Entity entity) {
        this.byteBuffer = ByteBuffer.empty();
        this.entityId = entity.id();
        this.uuid = entity.uuid();
        this.type = entity.type();
        this.position = entity.position();
    }

    private ClientSpawnEntity(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.entityId = Entity.EntityId.of(read(ByteBuffer.Adapter.VAR_INT));
        this.uuid = read(ByteBuffer.Adapter.UUID);
        this.type = Entity.Type.find(read(ByteBuffer.Adapter.VAR_INT));
        this.position = Vector3.vec3(read(ByteBuffer.Adapter.FLOAT), read(ByteBuffer.Adapter.FLOAT),  read(ByteBuffer.Adapter.FLOAT));
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        return byteBuffer.bytes();
    }
}
