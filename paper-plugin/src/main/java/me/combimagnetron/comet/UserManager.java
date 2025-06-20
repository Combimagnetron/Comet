package me.combimagnetron.comet;

import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.event.EventSubscription;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.comet.user.UserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.*;

public class UserManager implements Listener, UserHandler<Player> {
    @Inject
    CometBase<JavaPlugin> cometBase;
    private final HashMap<UUID, User<Player>> USER_MAP = new HashMap<>();
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User<Player> playerUser = new PaperUser(player, cometBase);
        USER_MAP.put(player.getUniqueId(), playerUser);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        USER_MAP.remove(player.getUniqueId());
    }

    @Override
    public User<Player> user(Player player) {
        return USER_MAP.get(player.getUniqueId());
    }

    public Optional<User<Player>> user(UUID player) {
        return Optional.of(USER_MAP.get(player));
    }

    @Override
    public Optional<User<Player>> user(String name) {
        return USER_MAP.values().stream().filter(user -> Objects.equals(user.name(), name)).findAny();
    }

    @Override
    public Collection<User<Player>> users() {
        return USER_MAP.values();
    }

    @Override
    public Collection<User<Player>> global() {
        return null;
    }

    @Override
    public User<Player> deserialize(ByteBuffer buffer) {
        return new PaperUser(buffer);
    }

}
