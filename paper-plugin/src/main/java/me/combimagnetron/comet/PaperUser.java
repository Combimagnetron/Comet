package me.combimagnetron.comet;

import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.connection.ConnectionImpl;
import me.combimagnetron.comet.data.impl.UserDataContainer;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.user.User;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaperUser implements User<Player> {
    private final UserDataContainer userDataContainer = null;
    private final CometPlugin plugin;
    private final Connection connection;
    private final Player player;
    private Instance instance;

    public PaperUser(Player player, CometPlugin cometPlugin) {
        this.player = player;
        this.plugin = cometPlugin;
        this.connection = ConnectionImpl.of(this, plugin);
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
}
