package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.data.impl.PlayerDataContainer;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.user.User;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaperUser implements User<Player> {
    private final PlayerDataContainer playerDataContainer = null;
    private final Player player;
    private Instance instance;

    public PaperUser(Player player) {
        this.player = player;
    }

    @Override
    public Player platformSpecificPlayer() {
        return player;
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
    public PlayerDataContainer playerData() {
        return playerDataContainer;
    }

    @Override
    public UUID uniqueIdentifier() {
        return player.getUniqueId();
    }
}
