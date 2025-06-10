package me.combimagnetron.comet;

import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.comet.user.UserHandler;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.jetbrains.annotations.NotNull;


import java.util.*;

public class UserManager implements UserHandler<Player> {
    private final HashMap<UUID, User<Player>> USER_MAP = new HashMap<>();
    private final @NotNull EventNode<Event> JOIN_LISTENER = MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerConfigurationEvent.class, this::onJoin);
    private final @NotNull EventNode<Event> QUIT_LISTENER = MinecraftServer.getGlobalEventHandler().addListener(PlayerDisconnectEvent.class, this::onQuit);


    public void onJoin(AsyncPlayerConfigurationEvent event) {
        Player player = event.getPlayer();
        User<Player> playerUser = new UserImpl(player);
        USER_MAP.put(player.getUuid(), playerUser);
    }


    public void onQuit(PlayerDisconnectEvent event) {
        Player player = event.getPlayer();
        USER_MAP.remove(player.getUuid());
    }

    @Override
    public User<Player> user(Player player) {
        return USER_MAP.get(player.getUuid());
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
        return new UserImpl(buffer);
    }

}
