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
        Arrays.stream(MessageClient.Type.values()).toList().forEach(value -> Lagoon.messageClient(value).terminate());
    }

    @Override
    public void onEnable() {
        try {
            Lagoon.server(new ServerImpl(Path.of("")));
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }


}
