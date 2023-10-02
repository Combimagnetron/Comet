package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.config.annotation.Config;
import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.internal.network.Network;
import me.combimagnetron.comet.resourcepack.ResourcePackManager;
import me.combimagnetron.comet.service.ServiceHandler;
import me.combimagnetron.comet.user.UserHandler;
import net.kyori.adventure.audience.Audience;
import org.bukkit.plugin.java.JavaPlugin;

public class CometBaseImpl implements CometBase<JavaPlugin> {
    private final CometPlugin cometPlugin;

    public CometBaseImpl(CometPlugin cometPlugin) {
        this.cometPlugin = cometPlugin;
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
        return null;
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
    public JavaPlugin plugin() {
        return cometPlugin;
    }

    @Config
    record BaseConfig(

    ) {

    }

}
