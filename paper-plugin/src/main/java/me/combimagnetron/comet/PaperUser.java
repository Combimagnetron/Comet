package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.message.user.UserMessageChannel;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.connection.ConnectionImpl;
import me.combimagnetron.comet.data.impl.UserDataContainer;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.user.User;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PaperUser implements User<Player> {
    private final UserDataContainer userDataContainer;
    private final CometBase<?> plugin;
    private final Connection connection;
    private final UserMessageChannel userMessageChannel;
    private final Player player;
    private Instance instance;

    public PaperUser(Player player, CometBase<?> cometPlugin) {
        this.userDataContainer = new UserDataContainer();
        this.player = player;
        this.plugin = cometPlugin;
        this.userMessageChannel = UserMessageChannel.redis(Identifier.of("player", player.getUniqueId().toString()), this);
        this.connection = ConnectionImpl.of(this, (CometBase<JavaPlugin>) plugin);
    }


    /*
1. UUID -> Unique Identifier
2. String -> Name
3. UUId -> Instance
4. Identifier -> Message Channel
5. DataContainer -> Player Data
6. 3x Double -> Vector3d Position
*/
    public PaperUser(ByteBuffer byteBuffer) {
        this.player = Bukkit.getPlayer(byteBuffer.read(ByteBuffer.Adapter.UUID));
        byteBuffer.read(ByteBuffer.Adapter.STRING);
        /*this.instance = CometBase.comet().instance(*/byteBuffer.read(ByteBuffer.Adapter.UUID);//);

        this.plugin = CometBase.comet();
        this.userMessageChannel = UserMessageChannel.redis(byteBuffer.read(ByteBuffer.Adapter.IDENTIFIER), this);;
        this.userDataContainer = (UserDataContainer) byteBuffer.read(ByteBuffer.Adapter.DATA_CONTAINER);
        this.connection = ConnectionImpl.of(this, (CometBase<JavaPlugin>) plugin);
    }

    @Override
    public Player platformSpecificPlayer() {
        return player;
    }

    @Override
    public String name() {
        return player.getName();
    }

    @Override
    public void message(Component component) {
        player.sendMessage(component);
    }

    @Override
    public UserMessageChannel messageChannel() {
        return null;
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
        return player.getUniqueId();
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Vector3d position() {
        final Location location = player.getLocation();
        return Vector3d.vec3(location.x(), location.y(), location.z());
    }



    public User<Player> deserialize(ByteBuffer buffer) {
        return new PaperUser(buffer);
    }
}
