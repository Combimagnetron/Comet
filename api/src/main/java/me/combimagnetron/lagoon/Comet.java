package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;

public class Comet {
    private static Server server;

    static void server(Server server) {
        if (Comet.server != null) {
            throw new RuntimeException("Can't initialize the Server singleton twice!");
        }
        Comet.server = server;
    }

    public static Path dataFolder() {
        return Path.of(javaPlugin().getDataFolder().getPath());
    }

    public static User<?> userByUniqueId(UUID uuid) {
        return server.playerByUniqueId(uuid);
    }

    public static MessageClient messageClient(MessageClient.Type type) {
        return server.messageClient(type);
    }

    public static MessageClient messageClient() {
        return server.messageClient(MessageClient.Type.REDIS);
    }

    public static Instance instance(Identifier identifier) {
        return server.instance(identifier);
    }

    public static Instance instance(UUID uuid) {
        return server.instance(uuid);
    }

    public static JavaPlugin javaPlugin() {
        return server.javaPlugin();
    }

    public static Collection<User<?>> usersOnInstance(Instance instance) {
        return server.playersOnInstance(instance);
    }

    public static Collection<User<?>> onlineUsers() {
        return server.onlinePlayers();
    }

    public static Feature feature(Identifier identifier) {
        return server.feature(identifier);
    }

    public static <T extends Feature> T feature(Class<T> clazz) {
        return server.feature(clazz);
    }

    public static Feature startFeature(Feature feature) {
        return server.startFeature(feature);
    }




}
