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

    }

    @Override
    public void onEnable() {
        this.cometBase = new CometBaseImpl(this);
        CometBase.Holder.INSTANCE = cometBase;
        PaperCommandHandler commandHandler = new PaperCommandHandler(cometBase);
    }

}
