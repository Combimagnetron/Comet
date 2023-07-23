package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.serialize.SerializationException;

import java.nio.file.Path;
import java.util.Arrays;

public class LagoonPlugin extends JavaPlugin {

    @Override
    public void onLoad() {

    }

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


}
