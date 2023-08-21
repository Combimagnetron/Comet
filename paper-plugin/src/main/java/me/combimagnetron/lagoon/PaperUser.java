package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.connection.ConnectionImpl;
import me.combimagnetron.lagoon.data.impl.UserDataContainer;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.Connection;
import me.combimagnetron.lagoon.user.User;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaperUser implements User<Player> {
    private final UserDataContainer userDataContainer = null;
    private final LagoonPlugin plugin;
    private final Connection connection;
    private final Player player;
    private Instance instance;

    public PaperUser(Player player, LagoonPlugin lagoonPlugin) {
        this.player = player;
        this.plugin = lagoonPlugin;
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
