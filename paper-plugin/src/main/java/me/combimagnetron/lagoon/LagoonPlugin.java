package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.connection.NetworkImpl;
import me.combimagnetron.lagoon.event.EventBus;
import me.combimagnetron.lagoon.internal.network.Network;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.serialize.SerializationException;

import java.nio.file.Path;
import java.util.Arrays;

public class LagoonPlugin extends JavaPlugin implements CometBase<JavaPlugin> {
    private final Network network = new NetworkImpl();

    @Override
    public void onDisable() {
        Arrays.stream(MessageClient.Type.values()).toList().forEach(value -> Comet.messageClient(value).terminate());
    }

    @Override
    public void onEnable() {
        try {
            Comet.server(new ServerImpl(Path.of(getDataFolder().getPath(), "config.hocon"), this));
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
        PaperCommandHandler commandHandler = new PaperCommandHandler();
    }


    @Override
    public Network network() {
        return network;
    }

    @Override
    public EventBus eventBus() {
        return null;
    }

    @Override
    public JavaPlugin plugin() {
        return this;
    }
}
