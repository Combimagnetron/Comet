package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.message.user.UserMessageChannel;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.data.impl.UserDataContainer;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.user.User;
import net.minestom.server.entity.Player;

import java.util.UUID;

public class UserImpl implements User<Player> {
    private final Connection connection;
    private final Player player;

    public UserImpl(Player player) {
        this.player = player;
        this.connection = new ConnectionImpl(this);
    }

    public UserImpl(ByteBuffer buffer) {
        this.connection = null;
        this.player = null;
    }

    @Override
    public Player platformSpecificPlayer() {
        return player;
    }

    @Override
    public String name() {
        return player.getUsername();
    }

    @Override
    public UserMessageChannel messageChannel() {
        return null;
    }

    @Override
    public Instance instance() {
        return null;
    }

    @Override
    public UserDataContainer playerData() {
        return null;
    }

    @Override
    public UUID uniqueIdentifier() {
        return player.getUuid();
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Vector3d position() {
        return new Vector3d(player.getPosition().x(), player.getPosition().y(), player.getPosition().z());
    }

    @Override
    public Identifier location() {
        return null;
    }

    @Override
    public me.combimagnetron.generated.baseservice.User sat() {
        return null;
    }
}
