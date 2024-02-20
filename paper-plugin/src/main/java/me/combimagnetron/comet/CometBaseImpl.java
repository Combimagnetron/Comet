package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.config.annotation.Config;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Singleton;
import java.util.logging.Logger;

@Singleton
public class CometBaseImpl implements CometBase<JavaPlugin> {
    private final UserManager userManager = new UserManager();
    private final CometPlugin cometPlugin;

    public CometBaseImpl(CometPlugin cometPlugin) {
        this.cometPlugin = cometPlugin;
        Bukkit.getServer().getPluginManager().registerEvents(userManager, cometPlugin);
    }

    @Override
    public Network network() {
        return null;
    }

    @Override
    public EventBus eventBus() {
        return null;
    }

    @Override
    public UserHandler<? extends Audience> users() {
        return userManager;
    }

    @Override
    public ServiceHandler services() {
        return null;
    }

    @Override
    public ResourcePackManager resourcePacks() {
        return null;
    }

    @Override
    public MessageClient messageClient() {
        return null;
    }

    @Override
    public Logger logger() {
        return Bukkit.getLogger();
    }

    @Override
    public JavaPlugin plugin() {
        return cometPlugin;
    }

    @Config
    record BaseConfig(

    ) {

    }

}
