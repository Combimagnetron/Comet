package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.MessageClient;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.serialize.SerializationException;

import java.nio.file.Path;
import java.util.Arrays;

public class CometPlugin extends JavaPlugin {
    CometBase<JavaPlugin> cometBase;

    @Override
    public void onDisable() {
        Arrays.stream(MessageClient.Type.values()).toList().forEach(value -> Comet.messageClient(value).terminate());
    }

    @Override
    public void onEnable() {
        this.cometBase = new CometBaseImpl(this);
        try {
            Comet.server(new ServerImpl(Path.of(getDataFolder().getPath(), "config.hocon"), this));
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
        PaperCommandHandler commandHandler = new PaperCommandHandler(cometBase);
    }

}
