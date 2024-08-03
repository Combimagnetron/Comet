package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.Channels;
import me.combimagnetron.comet.config.annotation.Config;
import me.combimagnetron.comet.connection.NetworkImpl;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.instance.InstanceHandler;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.UUID;

@Singleton
public class CometBaseImpl implements CometBase<JavaPlugin> {
    private final UUID instanceUuid = UUID.randomUUID();
    private final UserManager userManager = new UserManager();
    private final Network network = new NetworkImpl();
    private final CometPlugin cometPlugin;

    public CometBaseImpl(CometPlugin cometPlugin) {
        this.cometPlugin = cometPlugin;
        Bukkit.getServer().getPluginManager().registerEvents(userManager, cometPlugin);
    }

    @Override
    public Network network() {
        return network;
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
    public UUID instanceUuid() {
        return instanceUuid;
    }

    @Override
    public InstanceHandler instances() {
        return null;
    }

    @Override
    public Channels channels() {
        return new Channels(null, null, null);
    }

    @Override
    public Path dataFolder() {
        return plugin().getDataPath();
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
