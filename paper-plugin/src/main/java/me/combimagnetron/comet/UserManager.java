package me.combimagnetron.comet;

import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.event.EventSubscription;
import me.combimagnetron.comet.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class UserManager implements Listener {
    private final HashMap<UUID, User<Player>> USER_MAP = new HashMap<>();
    private final EventSubscription<?> joinSubscription = EventBus.subscribe(PlayerJoinEvent.class, (event) -> {
        Player player = event.getPlayer();
    }).async();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User<Player> playerUser = new PaperUser(player);
        USER_MAP.put(player.getUniqueId(), playerUser);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        USER_MAP.remove(player.getUniqueId());
    }

    public User<?> user(Player player) {
        return USER_MAP.get(player.getUniqueId());
    }

    public User<?> user(UUID player) {
        return USER_MAP.get(player);
    }

}
