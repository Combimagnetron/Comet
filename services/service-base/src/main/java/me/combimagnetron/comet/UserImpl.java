package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.message.user.UserMessageChannel;
import me.combimagnetron.comet.data.impl.UserDataContainer;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.user.User;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;

import java.util.UUID;

public class UserImpl implements User<Audience> {
    private final String name;
    private final UUID uuid;
    private final UserMessageChannel userMessageChannel;
    private final Instance instance;
    private final UserDataContainer userDataContainer;
    private final Vector3d position;
    private final Connection connection;

        /*
    1. UUID -> Unique Identifier
    2. String -> Name
    3. UUId -> Instance
    4. Identifier -> Message Channel
    5. DataContainer -> Player Data
    6. 3x Double -> Vector3d Position
    */
    public UserImpl(ByteBuffer byteBuffer) {
        this.uuid = byteBuffer.read(ByteBuffer.Adapter.UUID);
        this.name = byteBuffer.read(ByteBuffer.Adapter.STRING);
        this.instance = CometBase.comet().instances().instance(byteBuffer.read(ByteBuffer.Adapter.UUID));
        this.userMessageChannel = UserMessageChannel.redis(byteBuffer.read(ByteBuffer.Adapter.IDENTIFIER), this);
        this.userDataContainer = (UserDataContainer) byteBuffer.read(ByteBuffer.Adapter.DATA_CONTAINER);
        this.position = new Vector3d(byteBuffer.read(ByteBuffer.Adapter.DOUBLE), byteBuffer.read(ByteBuffer.Adapter.DOUBLE), byteBuffer.read(ByteBuffer.Adapter.DOUBLE));
        this.connection = new ConnectionImpl(this);
    }


    @Override
    public Audience platformSpecificPlayer() {
        return Audience.empty();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public UserMessageChannel messageChannel() {
        return userMessageChannel;
    }

    @Override
    public Instance instance() {
        return instance;
    }

    @Override
    public UserDataContainer playerData() {
        return userDataContainer;
    }

    @Override
    public UUID uniqueIdentifier() {
        return uuid;
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Vector3d position() {
        return position;
    }

}
